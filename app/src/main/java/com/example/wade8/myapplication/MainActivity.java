package com.example.wade8.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button mSendNotificationButton;
    private Button mStartServiceButton;
    private Button mStopServiceButton;
    private Button mBindServiceButton;
    private Button mUnBindServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
