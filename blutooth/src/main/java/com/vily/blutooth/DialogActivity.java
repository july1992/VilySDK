package com.vily.blutooth;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/30
 *  
 **/
public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        getWindow().getAttributes().windowAnimations = R.style.ActivityFade;


    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, R.anim.push_bottom_out);
//        getWindow().getAttributes().windowAnimations = R.style.ActivityFade;
    }
}
