package com.vily.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-16
 *  
 **/
public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setAction(MyConfig.LOCK_SCREEN);
        sendBroadcast(intent);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_lock);


    }

    @Override
    protected void onUserLeaveHint() {
        Log.d("lock","onUserLeaveHint");
        super.onUserLeaveHint();

        Intent intent = new Intent();
        intent.setAction(MyConfig.LOCK_SCREEN);
        intent.putExtra("islock",false);
        sendBroadcast(intent);
        finish();
    }
}
