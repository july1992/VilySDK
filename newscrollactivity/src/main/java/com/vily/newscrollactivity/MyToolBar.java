package com.vily.newscrollactivity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-27
 *  
 **/
public class MyToolBar  extends Toolbar {


    private LayoutInflater mInflater;
    private Context mContext;
    private View mView;

    public MyToolBar(Context context) {
        this(context,null);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initView();

    }

    private void initView() {
        mInflater = LayoutInflater.from(mContext);
        if (mView == null) {
            mView = mInflater.inflate(R.layout.layout_legend_data, this, true);

        }

    }
}
