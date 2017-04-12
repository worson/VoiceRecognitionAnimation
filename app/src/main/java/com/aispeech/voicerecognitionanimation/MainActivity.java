package com.aispeech.voicerecognitionanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private IVoiceStatusViewLister mVoiceStatusViewLister = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceStatusViewLister = new VoiceWaterView();
    }
}
