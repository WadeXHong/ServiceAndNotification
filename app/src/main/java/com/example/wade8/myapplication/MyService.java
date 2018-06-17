package com.example.wade8.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wade8 on 2018/6/17.
 */

public class MyService extends Service {

    private final String TAG = MyService.class.getSimpleName();
    private final CharSequence CHANNEL_NAME = "NAME";
    private final String CHANNEL_ID = "ID";

    // 這個很容易忘記
    private final IBinder mBinder = new MyBinder();
    private NotificationManager mNotificationManager;

    private boolean mIsServiceAlive;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, TAG + " onCreate");

        mIsServiceAlive = true;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        startAnnoying();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        sendNotification();
        Log.d(TAG, TAG + " onStartCommand");


        // START_STICKY : Service被殺掉, 系統會重啟, 但是Intent會是null。
        // START_NOT_STICKY : Service被系統殺掉, 不會重啟。
        // START_REDELIVER_INTENT : Service被系統殺掉, 重啟且Intent會重傳。
        // From 惡補筆記
        return START_NOT_STICKY;
    }

    private void startAnnoying() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsServiceAlive){
                    try {
                        Thread.sleep(2000);
                        sendNotification();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, TAG + " onDestroy");
        mIsServiceAlive = false;
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent intent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this)
                  // statusbar 上面顯示的那種小縮圖
                  .setSmallIcon(R.drawable.logo_main)
                  // 類似mipmap較大的圖
                  .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.mipmap))
                  .setContentTitle("Service")
                  .setContentText("onStartCommand")
                  // 點擊發送 PendingIntent
                  .setContentIntent(intent)
                  // Android O 以前設定 Priority 的方法 (兩行都要)
                  .setDefaults(Notification.DEFAULT_ALL)
                  .setPriority(Notification.PRIORITY_HIGH)
                  // 點擊後自動取消 Notification
                  .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Constructor (ID, NAME, Priority)
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("啥");
            // 鎖屏是否可見
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            // LED燈和顏色
            channel.enableLights(true);
            channel.setLightColor(Color.RED);

            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(CHANNEL_ID);
        }


        Notification notification = builder.build();
        mNotificationManager.notify(1, notification);
    }


    public boolean isServiceAlive() {
        return mIsServiceAlive;
    }

    public void setServiceAlive(boolean serviceAlive) {
        mIsServiceAlive = serviceAlive;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
}
