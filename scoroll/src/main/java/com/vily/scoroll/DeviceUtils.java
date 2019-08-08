package com.vily.scoroll;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.view.ViewConfiguration;

/**
 *  * description : 
 *  
 **/
public class DeviceUtils {

    public static boolean isHideNavigationBar(Context context) {

        if (isMIUI()) {//小米系统判断虚拟键是否显示方法
            boolean isHideNavigationBar = Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
            return isHideNavigationBar;
        }

        return false;
    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        // 这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    public static int getNavigationBarHeight(Context context) {
        try {
            if (isHideNavigationBar(context)) {
                boolean var1 = ViewConfiguration.get(context).hasPermanentMenuKey();
                int var2;
                return (var2 = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android")) > 0 && !var1 ? context.getResources().getDimensionPixelSize(var2) : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }


    public static int getNavigationBarHeight2(Context context) {
        try {
            if (isHideNavigationBar(context)) {
                return 50;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
}
