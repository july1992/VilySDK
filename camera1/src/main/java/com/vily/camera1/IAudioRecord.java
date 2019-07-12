package com.vily.camera1;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2018/12/27
 *  
 **/
public interface IAudioRecord {
    // 音频错误
    void onAudioError(int what, String message);

    // 接收音频数据
    void receiveAudioData(byte[] sampleBuffer, int len);
}
