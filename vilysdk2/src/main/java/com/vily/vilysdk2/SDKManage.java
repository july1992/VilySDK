package com.vily.vilysdk2;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/5/29
 *  
 **/
public class SDKManage {

    private static SDKManage spManager;
    private Handler mHandler;
    private static Context mContext;

    public SDKManage() {
    }

    public static SDKManage getInstance() {
        if (spManager == null) {
            synchronized(SDKManage.class) {
                if (spManager == null) {
                    spManager = new SDKManage();
                }
            }
        }

        return spManager;
    }

    public static Context getContent() {
        if (mContext == null) {
            Log.e("Connect", "Connect is null");
        }

        return mContext;
    }

}
