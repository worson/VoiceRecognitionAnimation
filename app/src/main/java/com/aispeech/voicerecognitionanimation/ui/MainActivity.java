package com.aispeech.voicerecognitionanimation.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aispeech.voicerecognitionanimation.R;
import com.aispeech.voicerecognitionanimation.anim.RobotView;
import com.aispeech.voicerecognitionanimation.ui.style.VoiceWaterView;
import com.aispeech.voicerecognitionanimation.voice.IVoiceStatusViewLister;

public class MainActivity extends Activity {

    private IVoiceStatusViewLister mVoiceStatusViewLister = null;
    private ViewGroup mVoiceViewGroup = null;
    private View mVoiceView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVoiceViewGroup = (ViewGroup)findViewById(R.id.voice_layout);
        mVoiceView = new RobotView(getApplicationContext());

        TextView textView = new TextView(getApplicationContext());
        textView.setText("您好");
        textView.setTextColor(Color.RED);

        mVoiceViewGroup.addView(textView);
        mVoiceStatusViewLister = new VoiceWaterView();
    }
}
