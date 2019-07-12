package com.vily.progressbar;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;



/**
 * description : 悬浮窗
 */
public class SettingWindow extends PopupWindow {

    private final View rootView;
    private final Context mContext;



    public SettingWindow(Context context, View rootView) {
        super(context);
        this.mContext = context;
        this.rootView = rootView;


        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom,null,false);
        setContentView(view);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setAnimationStyle(R.style.BottomFade);
        //使其可以在窗口外点击取消
        setOutsideTouchable(true);
    }

    public void showBase(View view) {
        showAsDropDown(view);
//        int mHeight = mContext.getResources().getDisplayMetrics().heightPixels;
//        showAtLocation(rootView, Gravity.NO_GRAVITY, 0, mHeight-10);
    }

    public void showAll(){
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }
}
