package com.vily.vilysdk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_camera1, btn_camera2, video_audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_camera1 = findViewById(R.id.btn_camera1);
        btn_camera2 = findViewById(R.id.btn_camera2);
        video_audio = findViewById(R.id.video_audio);

        btn_camera1.setOnClickListener(this);
        btn_camera2.setOnClickListener(this);
        video_audio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        initPermission();
        switch (v.getId()){
            case R.id.btn_camera1 :   // 原生 camera1
                Intent intent=new Intent(MainActivity.this,Camera1Activity.class);
                startActivity(intent);
                break;

            case R.id.btn_camera2 :   // camera2
                Intent intent2=new Intent(MainActivity.this,Camera2Actvitiy.class);
                startActivity(intent2);
                break;

            case R.id.video_audio:
                break;
            default :
                break;
        }
    }


    protected void initPermission() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        int flag2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag2) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }
}
