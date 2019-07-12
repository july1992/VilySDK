package com.vily.progressbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/8
 *  
 **/
public class MyProgressBar extends ProgressBar {


    public MyProgressBar(Context context) {
        this(context,null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);

    }

    @Override
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);

    }


}
