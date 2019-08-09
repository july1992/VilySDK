package com.vily.file;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ShakeDetector1 shakeDetector;
    private Button mBtn_start;
    private Button mBtn_close;
    private int mCurr = 0;

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/senblottt/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn_start = findViewById(R.id.btn_start);
        mBtn_close = findViewById(R.id.btn_close);


        mBtn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    //申请权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

                } else {

                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File[] files = dir.listFiles();
                    if (files == null || files.length == 0) {
                        // 子文件目录从0开始
                        mCurr = 0;
                    } else {
                        mCurr = files.length;
                    }
                    final File childDir = new File(path + "/s" + mCurr);
                    if (!childDir.exists()) {
                        childDir.mkdirs();
                    }
                    Log.i(TAG, "onClick: -------:"+mCurr +"----"+ childDir.getAbsolutePath());


                    mBtn_start.setEnabled(false);
//
//                    for (int i = 0; i < 50; i++) {
//                        FileUtils.write(childDir, "/s"+mCurr+"_delta.txt", (int) (Math.random()*100));
//                        FileUtils.write(childDir, "/s"+mCurr+"_x1.txt",  (int) (Math.random()*100));
//                        FileUtils.write(childDir, "/s"+mCurr+"_x2.txt",  (int) (Math.random()*100));
//                    }

                    if (shakeDetector == null) {
                        Log.i(TAG, "onCreate: --------xxxx");

                        shakeDetector = new ShakeDetector1(MainActivity.this);
                        shakeDetector.registerOnShakeListener(new ShakeDetector1.OnShakeListener() {

                            @Override
                            public void onData(int delta, int x1, int x2) {

                                Log.i(TAG, "onData: ------:" + delta + "-----" + x1 + "----"+x2);

                                FileUtils.write(childDir.getAbsolutePath(), "/s"+mCurr+"_delta.txt", delta);
                                FileUtils.write(childDir.getAbsolutePath(), "/s"+mCurr+"_x1.txt", x1);
                                FileUtils.write(childDir.getAbsolutePath(), "/s"+mCurr+"_x2.txt", x2);

                            }
                        });
                    }
                    shakeDetector.start();
                }
            }
        });

        mBtn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurr = 0;
                if (!mBtn_start.isEnabled()) {
                    mBtn_start.setEnabled(true);
                }
                if (shakeDetector != null) {
                    shakeDetector.stop();
                    shakeDetector=null;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(shakeDetector!=null){
            shakeDetector.stop();
            shakeDetector=null;
        }
    }
}
