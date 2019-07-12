package com.vily.progressbar;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


public class ReportLayout extends PopupWindow implements PopupWindow.OnDismissListener {

    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;
    private View mRoot;

    public ReportLayout(Activity activity, View rootView) {
        super(activity);
        mRoot=rootView;
        initView();
    }

    private void initView() {
        mContext=BaseApplication.getContext();
        mInflater=LayoutInflater.from(mContext);
        if(mView==null){
            mView = mInflater.inflate(R.layout.layout_report,null);
            setContentView(mView);
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setAnimationStyle(R.style.BottomFade);
            //使其可以在窗口外点击取消
            setOutsideTouchable(true);
            setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0x00000000);
            setBackgroundDrawable(dw);
            setOnDismissListener(this);

        }


    }

    private void initUI(View view) {

    }

    @Override
    public void onDismiss() {
        clearDim();
    }

    public void show() {
        showAtLocation(mRoot, Gravity.BOTTOM, 0, 0);
        applyDim(0.5f);
    }



    private void applyDim(float dimAmount) {
//        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
//        Drawable dim = new ColorDrawable(Color.BLACK);
//        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
//        dim.setAlpha((int) (255 * dimAmount));
//        ViewGroupOverlay overlay = parent.getOverlay();
//        overlay.add(dim);
    }

    private void clearDim() {
//        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
//        ViewGroupOverlay overlay = parent.getOverlay();
//        overlay.clear();
    }


}
