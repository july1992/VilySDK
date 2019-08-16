package com.vily.scoroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/6
 *  
 **/
public class MySwip extends SwipeRefreshLayout {
    private static final String TAG = "PullUpDragLayout";
    public MySwip(@NonNull Context context) {
        super(context);
    }

    public MySwip(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }




//    @Override
//    public void computeScroll() {
//        Log.i(TAG, "computeScroll: --------MySwip");
//        super.computeScroll();
//    }
}
