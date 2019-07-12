package com.vily.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/8
 *  
 **/
public class MyProgressBarUI extends View {

    private static final String TAG = "MyProgressBarUI";

    private int mWidth, mHeight;

    private Paint mPaint;
    private Paint mPaintStorke;

    private int mBackgroundColor;
    private int mStorkeColor;

    private List<User> mData = new ArrayList<>();
    private int max;

    /**
     * 分段颜色
     */
    private static final int[] SECTION_COLORS = new int[7];
//    private static final int[] progressData = new int[4];


    public MyProgressBarUI(Context context) {
        this(context, null);
    }

    public MyProgressBarUI(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBarUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBarUI);
        try {

            mBackgroundColor = mTypedArray.getColor(R.styleable.MyProgressBarUI_backgroundColor, Color.TRANSPARENT);
            mStorkeColor = mTypedArray.getColor(R.styleable.MyProgressBarUI_stokerColor, Color.TRANSPARENT);

        } finally {
            mTypedArray.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintStorke = new Paint(Paint.ANTI_ALIAS_FLAG);
        SECTION_COLORS[0] = getResources().getColor(R.color.color1);
        SECTION_COLORS[1] = getResources().getColor(R.color.color2);
        SECTION_COLORS[2] = getResources().getColor(R.color.color3);
        SECTION_COLORS[3] = getResources().getColor(R.color.color1);
        SECTION_COLORS[4] = getResources().getColor(R.color.color2);
        SECTION_COLORS[5] = getResources().getColor(R.color.color3);
        SECTION_COLORS[6] = getResources().getColor(R.color.color1);

    }

    public void setData(List<User> data) {
        max = 0;
        mData.clear();
        if(data==null || data.size()==0){
            return;
        }
        mData = data;

        for (int i = 0; i < data.size(); i++) {
            max += data.get(i).getProgress();
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onMeasure: ----------1:" + mWidth);
        Log.i(TAG, "onMeasure: ----------1" + mHeight);
        mPaint.reset();
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        mPaint.setStrokeWidth(20);


        mPaintStorke.reset();
        mPaintStorke.setAntiAlias(false);// 设置画笔的锯齿效果
        mPaintStorke.setStyle(Paint.Style.STROKE);//设置填满
        mPaintStorke.setStrokeWidth(dipToPx(1));

        float mCircleRadius = mHeight / 2;
        mPaint.setColor(mBackgroundColor);
        mPaintStorke.setColor(mStorkeColor);


        RectF mCircleRectF = new RectF(0, 0, 0, 0);

        //画背景矩形
        RectF backgroudRect = new RectF(mCircleRadius, 0, mWidth - mCircleRadius, mHeight);
        canvas.drawRect(backgroudRect, mPaint);

        try {

            int sum = 0;
            for (int i = 0; i < mData.size(); i++) {

                mPaint.setColor(SECTION_COLORS[i]);
                if (i == 0) {
                    // 画左边半圆
                    mCircleRectF.left = 0;
                    mCircleRectF.top = 0;
                    mCircleRectF.right = mCircleRadius * 2;
                    mCircleRectF.bottom = mHeight;
                    canvas.drawArc(mCircleRectF, -90, -180, false, mPaint);
                    canvas.drawArc(mCircleRectF, -90, -180, false, mPaintStorke);

                    if (mData.size() == 1) {
                        RectF progressRec = new RectF(mCircleRadius, 0, mWidth - mCircleRadius, mHeight);
                        canvas.drawRect(progressRec, mPaint);
                    } else {
                        RectF progressRec = new RectF(mCircleRadius, 0, mData.get(i).getProgress() * mWidth / max, mHeight);
                        canvas.drawRect(progressRec, mPaint);
                    }


                } else {
                    // 画中间的半圆
                    mCircleRectF.left = sum - mCircleRadius;
                    mCircleRectF.top = 0;
                    mCircleRectF.right = sum + mCircleRadius;
                    mCircleRectF.bottom = mHeight;
                    canvas.drawArc(mCircleRectF, -90, -180, false, mPaint);
                    canvas.drawArc(mCircleRectF, -90, -180, false, mPaintStorke);


                    if (i == mData.size() - 1) {
                        RectF progressRec = new RectF(sum, 0, mWidth - mCircleRadius, mHeight);
                        canvas.drawRect(progressRec, mPaint);


                    } else {
                        RectF progressRec = new RectF(sum, 0, sum + (mData.get(i).getProgress() * mWidth / max), mHeight);
                        canvas.drawRect(progressRec, mPaint);


                    }


                }
                sum += mData.get(i).getProgress() * mWidth / max;

            }
        } catch (Exception e) {
            // 画左边半圆
            mPaint.setColor(mBackgroundColor);
            mCircleRectF.left = 0;
            mCircleRectF.top = 0;
            mCircleRectF.right = mCircleRadius * 2;
            mCircleRectF.bottom = mHeight;
            canvas.drawArc(mCircleRectF, -90, -180, false, mPaint);
            canvas.drawArc(mCircleRectF, -90, -180, false, mPaintStorke);
        } finally {

            if(max==0){
                // 画左边半圆
                mPaint.setColor(mBackgroundColor);
                mCircleRectF.left = 0;
                mCircleRectF.top = 0;
                mCircleRectF.right = mCircleRadius * 2;
                mCircleRectF.bottom = mHeight;
                canvas.drawArc(mCircleRectF, -90, -180, false, mPaint);
                canvas.drawArc(mCircleRectF, -90, -180, false, mPaintStorke);
            }
            // 画右边半圆
            mCircleRectF.left = mWidth - mCircleRadius * 2;
            mCircleRectF.top = 0;
            mCircleRectF.right = mWidth;
            mCircleRectF.bottom = mHeight;
            canvas.drawArc(mCircleRectF, -90, 180, false, mPaint);
            canvas.drawArc(mCircleRectF, -90, 180, false, mPaintStorke);

            // 画上下两条线
            mPaintStorke.setStrokeWidth(dipToPx(2));
            canvas.drawLine(mCircleRadius, 0, mWidth - mCircleRadius, 0, mPaintStorke);
            canvas.drawLine(mCircleRadius, mHeight, mWidth - mCircleRadius, mHeight, mPaintStorke);
        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == View.MeasureSpec.EXACTLY || widthSpecMode == View.MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;

        } else {
            mWidth = 0;
        }
        if (heightSpecMode == View.MeasureSpec.AT_MOST || heightSpecMode == View.MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);

    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }


}
