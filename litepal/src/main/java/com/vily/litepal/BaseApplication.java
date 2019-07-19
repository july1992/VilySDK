package com.vily.litepal;

import android.app.Application;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/19
 *  
 **/
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LitePal.initialize(this);

    }
}
