package com.vily.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/8
 *  
 **/
public class MyService extends Service {

    private static final String TAG = "MyService";
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate: --------");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        NotificationManager notificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10", "senblo", NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId("10");
            notificationManager.createNotificationChannel(channel);

        }


        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentText("senblo 服务");
        mBuilder.setContentTitle("senblo");
//        notificationManager.notify(10, mBuilder.build());

        mBuilder.build().flags |= Notification.FLAG_ONGOING_EVENT;
        mBuilder.build().flags |= Notification.FLAG_NO_CLEAR;
        startForeground(10,mBuilder.build());

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: -----------");
    }
}
