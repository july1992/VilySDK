package com.vily.scoroll;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/6
 *  
 **/
public class ViewDragHelpActivity extends AppCompatActivity {

    private static final String TAG = "ViewDragHelpActivity";

    private LinearLayout mLly_drag;
    private MyViewDragHelper mViewDragHelper;
    private int mHeight=2197;

    private int mBottomBorderHeigth = 50;//底部边界凸出的高度
    private int mBottomHeight=1238;  // 450dp


    private boolean isOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        mLly_drag = findViewById(R.id.lly_drag);

        mBottomBorderHeigth=dip2px(100);

        mViewDragHelper = MyViewDragHelper.create(mLly_drag, 1.0f, mCallback);
    }


    MyViewDragHelper.Callback mCallback = new MyViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.i(TAG, "tryCaptureView: --------mBottomView == child:"+(mLly_drag == child));
//            return mBottomView == child;
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {

            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {

            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            return top;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i(TAG, "onViewPositionChanged: ------------");
            if (changedView == mLly_drag) {
                float startPosition = mHeight;
                float endPosition = mHeight - mBottomBorderHeigth;
                float totalLength = endPosition - startPosition;
                float rate = 1 - ((top - startPosition) / totalLength);


            }

        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {


            if (isOpen) {
                if (Math.abs(mLly_drag.getTop() - (mHeight - mBottomHeight)) > dip2px(mBottomBorderHeigth)) {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight - mBottomHeight);
                    isOpen = false;

                } else {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight -mBottomHeight);
                    isOpen = true;

                }
            } else {
                if (Math.abs(mLly_drag.getTop() - (mHeight - dip2px(mBottomBorderHeigth))) > dip2px(mBottomBorderHeigth)) {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight - mBottomHeight);
                    isOpen = true;


                } else {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight - dip2px(mBottomBorderHeigth));
                    isOpen = false;

                }
            }


        }


    };


    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i(TAG, "onInterceptTouchEvent: ------:" + ev.getAction());
//        return mViewDragHelper.shouldInterceptTouchEvent(ev);
//
//    }




    private float mStart;
    private int top;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ------:" + event.getAction());
        mViewDragHelper.processTouchEvent(event);

       return true;

    }



}
