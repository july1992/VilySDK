package com.vily.camera1;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.vily.vilysdk2.DeviceUtils;

import java.io.IOException;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/5/29
 *  
 **/
public class Camera1Utils implements Camera.PreviewCallback {
    private static final String TAG = "Camera1Utils";

    protected Camera mCamera;

    private Camera.Parameters parameters;

    private Context mContext;

    private static Camera1Utils mCamera1Utils;

    private int mCameraId=0;  // 0是后置camera  1是前置camera

    private SurfaceHolder mSurfaceHolder;

    private static int mWith=720;
    private static int mHeight=480;


    public Camera1Utils() {
    }


    public void setPreviewSize(int VIDEO_With, int VIDEO_Height){
        mWith=VIDEO_With;
        mHeight=VIDEO_Height;
    }

    public Camera1Utils(Context context) {
        mContext = context;
    }

    public static Camera1Utils getInstance(Context context){

        if(mCamera1Utils==null){

            mCamera1Utils=new Camera1Utils(context);
        }
        return mCamera1Utils;

    }


    // 初始化照相机
    public void initCamera(SurfaceHolder surfaceHolder){
        mSurfaceHolder=surfaceHolder;
        mCamera = Camera.open(mCameraId);
//        setCameraDisplayOrientation();
        parameters = mCamera.getParameters();
        parameters.setPreviewFormat(ImageFormat.NV21);
        String mode = getAutoFocusMode();
        if (!TextUtils.isEmpty(mode)) {
            parameters.setFocusMode(mode);
        }
        if (isSupported(parameters.getSupportedWhiteBalance(), "auto"))
            parameters.setWhiteBalance("auto");
        //是否支持视频防抖
        if ("true".equals(parameters.get("video-stabilization-supported")))
            parameters.set("video-stabilization", "true");
        if (!DeviceUtils.isDevice("GT-N7100", "GT-I9308", "GT-I9300")) {
            parameters.set("cam_mode", 1);
            parameters.set("cam-mode", 1);
        }


        parameters.setPreviewSize(mWith, mHeight);
        List<Camera.Size> mSizeList = parameters.getSupportedPreviewSizes();
        for(Camera.Size size:mSizeList){
            Log.i(TAG, "getSupportedPreviewSizes: -----------size:"+size.width+"---"+size.height);
        }

        parameters.setPreviewFrameRate(25);
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCamera.setPreviewCallback(this);
    }

    //  连续自动对焦 */
    private String getAutoFocusMode() {
        if (parameters != null) {
            //持续对焦是指当场景发生变化时，相机会主动去调节焦距来达到被拍摄的物体始终是清晰的状态。
            List<String> focusModes = parameters.getSupportedFocusModes();
            if ((Build.MODEL.startsWith("GT-I950") || Build.MODEL.endsWith("SCH-I959") || Build.MODEL.endsWith("MEIZU MX3")) && isSupported(focusModes, "continuous-picture")) {
                return "continuous-picture";
            } else if (isSupported(focusModes, "continuous-video")) {
                return "continuous-video";
            } else if (isSupported(focusModes, "auto")) {
                return "auto";
            }
        }
        return null;
    }

    //  检测是否支持指定特性 */
    private boolean isSupported(List<String> list, String key) {
        return list != null && list.contains(key);
    }
    // 因为默认是横着的，所以要旋转
    private void setCameraDisplayOrientation() {
        Display mDisplay =((Activity)mContext).getWindowManager().getDefaultDisplay();
        int orientation = mDisplay.getOrientation();
        Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        Camera.getCameraInfo(1, info);
        int degrees = 0;
        switch (orientation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 270;
                break;
            case Surface.ROTATION_180:
                degrees = 0;
                break;
            case Surface.ROTATION_270:
                degrees = 90;
                break;
            default:
                break;

        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {// back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);
        Log.d(TAG, " orientation: " + orientation);
    }

    //   是否支持前置摄像头
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isSupportFrontCamera() {
        if (!DeviceUtils.hasGingerbread()) {
            return false;
        }
        int numberOfCameras = Camera.getNumberOfCameras();
        if (2 == numberOfCameras) {
            return true;
        }
        return false;
    }


    //  闪光灯
    public boolean changeFlash() {
        boolean flashOn = false;
        if (flashEnable(mContext)) {
            Camera.Parameters params = mCamera.getParameters();
            if (Camera.Parameters.FLASH_MODE_TORCH.equals(params.getFlashMode())) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                flashOn = false;
            } else {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                flashOn = true;
            }
            mCamera.setParameters(params);
        }
        return flashOn;
    }

    //  闪光灯是否可用
    public boolean flashEnable(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
                && mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK;

    }

    //   切换前置/后置摄像头
    public void switchCamera() {
        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {

            mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            destroyCamera();
            if(mSurfaceHolder!=null){
                initCamera(mSurfaceHolder);
                startPreview();
            }
        } else {

            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            destroyCamera();
            if(mSurfaceHolder!=null){
                initCamera(mSurfaceHolder);
                startPreview();
            }
        }
    }



    // 获取预览帧
    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

        if(bytes!=null && bytes.length>0 && mIVedioCallBack!=null){

            mIVedioCallBack.onVedioCallBack(bytes);
        }
    }

    private IVedioCallBack mIVedioCallBack;
    public void setPreviewCallBack(IVedioCallBack iVedioCallBack){
        mIVedioCallBack=iVedioCallBack;
    }

    public void startPreview() {
        if(mCamera!=null){
            mCamera.startPreview();
        }

    }




    public void stopCamera() {
        if (mCamera != null) {

            mCamera.stopPreview();

        }
    }

    // 销毁camera 不然其他设备不能用
    public void destroyCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }


}
