package com.example.wade8.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private final CharSequence CHANNEL_NAME = "NAME";
    private final String CHANNEL_ID = "ID";

    private Button mSendNotificationButton;
    private Button mStartServiceButton;
    private Button mStopServiceButton;
    private Button mBindServiceButton;
    private Button mUnBindServiceButton;

    private NotificationManager mNotificationManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder) service;
            mMyService = binder.getService();
            mBound = true;
            Log.d(TAG, "onServiceConnected executed");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected executed");
        }
    };
    private MyService mMyService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mSendNotificationButton = findViewById(R.id.button_send_notification);
        mStartServiceButton = findViewById(R.id.button_start_service);
        mStopServiceButton = findViewById(R.id.button_stop_service);
        mBindServiceButton = findViewById(R.id.button_bind_service);
        mUnBindServiceButton = findViewById(R.id.button_unbind_service);

        mSendNotificationButton.setOnClickListener(this);
        mStartServiceButton.setOnClickListener(this);
        mStopServiceButton.setOnClickListener(this);
        mBindServiceButton.setOnClickListener(this);
        mUnBindServiceButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_send_notification:
                sendNotification();
                break;

            case R.id.button_start_service:
                Intent startServiceIntent = new Intent(this, MyService.class);
                startService(startServiceIntent);
                break;

            case R.id.button_stop_service:
                Intent stopServiceIntent = new Intent(this, MyService.class);
                stopService(stopServiceIntent);
                break;

            case R.id.button_bind_service:

                    Intent intent = new Intent(this, MyService.class);
                    bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

                break;

            case R.id.button_unbind_service:
                if (mBound) {
                    unbindService(mServiceConnection);
                    mBound = false;
                }
                break;
        }
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent intent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this)
                  // statusbar 上面顯示的那種小縮圖
                  .setSmallIcon(R.drawable.logo_main)
                  // 類似mipmap較大的圖
                  .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.mipmap))
                  .setContentTitle("測試")
                  .setContentText("嘻嘻")
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
}
