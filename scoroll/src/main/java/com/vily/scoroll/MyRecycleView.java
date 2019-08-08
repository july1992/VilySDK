package com.vily.scoroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/5
 *  
 **/
public class MyRecycleView extends RecyclerView {

    private static final String TAG = "MainActivity";


    public MyRecycleView(@NonNull Context context) {
        super(context);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        Log.i(TAG, "onInterceptTouchEvent: --------");
//        return true;
//
//    }

}
