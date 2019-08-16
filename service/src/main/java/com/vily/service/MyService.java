package com.vily.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
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
    private NotificationCompat.Builder mBuilder;

    private PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate: --------");
        acquireWakeLock(this);
    }
    @SuppressLint("InvalidWakeLockTag")
    public  void acquireWakeLock(Context context)
    {
        if (null == mWakeLock)
        {
            PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "WakeLock");
            if (null != mWakeLock)
            {
                mWakeLock.acquire();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ---------");

        if(mBuilder==null){
            mBuilder = new NotificationCompat.Builder(this);

            NotificationManager notificationManager = (NotificationManager) getSystemService
                    (NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("10", "senblo", NotificationManager.IMPORTANCE_DEFAULT);
                mBuilder.setChannelId("10");
                notificationManager.createNotificationChannel(channel);
            }

            mBuilder.setLargeIcon(BitmapFactory.decodeResource(BaseApplication.getApplication().getResources(), R.mipmap.ic_launcher));
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentText("senblo sport running");
            mBuilder.setContentTitle("senblo sport");
//        notificationManager.notify(10, mBuilder.build());

            Intent newIntent = new Intent(this, MusicActivtiy.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            mBuilder.setContentIntent(PendingIntent.getActivity(this, 10, newIntent, PendingIntent.FLAG_UPDATE_CURRENT));

            mBuilder.build().flags |= Notification.FLAG_ONGOING_EVENT;
            mBuilder.build().flags |= Notification.FLAG_NO_CLEAR;
        }

        startForeground(10,mBuilder.build());

        return START_REDELIVER_INTENT;


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: -----------");

        if(mBuilder!=null){
            stopForeground(true);
            mBuilder=null;
        }
        if (null != mWakeLock)
        {
            mWakeLock.release();
            mWakeLock = null;
        }
    }
}
