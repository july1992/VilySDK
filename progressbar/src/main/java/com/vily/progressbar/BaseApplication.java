package com.vily.progressbar;

import android.app.Application;
import android.content.Context;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/10
 *  
 **/
public class BaseApplication extends Application {


    private static  Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;

    }

    public static Context getContext(){
        return mContext;
    }
}
