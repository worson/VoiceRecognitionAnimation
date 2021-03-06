package com.aispeech.voicerecognitionanimation.voice;

/**
 * author       : wangshengxing;
 * date         : 2017/4/11;
 * email        : wangshengxing@haloai.com;
 * package_name : com.aispeech.voicerecognitionanimation;
 * project_name : VoiceRecognitionAnimation;
 */
public interface IVoiceStatusViewLister {
    void onStart();//开始录音
    void onRecognize();//正常识别
    void onStop();//停止识别
    void onUpdateVolume(int vol);//输入的接收范围为0~100
    void onShow();//完成识别
    void onTTS(); //语音播报
}
