package com.vily.service;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/12
 *  
 **/
public class BaseApplication extends Application {
    private static BaseApplication sBaseApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication=this;
//        AlarmRepeat();
    }
    public static BaseApplication getApplication(){
        return sBaseApplication;
    }
    private void AlarmRepeat() {
        // 闹钟
        Intent intentRepeat = new Intent(this, MyService.class);
        PendingIntent sender = PendingIntent.getService(this, 0,
                intentRepeat, 0);
        long triggerTime = SystemClock.elapsedRealtime() + 2 * 1000; // 第一次时间
        long intervalTime = 5* 60 * 1000; // ms
        AlarmManager am = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);
        /**
         * AlarmManager.ELAPSED_REALTIME表示闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（
         * 相对于系统启动开始），状态值为3；
         *
         * AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，
         * 该状态下闹钟也使用相对时间，状态值为2；
         *
         * AlarmManager.RTC表示闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
         *
         * AlarmManager.RTC_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0；
         */
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime,
                intervalTime, sender);

    }

}
