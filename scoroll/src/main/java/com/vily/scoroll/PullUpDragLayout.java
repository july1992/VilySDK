package com.vily.scoroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 *  * description : 底部拖拽监听
 */
public class PullUpDragLayout extends ViewGroup {

    private static final String TAG = "PullUpDragLayout";

    private MyViewDragHelper mViewDragHelper;//拖拽帮助类
    private View mBottomView;//底部内容View
    private View mContentView;//内容View
    LayoutInflater mLayoutInflater;
    private int mBottomBorderHeigth = 50;//底部边界凸出的高度

    private Point mAutoBackBottomPos = new Point();
    private Point mAutoBackTopPos = new Point();
    private int mBoundTopY;
    private boolean isOpen;
    private OnStateListener mOnStateListener;
    private OnScrollChageListener mScrollChageListener;

    private Context mContext;

    public void setOnStateListener(OnStateListener onStateListener) {
        mOnStateListener = onStateListener;
    }

    public void setScrollChageListener(OnScrollChageListener scrollChageListener) {
        mScrollChageListener = scrollChageListener;
    }

    public void cancelDrag() {
        mViewDragHelper.cancel();
    }

    private boolean isRefresh=false;
    public void isRefresh(boolean b) {
        isRefresh=b;
    }


    public interface OnStateListener {
        void open();

        void close();
    }

    public interface OnScrollChageListener {
        void onScrollChange(float rate);
    }


    public PullUpDragLayout(Context context) {
        this(context, null, 0);
    }

    public PullUpDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullUpDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init(context);
        initCustomAttrs(context, attrs);
    }

    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mViewDragHelper = MyViewDragHelper.create(this, 1.0f, mCallback);

    }

    MyViewDragHelper.Callback mCallback = new MyViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.i(TAG, "tryCaptureView: -----");
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
            Log.i(TAG, "clampViewPositionHorizontal: ----------");
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mBottomView.getWidth() - leftBound;
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.i(TAG, "clampViewPositionVertical: ------------");
            int topBound = mContentView.getHeight() - mBottomView.getHeight();
            int bottomBound = mContentView.getHeight() - mBottomBorderHeigth;
            return Math.min(bottomBound, Math.max(top, topBound));
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i(TAG, "onViewPositionChanged: ------------");
            if (changedView == mBottomView) {
                float startPosition = mContentView.getHeight() - mBottomView.getHeight();
                float endPosition = mContentView.getHeight() - mBottomBorderHeigth;
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

            Log.i(TAG, "onViewReleased: -------------："+yvel+"----:"+ (mContentView.getHeight()-mBottomView.getTop()));

            if(isRefresh){
                return;
            }

            if (releasedChild == mBottomView) {
                if(isOpen){
                    if(Math.abs(mBottomView.getTop()-(mContentView.getHeight()-dip2px(450)))>dip2px(50)){
                        mViewDragHelper.settleCapturedViewAt(mAutoBackBottomPos.x, mContentView.getHeight()-dip2px(50));
                        isOpen = false;
                        if (mOnStateListener != null) mOnStateListener.close();
                    }else{
                        mViewDragHelper.settleCapturedViewAt(mAutoBackTopPos.x, mAutoBackTopPos.y);
                        isOpen = true;
                        if (mOnStateListener != null) mOnStateListener.open();
                    }
                }else{
                    if(Math.abs(mBottomView.getTop()-(mContentView.getHeight()-dip2px(50)))>dip2px(50)){
                        mViewDragHelper.settleCapturedViewAt(mAutoBackTopPos.x, mAutoBackTopPos.y);
                        isOpen = true;
                        if (mOnStateListener != null) mOnStateListener.open();

                    }else{
                        mViewDragHelper.settleCapturedViewAt(mAutoBackBottomPos.x, mContentView.getHeight()-dip2px(50));
                        isOpen = false;
                        if (mOnStateListener != null) mOnStateListener.close();
                    }
                }

                invalidate();
            }

        }


    };

    public boolean isOpen() {
        return isOpen;
    }

    // 初始动画  底部划出来
    public void startAni() {
        Log.i(TAG, "startAni: ----------");

        mViewDragHelper.smoothSlideViewTo(mBottomView, mAutoBackBottomPos.x, mContentView.getHeight()-dip2px(50));
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
            mViewDragHelper.smoothSlideViewTo(mBottomView, mAutoBackBottomPos.x, mContentView.getHeight()-dip2px(50));
            if (mOnStateListener != null) mOnStateListener.close();
        } else {
            mViewDragHelper.smoothSlideViewTo(mBottomView, mAutoBackTopPos.x, mAutoBackTopPos.y);
            if (mOnStateListener != null) mOnStateListener.open();
        }
        invalidate();
        isOpen = !isOpen;
    }


    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullUpDragLayout);
        if (typedArray != null) {
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_ContentView)) {
                inflateContentView(typedArray.getResourceId(R.styleable.PullUpDragLayout_PullUpDrag_ContentView, 0));
            }
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_BottomView)) {
                inflateBottomView(typedArray.getResourceId(R.styleable.PullUpDragLayout_PullUpDrag_BottomView, 0));
            }
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_BottomBorderHeigth)) {
                mBottomBorderHeigth = (int) typedArray.getDimension(R.styleable.PullUpDragLayout_PullUpDrag_BottomBorderHeigth, 20);
            }
            typedArray.recycle();
        }

    }

    private void inflateContentView(int resourceId) {
        mContentView = mLayoutInflater.inflate(resourceId, this, true);
    }

    private void inflateBottomView(int resourceId) {
        mBottomView = mLayoutInflater.inflate(resourceId, this, true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentView = getChildAt(0);
        mBottomView = getChildAt(1);
        measureChild(mBottomView, widthMeasureSpec, heightMeasureSpec);
        int bottomViewHeight = mBottomView.getMeasuredHeight();

        measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
        int contentHeight = mContentView.getMeasuredHeight();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), bottomViewHeight + contentHeight + getPaddingBottom() + getPaddingTop());


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout: -----------");
        if(isRefresh){
            return;
        }
        mContentView = getChildAt(0);
        mBottomView = getChildAt(1);
        mContentView.layout(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), mContentView.getMeasuredHeight());
        mBottomView.layout(getPaddingLeft(), mContentView.getHeight(), getWidth() - getPaddingRight(), getMeasuredHeight() );
        mAutoBackBottomPos.x = mBottomView.getLeft();
        mAutoBackBottomPos.y = mBottomView.getTop();

        mAutoBackTopPos.x = mBottomView.getLeft();
        mAutoBackTopPos.y = mContentView.getHeight() - mBottomView.getHeight();
        mBoundTopY = mContentView.getHeight() - mBottomView.getHeight() / 2;


        startAni();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}

