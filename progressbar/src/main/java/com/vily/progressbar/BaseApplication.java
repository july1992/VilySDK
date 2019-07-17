package com.vily.progressbar;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/10
 *  
 **/
public class BaseApplication extends Application {


    private static  Context mContext;
    //全局的handler
    private static Handler mHandler;
    //主线程
    private static Thread mMainThread;
    //主线程id
    private static int mMainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;
        mHandler = new Handler();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
    }

    public static Context getContext(){
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }
    public static int getMainThreadId() {
        return mMainThreadId;
    }
}
