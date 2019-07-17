package com.vily.progressbar;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/15
 *  
 **/
public class MyPopuWindow {

    private LayoutInflater mInflater;
    private View mPopouView;
    private View mView;
    private PopupWindow mWindow;

    private Context mContext;

    public MyPopuWindow() {
    }
    public MyPopuWindow(View contentView, Context context,List<String> list) {
        mInflater=LayoutInflater.from(context);
        mView=contentView;
        mContext=context;


        init();
    }
    public MyPopuWindow( Context context) {
        mInflater=LayoutInflater.from(context);

        mContext=context;


    }
    public MyPopuWindow(View contentView, Context context,List<String> list,int type) {
        mInflater=LayoutInflater.from(context);
        mView=contentView;
        mContext=context;

        init();
    }


    public void setData(View contentView,List<String> data){
        if(contentView==null){
            return;
        }
        if(data==null){
            return;
        }

        mWindow.showAsDropDown(contentView);
    }

    private void init() {
        mPopouView = mInflater.inflate(R.layout.inflate_selector_popu, null, false);


        mWindow = new PopupWindow(mPopouView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mWindow.setTouchable(true);

        mWindow.setFocusable(false);
        mWindow.setOutsideTouchable(true);


        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mOnPopuDismissListener!=null){
                    mOnPopuDismissListener.onPopuDismiss();
                }
            }
        });
        mWindow.showAtLocation(((MainActivity)mContext).getWindow().getDecorView(), Gravity.CENTER, 0, 0);

    }




    public void show() {
        mWindow.showAtLocation(((MainActivity)mContext).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    private OnPopuDismissListener mOnPopuDismissListener;
    public interface OnPopuDismissListener{
        void onPopuDismiss();
    }
    public void setOnPopuDismissListener(OnPopuDismissListener onPopuDismissListener) {
        mOnPopuDismissListener = onPopuDismissListener;
    }

}
