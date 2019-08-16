package com.vily.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MusicActivtiy extends AppCompatActivity {

    private static final String TAG = "MusicActivtiy";

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

            MusicService2.MusicBinder myBinder = (MusicService2.MusicBinder)binder;
            mService = myBinder.getService();

            int currPosition = mService.getCurrPosition();
            boolean mediaPlay = mService.isMediaPlay();

            mService.setOnNextChangeListener(new MusicService2.OnNextChangeListener() {
                @Override
                public void onNextChenge(int lastP, int currP) {
                    Log.i(TAG, "onNextChenge: --------:"+lastP+"-----:"+currP);
                }

                @Override
                public void onState(int currP, boolean state) {

                }
            });
            Log.i(TAG, "onServiceConnected: --------:"+currPosition+"------:"+mediaPlay);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: --------");
            mService = null;
        }
    };
    private MusicService2 mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ignoreBatteryOptimization(this);

        Button btn_start = findViewById(R.id.btn_start);
        Button btn_stop = findViewById(R.id.btn_stop);
        Button btn_restart = findViewById(R.id.btn_restart);
        Button btn_close = findViewById(R.id.btn_close);


        Intent intent = new Intent(this, MusicService2.class);
        bindService(intent, conn, BIND_AUTO_CREATE);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MusicActivtiy.this,MusicService2.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                }else{
                    startService(intent);
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.stopMusic();
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.restart();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(conn);
            }
        });


    }


    // 忽略电池优化
    public static void ignoreBatteryOptimization(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            PowerManager powerManager = (PowerManager) activity.getSystemService(POWER_SERVICE);

            boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            if (!hasIgnored) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(conn);
    }
}

