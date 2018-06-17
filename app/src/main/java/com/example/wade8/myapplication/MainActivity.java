package com.example.wade8.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final CharSequence CHANNEL_NAME = "NAME";
    private final String CHANNEL_ID = "ID";

    private Button mSendNotificationButton;
    private Button mStartServiceButton;
    private Button mStopServiceButton;
    private Button mBindServiceButton;
    private Button mUnBindServiceButton;

    private NotificationManager mNotificationManager;

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
        switch (v.getId()){

            case R.id.button_send_notification:

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
                          // 點擊後自動取消 Notification
                          .setAutoCancel(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("啥");
                    // 所平是否可見
                    channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
                    // LED燈和顏色
                    channel.enableLights(true);
                    channel.setLightColor(Color.RED);

                    mNotificationManager.createNotificationChannel(channel);
                    builder.setChannelId(CHANNEL_ID);
                }



                Notification notification = builder.build();
                mNotificationManager.notify(1, notification);

                break;

            case R.id.button_start_service:
                break;

            case R.id.button_stop_service:
                break;

            case R.id.button_bind_service:
                break;

            case R.id.button_unbind_service:
                break;
        }
    }
}
