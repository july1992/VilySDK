package com.vily.service.shake;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * 用于检测手机摇晃
 */
public class ShakeDetector implements SensorEventListener {
    /**
     * 检测的时间间隔
     */
    static final int UPDATE_INTERVAL = 200;
    /**
     * 上一次检测的时间
     */
    long mLastUpdateTime;
    /**
     * 上一次检测时，加速度在x、y、z方向上的分量，用于和当前加速度比较求差。
     */
    float mLastX, mLastY, mLastZ;
    Context mContext;
    SensorManager mSensorManager;
    ArrayList<OnShakeListener> mListeners;
    /**
     * 摇晃检测阈值，决定了对摇晃的敏感程度，越小越敏感。
     */
    public float shakeThreshold = 1.5f;

    public ShakeDetector(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        mListeners = new ArrayList<OnShakeListener>();
    }
    /**
     * 当摇晃事件发生时，接收通知
     */
    public interface OnShakeListener {

        /**
         *
         * @param shake_num 晃动次数
         * @param stat_time 统计时间
         * @param cur_time 记录时间
         */
        void onData(int shake_num, int stat_time, long cur_time);
    }
    /**
     * 注册OnShakeListener，当摇晃时接收通知
     *
     * @param listener
     */
    public void registerOnShakeListener(OnShakeListener listener) {
        if (mListeners.contains(listener))
            return;
        mListeners.add(listener);
    }
    /**
     * 移除已经注册的OnShakeListener
     *
     * @param listener
     */
    public void unregisterOnShakeListener(OnShakeListener listener) {
        mListeners.remove(listener);
    }
    /**
     * 启动摇晃检测
     * SensorManager.SENSOR_DELAY_FASTEST：最快，延迟最小。
     * SensorManager.SENSOR_DELAY_GAME：适合游戏的频率。
     * SensorManager.SENSOR_DELAY_NORMAL：正常频率。
     * SensorManager.SENSOR_DELAY_UI：适合普通用户界面UI变化的频率。
     */
    public void start() {
        if (mSensorManager == null) {
            throw new UnsupportedOperationException();
        }
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null) {
            throw new UnsupportedOperationException();
        }
        boolean success = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (!success) {
            throw new UnsupportedOperationException();
        }
    }
    /**
     * 停止摇晃检测
     */
    public void stop() {
        if (mSensorManager != null)
            mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        long diffTime = currentTime - mLastUpdateTime;
        if (diffTime < UPDATE_INTERVAL)
            return;
        mLastUpdateTime = currentTime;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float deltaX = x - mLastX;
        float deltaY = y - mLastY;
        float deltaZ = z - mLastZ;
        mLastX = x;
        mLastY = y;
        mLastZ = z;
        float delta = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)
                / diffTime * 10000);
        Log.w("delta", ""+delta);
        if (delta < shakeThreshold) { // 当加速度的差值大于指定的阈值，认为这是一个摇晃
            if(time == 0) {
                time = System.currentTimeMillis();
            }
            long curTime = System.currentTimeMillis();
            long l = curTime - time;
            if(l >= split_time) {
                this.notifyListeners(shakeNum, curTime);
                time = 0;
                shakeNum = 0;
            }
        } else {
            shakeNum++;
            Log.w("ffffff", "onShare");
        }
    }
    private long time;
    private int shakeNum;
    private final int split = 10;
    private final int split_time = 1000*split;
    /**
     * 当摇晃事件发生时，通知所有的listener
     */
    private void notifyListeners(int shake_num, long cur_time) {
        for (OnShakeListener listener : mListeners) {
            listener.onData(shake_num, split, cur_time);
        }
    }

}
