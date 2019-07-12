package com.vily.vilysdk2;

import android.util.Log;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/5/29
 *  
 **/
public class SDKDebug {

    private static final boolean isDebug = true;
    private static String debugStr = "";

    public SDKDebug() {
    }

    public static String getDebugStr() {
        return debugStr;
    }

    public static void clearDebugStr() {
        debugStr = "";
    }

    public static void d(String msg) {
        if (debugStr != null && debugStr.length() > 1000) {
            debugStr = "";
        }

        debugStr = debugStr + msg + "\n";
        Log.w("SPDebug", msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        Log.d(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        Log.i(tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void w(String tag, Throwable tr) {
        Log.w(tag, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        Log.v(tag, msg, tr);
    }
}
