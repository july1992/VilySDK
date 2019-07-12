package com.vily.progressbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/10
 *  
 **/
public class RotateUi extends LinearLayout {

    private static final String TAG = "RotateUi";
    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;
    private ImageView mIv_core;
    private ImageView mIv_out;

    public RotateUi(Context context) {
        this(context,null);
    }

    public RotateUi(Context context,   AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateUi(Context context,   AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext=context;
        initView();
    }

    private void initView() {

        mInflater=LayoutInflater.from(mContext);
        if(mView==null){
            mView = mInflater.inflate(R.layout.layout_rotate, this,true);

            mIv_core = mView.findViewById(R.id.iv_core);
            mIv_out = mView.findViewById(R.id.iv_out);
        }
    }

    public void startRotate(){

        int[] location = new int[2];
        mIv_core.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int r = (mIv_core.getRight()-mIv_core.getLeft())/2;

        //圆心坐标
        int vCenterX = x+r;
        int vCenterY = y+r;

        Log.i(TAG, "startRotate: ----vCenterX:"+vCenterX+"----vCenterY:"+vCenterY);

        RotateAnimation animation=new RotateAnimation(0,360,
                Animation.ABSOLUTE, 500,
                Animation.ABSOLUTE,500);
        animation.setDuration(2000);
        animation.setRepeatCount(-1);



        mIv_out.startAnimation(animation);
    }

    public void stopRotate(){

        int[] location = new int[2];
        mIv_out.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        mIv_out.clearAnimation();

        mIv_out.setX(x);
        mIv_out.setY(y);

    }

}
