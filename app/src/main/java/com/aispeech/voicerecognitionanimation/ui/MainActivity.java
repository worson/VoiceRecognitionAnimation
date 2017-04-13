package com.aispeech.voicerecognitionanimation.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aispeech.voicerecognitionanimation.R;
import com.aispeech.voicerecognitionanimation.anim.RobotView;
import com.aispeech.voicerecognitionanimation.ui.style.VoiceWaterView;
import com.aispeech.voicerecognitionanimation.voice.IVoiceStatusViewLister;

public class MainActivity extends Activity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private IVoiceStatusViewLister mVoiceStatusViewLister = null;
    private ViewGroup mVoiceViewGroup = null;
    private View mVoiceView = null;

    private Class[] mVoiceAnimSytleClass = new Class[]{VoiceWaterView.class};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSpinner();
        initButton();

        mVoiceViewGroup = (ViewGroup)findViewById(R.id.voice_layout);
        mVoiceView = new RobotView(getApplicationContext());

        mVoiceView = getLayoutInflater().inflate(R.layout.robot_view,null,false);
        TextView textView = new TextView(getApplicationContext());
        textView.setText("您好");
        textView.setTextColor(Color.RED);

        mVoiceViewGroup.addView(mVoiceView);
        mVoiceStatusViewLister = (IVoiceStatusViewLister) mVoiceView;
    }

    private void initSpinner() {
        Spinner spinner = (Spinner)findViewById(R.id.voice_style_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

//                String[] languages = getResources().getStringArray(R.array.languages);
//                Toast.makeText(MainActivity.this, "你点击的是:"+pos, 2000).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Log.d(TAG, "onClick:  id is "+id);
        switch (id){
            case R.id.button_stop:
                mVoiceStatusViewLister.onStop();
                break;
            case R.id.button_start:
                mVoiceStatusViewLister.onStart();
                break;
            case R.id.button_recognize:
                mVoiceStatusViewLister.onRecognize();
                break;
            case R.id.button_show:
                break;
            case R.id.button_tts:
                break;
        }
    }

    private void initButton() {
        int[] buttonIds = new int[]{R.id.button_stop,R.id.button_start,R.id.button_recognize,R.id.button_show,R.id.button_tts};
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this);
        }
    }
}
