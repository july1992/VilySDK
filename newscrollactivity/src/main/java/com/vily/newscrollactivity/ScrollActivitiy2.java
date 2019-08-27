package com.vily.newscrollactivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-27
 *  
 **/
public class ScrollActivitiy2  extends AppCompatActivity {


    private static final String TAG = "ScrollActivitiy2";
    private ImageView mIv_img;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_scro2);
        mIv_img = findViewById(R.id.iv_img);
        RecyclerView refresh = findViewById(R.id.rv_recycle);

        final RecyclerView rv_recycle = findViewById(R.id.rv_recycle);
        rv_recycle.setLayoutManager(new LinearLayoutManager(ScrollActivitiy2.this));


        List<String> list=new ArrayList<>();
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
        list.add("wwwwwwww");
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


        refresh.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);



            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        refresh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "onTouch: -----");

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        start=event.getY();
                        break;

                    case MotionEvent.ACTION_UP :
                        float end = event.getY();


                        isUp = end-start<0;

                        boolean b = rv_recycle.canScrollVertically(-1);

                        isTop=!b;

                        Log.i(TAG, "onScrollStateChanged: ----:"+isTop+"---："+show+"----:"+isUp);

                        if(isUp){
                            if(show){
                                show=false;
                                
                                mIv_img.animate()
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .alpha(1f)
                                        .translationYBy(-50)
                                        .setInterpolator(LINEAR_INTERRPLATOR)
                                        .setDuration(600)
                                        .start();
                            }
                        }else{
                            if(!show){
                                show=true;
                                mIv_img.animate()
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .alpha(1f)
                                        .translationYBy(50)
                                        .setInterpolator(LINEAR_INTERRPLATOR)
                                        .setDuration(600)
                                        .start();
                            }
                        }

                        break;

                }
                return false;
            }
        });
    }

    private float start=0;
    private boolean isTop=true;
    private boolean show=true;
    private boolean isUp=false;
    private static AccelerateDecelerateInterpolator LINEAR_INTERRPLATOR =new AccelerateDecelerateInterpolator();
}
