package com.vily.scoroll;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/5
 *  
 **/
public class MyScorolleView extends NestedScrollView {

    private boolean first=true;

    public MyScorolleView(Context context) {
        super(context);
    }

    public MyScorolleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScorolleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
//        if(first){
//            first=false;
//            return true;
//        }else{
//            return false;
//        }
    }
}
