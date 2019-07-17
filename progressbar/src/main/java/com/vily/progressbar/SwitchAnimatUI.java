package com.vily.progressbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vily.progressbar.utils.UIUtil;

/**
 *  * description :  switch 开关切换动画
 *  
 **/
public class SwitchAnimatUI extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "SwitchAnimatUI";

    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;
    private LinearLayout mLly_toggle;
    private ImageView mIv_toggle;
    private int ovilWidth=22;  // 圆球的宽度

    private boolean isOpen=false;
    private TranslateAnimation mLeftAni;
    private TranslateAnimation mRightAni;

    public SwitchAnimatUI(Context context) {
        this(context,null);
    }

    public SwitchAnimatUI(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchAnimatUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();
    }

    private void initView() {


        mInflater=LayoutInflater.from(mContext);
        if(mView==null){
            mView = mInflater.inflate(R.layout.layout_switch_toggle, this,true);
            mLly_toggle = mView.findViewById(R.id.lly_toggle);
            mIv_toggle = mView.findViewById(R.id.iv_toggle);

        }

        mLly_toggle.setOnClickListener(this);

        toInitSwitch();
    }


    private void toInitSwitch(){


        mLly_toggle.post(new Runnable() {
            @Override
            public void run() {
                int left = mLly_toggle.getLeft();
                int right = mLly_toggle.getRight();
                Log.i(TAG, "toInitSwitch: -----:"+left+"---:"+right);
                // 左-右
                mLeftAni = new TranslateAnimation(
                        Animation.ABSOLUTE, (float) left,
                        Animation.ABSOLUTE, (float) (right-UIUtil.dip2px(ovilWidth+4)),
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0);
                mLeftAni.setRepeatCount(0);
                mLeftAni.setDuration(200);
                mLeftAni.setFillAfter(true);

                // 右-左
                mRightAni = new TranslateAnimation(
                        Animation.ABSOLUTE, (float)(right-UIUtil.dip2px(ovilWidth+4)),
                        Animation.ABSOLUTE, (float)left,
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0);
                mRightAni.setRepeatCount(0);
                mRightAni.setDuration(200);
                mRightAni.setFillAfter(true);


            }
        });

    }

    public void setPosition(boolean isOpen){

        isOpen=isOpen;
        if(isOpen){
            mIv_toggle.startAnimation(mLeftAni);
            mLly_toggle.setBackgroundResource(R.drawable.toggle_bg_shape);
        }

    }

    @Override
    public void onClick(View v) {

        if(isOpen){
            mIv_toggle.startAnimation(mRightAni);
            mLly_toggle.setBackgroundResource(R.drawable.toggle_close_shape);

        }else{
            mIv_toggle.startAnimation(mLeftAni);
            mLly_toggle.setBackgroundResource(R.drawable.toggle_bg_shape);

        }
        isOpen=!isOpen;
    }
}
