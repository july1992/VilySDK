package com.vily.chart;

import android.app.Application;
import android.content.Context;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/11
 *  
 **/
public class BaseApplication extends Application {

    private static  Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;
    }

    public static  Context getContext(){

        return  mContext;
    }
}
