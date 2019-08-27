package com.vily.newscroll2;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    private FrameLayout mLlf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scrolling);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);





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


//        MyViewDragHelper mViewDragHelper = MyViewDragHelper.create(this, 1.0f, mCallback);


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
