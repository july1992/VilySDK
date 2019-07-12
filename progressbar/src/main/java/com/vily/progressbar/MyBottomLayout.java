package com.vily.progressbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *  * description :  底部自由上升和下降
 *  * Author : Vily
 *  * Date : 2019/7/9
 *  
 **/
public class MyBottomLayout extends LinearLayout {

    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;
    private TextView mTv_title;
    private int mHeight;
    private RotateUi mRotate;
    private TranslateAnimation mDownAnim;
    private TranslateAnimation mUpAnim;

    public MyBottomLayout(Context context) {
        this(context,null);

    }

    public MyBottomLayout(Context context,   AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }

    public MyBottomLayout(Context context,   AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext=context;
        initView();

    }

    private void initView() {

        mInflater=LayoutInflater.from(mContext);
        if(mView==null){
            mView = mInflater.inflate(R.layout.layout_bottom, this,true);
            mTv_title = mView.findViewById(R.id.tv_title);
            mRotate = mView.findViewById(R.id.rotate);
        }
    }

    public void startShow(final MyBottomLayout root){
        mHeight = getResources().getDisplayMetrics().heightPixels;
        root.post(new Runnable() {
            @Override
            public void run() {
//                root.setY(mHeight-dipToPx(30));


            }
        });
        TranslateAnimation startAni = new TranslateAnimation(
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,dipToPx(100),
                Animation.ABSOLUTE,dipToPx(200));
        startAni.setRepeatCount(0);
        startAni.setDuration(2000);
        startAni.setFillAfter(true);

        root.setAnimation(startAni);

    }

    private boolean type=true;
    public void setData(final MyBottomLayout root){

        mHeight = getResources().getDisplayMetrics().heightPixels;
        // 设置起始位置
        root.post(new Runnable() {
            @Override
            public void run() {
                root.setY(mHeight-dipToPx(30));
            }
        });
        // 动画
        mDownAnim = new TranslateAnimation(
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,dipToPx(170));
        mDownAnim.setRepeatCount(0);
        mDownAnim.setDuration(200);
        mDownAnim.setFillAfter(true);


        mUpAnim = new TranslateAnimation(
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,0f,
                Animation.ABSOLUTE,-dipToPx(170));
        mUpAnim.setRepeatCount(0);
        mUpAnim.setDuration(200);
        mUpAnim.setFillAfter(true);

        // 监听
        mUpAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                root.clearAnimation();
                root.setY(mHeight-dipToPx(200));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mDownAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                root.clearAnimation();
                root.setY(mHeight-dipToPx(30));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mTv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type){
                    type=false;
                    root.startAnimation(mUpAnim);
                }else{
                    type=true;
                    root.startAnimation(mDownAnim);
                }
            }
        });

    }


    private int dipToPx(int dip) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public void startRotate(){
        mRotate.startRotate();
    }

    public void stopRotate(){
        mRotate.stopRotate();
    }

}
