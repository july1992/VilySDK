package com.vily.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService
//                (NOTIFICATION_SERVICE);
//
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("10", "senblo", NotificationManager.IMPORTANCE_DEFAULT);
//            mBuilder.setChannelId("10");
//            notificationManager.createNotificationChannel(channel);
//
//        }
//
//
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setContentText("senblo 服务");
//        mBuilder.setContentTitle("senblo");
//
//        notificationManager.notify(10, mBuilder.build());


        Intent intent=new Intent(MainActivity.this,MyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else{
            startService(intent);
        }

    }



}

