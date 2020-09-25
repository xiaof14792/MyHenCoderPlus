package com.example.threadandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                return false;
            }

        });
        handler.hasMessages(0);
        handler.getLooper();
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                return false;
            }
        });


        handler.obtainMessage(10).sendToTarget();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Handler.createAsync(Looper.myLooper());
        }

        Looper.prepareMainLooper();
    }

}