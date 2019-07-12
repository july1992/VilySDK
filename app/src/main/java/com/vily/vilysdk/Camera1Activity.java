package com.vily.vilysdk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.vily.camera1.Camera1Utils;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/5/29
 *  
 **/
public class Camera1Activity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera1Utils mCamera1Utils;
    private static final String TAG = "Camera1Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera1);

        SurfaceView surface = findViewById(R.id.surface);

        mCamera1Utils = Camera1Utils.getInstance(Camera1Activity.this);
        mCamera1Utils.setPreviewSize(1280,720);

        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Log.i(TAG, "surfaceCreated: --------1");
        mCamera1Utils.initCamera(holder);
        mCamera1Utils.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera1Utils.stopCamera();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCamera1Utils.destroyCamera();
    }
}
