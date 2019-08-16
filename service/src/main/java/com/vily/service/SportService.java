package com.vily.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.vily.service.shake.ShakeDetector;

/**
 *  * description : 
 *  
 **/
public class SportService extends Service {
    private static final String TAG = "SportService";
    private ShakeDetector shakeDetector;
    private long startTime = 0;
    private int sport = 0;
    private NotificationCompat.Builder mBuilder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: --------");
        if (shakeDetector == null) {
            startTime = 0;
            shakeDetector = new ShakeDetector(this);
            shakeDetector.registerOnShakeListener(new ShakeDetector.OnShakeListener() {
                @Override
                public void onData(int shake_num, int stat_time, long cur_time) {

                }
            });
            shakeDetector.start();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand: -----");
        if (mBuilder == null) {
            mBuilder = new NotificationCompat.Builder(this,"10");

            NotificationManager notificationManager = (NotificationManager) getSystemService
                    (NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("10", "senblo", NotificationManager.IMPORTANCE_DEFAULT);
//                mBuilder.setChannelId("10");
                notificationManager.createNotificationChannel(channel);
            }

            mBuilder.setLargeIcon(BitmapFactory.decodeResource(BaseApplication.getApplication().getResources(), R.mipmap.ic_launcher));
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentText("senblo sport running");
            mBuilder.setContentTitle("senblo sport");

            Intent newIntent = new Intent(this, MusicActivtiy.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            mBuilder.setContentIntent(PendingIntent.getActivity(this, 10, newIntent, PendingIntent.FLAG_UPDATE_CURRENT));

            mBuilder.build().flags |= Notification.FLAG_ONGOING_EVENT;
            mBuilder.build().flags |= Notification.FLAG_NO_CLEAR;
        }

        startForeground(10, mBuilder.build());


        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: -------");
        if (shakeDetector != null) {
            shakeDetector.stop();
            shakeDetector = null;
        }
        if (mBuilder != null) {
            stopForeground(true);
            mBuilder = null;
        }

    }
}
