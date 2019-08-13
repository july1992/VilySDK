package com.vily.scoroll;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private MyRecycleView mRc_recycle;

    private FrameLayout mRly_root;
    private PullUpDragLayout2 mDragLayout;
    private MySwip mRefresh;
    private PullUpDragLayout2 mPull_up_drag_layout;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mRc_recycle = findViewById(R.id.rv_recycle);
        mRly_root = findViewById(R.id.rly_root);
        mRefresh = findViewById(R.id.refresh);

//        mPull_up_drag_layout = findViewById(R.id.pull_up_drag_layout);

        TextView btn_click = findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ---------xxxxxxx");
            }
        });

//        mPull_up_drag_layout.initData(mPull_up_drag_layout);

        mRly_root.post(new Runnable() {
            @Override
            public void run() {

                Log.i(TAG, "onCreate: ------:" + mRly_root.getHeight());

                mDragLayout = new PullUpDragLayout2(MainActivity.this, mRly_root.getHeight());

                mRly_root.addView(mDragLayout);



            }
        });

//        mRefresh.setEnabled(false);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRefresh.setRefreshing(false);
            }
        });

        TestAdapter adapter = new TestAdapter(null);

        List<String> list = new ArrayList<>();
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        list.add("sssss");
        adapter.setNewData(list);
        mRc_recycle.setLayoutManager(new LinearLayoutManager(this));
        mRc_recycle.setAdapter(adapter);

    }

    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    private float mStart;
    private int top;
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mStart = event.getY();
//                top = mDragLayout.getBottomTop();
//                break;
//        }
//        Log.i(TAG, "dispatchTouchEvent: ------:"+mRly_root.getHeight()+"----:"+mStart+"---:"+top);
//
//        if(mStart>top){
//            return mDragLayout.dispatchTouchEvent(event);
//        }else{
//            return mRly_root.dispatchTouchEvent(event);
//        }
//    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mStart = event.getY();
//                top = mDragLayout.getBottomTop();
//
//        }
//        Log.i(TAG, "dispatchTouchEvent: ------:" + mRly_root.getHeight() + "----:" + mStart + "---:" + top);
//
//        if (mStart > top) {
//            return mDragLayout.dispatchTouchEvent(event);
//        } else {
//            return mRly_root.dispatchTouchEvent(event);
//        }
//
//        return super.onTouchEvent(event);
//    }
}

