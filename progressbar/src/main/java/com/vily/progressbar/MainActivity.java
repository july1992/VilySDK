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
import com.vily.progressbar.adapt.MySection;
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
    private MyPopuWindow mPopuWindow;


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
        mBtn_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mMyLayout.show(mMyLayout);

                if(mPopuWindow==null){
                    mPopuWindow = new MyPopuWindow(MainActivity.this);
                }else{
                    mPopuWindow.show();
                }



            }
        });

    }

    private void setPosition() {
        mMyLayout.startShow(mMyLayout);

        rv_recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        List<MySection> sections=new ArrayList<>();
        List<Student> students=new ArrayList<>();
        students.add(new Student("1231313","张三"));
        students.add(new Student("1231314","张4"));
        students.add(new Student("1231315","张5"));
        students.add(new Student("1231315","张7"));
        students.add(new Student("1231315","张8"));
        students.add(new Student("1231318","张9"));
        students.add(new Student("1231319","张14"));
        students.add(new Student("1231310","2313"));

        for(int i=0;i<students.size();i++){
            MySection section;
            if(i==0 ||!students.get(i).getData().equals(students.get(i-1).getData()) ){
                section= new MySection(true, students.get(i).getData());
                sections.add(section);
            }

            section = new MySection(students.get(i));
            sections.add(section);

        }

//
//        section.t=new Student("asdasdad","1111");
//
//        sections.add(new MySection(true,"head"));
//        sections.add(new MySection(new Student("1231313","张三")));
//        sections.add(new MySection(new Student("1231313","张三")));
//        sections.add(new MySection(new Student("1231313","张三")));
//        sections.add(new MySection(true,"head"));
//        sections.add(new MySection(true,"head"));
//        sections.add(new MySection(true,"head"));
//


        MyAdapter myAdapter = new MyAdapter(sections);
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
