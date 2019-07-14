package com.vily.progressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vily.progressbar.adapt.MyAdapter;
import com.vily.progressbar.adapt.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MyProgressBarUI mPb;
    private LinearLayout mLly;
    private TextView mTv_title;
    private int mHeight;
    private MyBottomLayout mMyLayout;
    private Button mBtn_rotate;

    private boolean start=true;
    private RelativeLayout mRoot;
    private ReportLayout mReportLayout;
    private SettingWindow mSettingWindow;
    private RecyclerView rv_recycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mPb = findViewById(R.id.mPb);
        mLly = findViewById(R.id.lly);
        mTv_title = findViewById(R.id.tv_title);
        mMyLayout = findViewById(R.id.myLayout);
        mBtn_rotate = findViewById(R.id.btn_rotate);
        mRoot = findViewById(R.id.root);
        rv_recycle = findViewById(R.id.rv_recycle);


        initData();

        setPosition();

    }

    private void setPosition() {
        mMyLayout.startShow(mMyLayout);

        rv_recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        List<Student> students=new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        MyAdapter myAdapter = new MyAdapter(students);
        rv_recycle.setAdapter(myAdapter);
        myAdapter.openLoadAnimation((BaseQuickAdapter.ALPHAIN));
    }

    private void initData() {

        List<User> data=new ArrayList<>();
        data.add(new User(20,"aaa"));
        data.add(new User(60,"aaa"));
        data.add(new User(20,"bbb"));
        data.add(new User(50,"bbb"));
        data.add(new User(20,"ccc"));
        data.add(new User(10,"ccc"));
        data.add(new User(90,"ddd"));

        mPb.setData(data);
    }


}
