package com.vily.newscrollactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    private CoordinatorLayout mLlf;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scrolling);

//        final ImageView iv_src = findViewById(R.id.iv_src);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("sadsadadad");
        setSupportActionBar(toolbar);

//        FloatingActionButton fab =  findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        NestedScrollView scrollView = findViewById(R.id.scrollView);

        final SwipeRefreshLayout refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
            }
        });
        mLlf = findViewById(R.id.llf);

        mLlf.postDelayed(new Runnable() {
            @Override
            public void run() {
                PullUpDragLayout2 mDragLayout = new PullUpDragLayout2(ScrollingActivity.this, mLlf.getHeight());

                mLlf.addView(mDragLayout);
            }
        },2000);

        RecyclerView rv_recycle = findViewById(R.id.rv_recycle);
        rv_recycle.setLayoutManager(new LinearLayoutManager(ScrollingActivity.this));


        List<String> list=new ArrayList<>();
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        list.add("sadadad");
        TextAdapter adpter=new TextAdapter(list);
        rv_recycle.setAdapter(adpter);






    }
    private static AccelerateDecelerateInterpolator LINEAR_INTERRPLATOR =new AccelerateDecelerateInterpolator();

    private float start=0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
