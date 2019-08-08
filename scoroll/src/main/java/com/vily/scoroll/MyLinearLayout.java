package com.vily.scoroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/5
 *  
 **/
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context,   AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return true;
////        if(first){
////            first=false;
////            return true;
////        }else{
////            return false;
////        }
//    }
}
