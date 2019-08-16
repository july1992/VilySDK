package com.vily.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

/**
 *  * description :   音乐播放器后台运行
 *  
 **/
public class MusicService extends Service {

    private static final String TAG = "MusicService";

    private Intent mBroadcast;

    private MediaPlayer mMediaPlayer;
    private int mCurrPosition = -1;
    private String[] music;
    private NotificationCompat.Builder mBuilder;
    private PowerManager.WakeLock mWakeLock;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: --------");

        initMedia();
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

        initNotify();


        return START_STICKY;
    }

    private void initMedia() {
        if(mMediaPlayer==null){
            mMediaPlayer = new MediaPlayer();
        }

        if(mBroadcast==null){
            mBroadcast = new Intent();
            mBroadcast.setAction("music");
        }

        try {
            if(music==null){
                music = BaseApplication.getApplication().getAssets().list("music");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPosition(int position) {

        Log.i(TAG, "startPosition: ------2");
        if (mMediaPlayer.isPlaying()) {

            if (position < music.length) {

                Log.i(TAG, "reStart: ------:" + music[position]);
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                setDataSource(position);

            } else {
                mMediaPlayer.stop();
            }

        } else {
            Log.i(TAG, "startPosition: -------3");
            setDataSource(position);

        }

    }

    public void setDataSource(final int position) {

        if (music == null || music.length == 0) {
            return;
        }

        try {

            Log.i(TAG, "setDataSource: ------:" + music[position]);
            AssetFileDescriptor afd = BaseApplication.getApplication().getAssets().openFd("music/" + music[position]);
            mMediaPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());//指定音频文件路径
            mMediaPlayer.setLooping(false);//设置为循环播放
            mMediaPlayer.prepare();//初始化播放器MediaPlayer
            mMediaPlayer.start();

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mCurrPosition = (position + 1) % music.length;

                    mMediaPlayer.reset();
                    setDataSource(mCurrPosition);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initNotify() {
        try {
            if (mBuilder == null) {

                mBuilder = new NotificationCompat.Builder(this,"20");

                NotificationManager notificationManager = (NotificationManager) getSystemService
                        (NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("20", "music", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentText("senblo music running");
                mBuilder.setContentTitle("senblo music");
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(BaseApplication.getApplication().getResources(), R.mipmap.ic_launcher));

                Intent newIntent = new Intent(this, MusicActivtiy.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mBuilder.setContentIntent(PendingIntent.getActivity(this, 20, newIntent, PendingIntent.FLAG_UPDATE_CURRENT));

                mBuilder.build().flags |= Notification.FLAG_ONGOING_EVENT;
                mBuilder.build().flags |= Notification.FLAG_NO_CLEAR;
            }

            startForeground(20, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: ------");
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        if (music != null) {
            music = null;
        }

        if (mBroadcast != null) {
            mBroadcast = null;
        }
        if (mBuilder != null) {
            stopForeground(true);
            mBuilder = null;
        }
        if (mWakeLock !=null)
        {
            mWakeLock.release();
            mWakeLock = null;
        }
    }


}
