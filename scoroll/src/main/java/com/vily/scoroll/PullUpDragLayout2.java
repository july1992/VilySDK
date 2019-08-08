package com.vily.scoroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *  * description : 底部拖拽监听
 */
public class PullUpDragLayout2 extends ViewGroup {

    private static final String TAG = "PullUpDragLayout";

    private Context mContext;
    private MyViewDragHelper mViewDragHelper;//拖拽帮助类
    private View mBottomView;//底部内容View


    private int mHeight;
    LayoutInflater mLayoutInflater;
    private int mBottomBorderHeigth = 50;//底部边界凸出的高度
    private int mBottomHeight=450;


    private Point mAutoBackBottomPos = new Point();
    //    private Point mAutoBackTopPos = new Point();
    private int mBoundTopY;
    private boolean isOpen;
    private TextView mTv_top;


    public PullUpDragLayout2(Context context) {
        this(context, null, 0);
    }

    public PullUpDragLayout2(Context context, int height) {
        this(context, null);
        mHeight = height;
    }

    public PullUpDragLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullUpDragLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);

    }



    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mBottomView = mLayoutInflater.inflate(R.layout.view_bottom, this, true);
        mViewDragHelper = MyViewDragHelper.create(this, 1.0f, mCallback);

        mTv_top = mBottomView.findViewById(R.id.tv_title);


        final TextView tv_test = mBottomView.findViewById(R.id.tv_test);
        ImageView iv_img = mBottomView.findViewById(R.id.iv_img);


        mTv_top.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mTv_top.setText("sdadsadsad");
                toggleBottomView();
            }
        });

        iv_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_test.setText("tv_test");
            }
        });
        tv_test.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_test.setText("bushuohua ");
                return true;
            }
        });
    }

    MyViewDragHelper.Callback mCallback = new MyViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mBottomView == child;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {

            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {

            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mBottomView.getWidth() - leftBound;
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.i(TAG, "clampViewPositionVertical: ------------");
            int topBound = mHeight-mBottomView.getHeight();
            int bottomBound = mHeight - mBottomBorderHeigth;
            return Math.min(bottomBound, Math.max(top, topBound));
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i(TAG, "onViewPositionChanged: ------------");
            if (changedView == mBottomView) {
                float startPosition = mHeight;
                float endPosition = mBottomView.getHeight() - mBottomBorderHeigth;
                float totalLength = endPosition - startPosition;
                float rate = 1 - ((top - startPosition) / totalLength);
                if (mScrollChageListener != null) {
                    mScrollChageListener.onScrollChange(rate);
                }

            }

        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            if (isOpen) {
                if (Math.abs(mBottomView.getTop() - (mHeight - mBottomView.getHeight())) > dip2px(mBottomBorderHeigth)) {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight - dip2px(mBottomBorderHeigth));
                    isOpen = false;
                    if (mOnStateListener != null) mOnStateListener.close();
                } else {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight -dip2px(450));
                    isOpen = true;
                    if (mOnStateListener != null) mOnStateListener.open();
                }
            } else {
                if (Math.abs(mBottomView.getTop() - (mHeight - dip2px(mBottomBorderHeigth))) > dip2px(mBottomBorderHeigth)) {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight - dip2px(450));
                    isOpen = true;
                    if (mOnStateListener != null) mOnStateListener.open();

                } else {
                    mViewDragHelper.settleCapturedViewAt(0, mHeight - dip2px(mBottomBorderHeigth));
                    isOpen = false;
                    if (mOnStateListener != null) mOnStateListener.close();
                }
            }

            invalidate();


        }


    };

    public boolean isOpen() {
        return isOpen;
    }

    // 初始动画  底部划出来
    public void startAni() {
        Log.i(TAG, "startAni: ----------:"+"----:"+mHeight);

        mViewDragHelper.smoothSlideViewTo(mBottomView,0, mHeight - dip2px(mBottomBorderHeigth));
        invalidate();
    }

    public int dip2px(int dip) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    /**
     * 切换底部View
     */
    public void toggleBottomView() {

        if (isOpen) {
            mViewDragHelper.smoothSlideViewTo(mBottomView, 0, mHeight - dip2px(mBottomBorderHeigth));
            if (mOnStateListener != null) mOnStateListener.close();
        } else {
            mViewDragHelper.smoothSlideViewTo(mBottomView, 0, mHeight - dip2px(450));
            if (mOnStateListener != null) mOnStateListener.open();
        }
        invalidate();
        isOpen = !isOpen;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.i(TAG, "onMeasure: -----:" + mHeight + "----");

        mBottomView = getChildAt(0);
        measureChild(mBottomView, widthMeasureSpec, heightMeasureSpec);
        int bottomViewHeight = mBottomView.getMeasuredHeight();

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), bottomViewHeight + mHeight + getPaddingBottom() + getPaddingTop());


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        mBottomView = getChildAt(0);
//        mBottomView.layout(0, mHeight, 0, getMeasuredHeight());
        mBottomView.layout(getPaddingLeft(), mHeight, getWidth() - getPaddingRight(), getMeasuredHeight() );
//        mAutoBackBottomPos.x = mBottomView.getLeft();
//        mAutoBackBottomPos.y = mBottomView.getTop();
//        mBoundTopY = mHeight - mBottomView.getHeight() / 2;
        startAni();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: ------:" + ev.getAction());
        return mViewDragHelper.shouldInterceptTouchEvent(ev);

    }


    private float mStart;
    private int top;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ------:" + event.getAction());
        mViewDragHelper.processTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStart = event.getY();
                top = mBottomView.getTop();
                break;
        }

        if (mStart > top) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void computeScroll() {
        Log.i(TAG, "computeScroll: ------:");
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }


    private OnStateListener mOnStateListener;
    private OnScrollChageListener mScrollChageListener;

    public void setOnStateListener(OnStateListener onStateListener) {
        mOnStateListener = onStateListener;
    }
    public void setScrollChageListener(OnScrollChageListener scrollChageListener) {
        mScrollChageListener = scrollChageListener;
    }

//    public Object getBottomTop() {
//    }

    public interface OnStateListener {
        void open();
        void close();
    }
    public interface OnScrollChageListener {
        void onScrollChange(float rate);
    }
}

