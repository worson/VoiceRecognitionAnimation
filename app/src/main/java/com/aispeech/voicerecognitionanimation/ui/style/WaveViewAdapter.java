package com.aispeech.voicerecognitionanimation.ui.style;

import android.content.Context;

import com.aispeech.voicerecognitionanimation.ui.view.WaveView;
import com.aispeech.voicerecognitionanimation.voice.IVoiceStatusViewLister;

/**
 * author       : wangshengxing;
 * date         : 2017/4/11;
 * email        : wangshengxing@haloai.com;
 * package_name : com.aispeech.voicerecognitionanimation;
 * project_name : VoiceRecognitionAnimation;
 */
public class WaveViewAdapter implements IVoiceStatusViewLister {

    private WaveView waveView;

    public WaveViewAdapter(Context context) {
        waveView = new WaveView(context);
        waveView.start();
    }

    public void setWaveView(WaveView waveView) {
        this.waveView = waveView;
    }

    public WaveView getWaveView() {
        return waveView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRecognize() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onTTS() {

    }

    @Override
    public void onUpdateVolume(int vol) {
        waveView.updateVolume(vol);
    }
}
