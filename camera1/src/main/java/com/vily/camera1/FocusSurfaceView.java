package com.vily.camera1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by zhaoshuang on 17/2/16.
 * 触摸对焦SurfaceView
 */

public class FocusSurfaceView extends SurfaceView {


    public FocusSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FocusSurfaceView(Context context) {
        super(context);
    }


    private int mRatioWidth=0;
    private int mRatioHeight=0;
    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if(mRatioWidth==0 && mRatioHeight==0){
            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        }else{
            setMeasuredDimension(mRatioWidth,mRatioHeight);
        }

    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//
//        Log.i("---", "onLayout: -----------正在切换大小屏幕:"+changed+"--"+left+"---"+top+"---"+right+"---"+bottom);
//
//
//    }
}
