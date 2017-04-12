package com.aispeech.voicerecognitionanimation.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.aispeech.voicerecognitionanimation.voice.IVoiceStatusViewLister;
import com.aispeech.voicerecognitionanimation.R;
import com.aispeech.voicerecognitionanimation.ui.style.VoiceWaterView;

public class MainActivity extends AppCompatActivity {

    private IVoiceStatusViewLister mVoiceStatusViewLister = null;
    private ViewGroup mVoiceViewGroup = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mVoiceViewGroup
        mVoiceStatusViewLister = new VoiceWaterView();
    }
}
