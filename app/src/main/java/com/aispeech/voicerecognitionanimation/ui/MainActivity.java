package com.aispeech.voicerecognitionanimation.ui;

import android.app.Activity;
import android.content.Intent;
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

    private static final int MODE_TURE_VOICE = 0;
    private static final int MODE_SIMU_VOICE = 1;
    private static final int MODE_NO_VOICE = 2;
    private int mVoiceType = MODE_TURE_VOICE;


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

    private void startActivity(Class<?> cls){
        Intent intent = new Intent(getApplicationContext(),cls);
        startActivity(intent);
    }

    private void initSpinner() {
        Spinner inputSpinner = (Spinner)findViewById(R.id.voice_input_spinner);
        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mVoiceType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.voice_style_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos){
                    case 0:
                        break;
                    case 1:
                        startActivity(OptimizeFrameAnimationActivity.class);
                        break;
                    case 2:
                        startActivity(VoiceLineActivity.class);
                        break;
                    case 3:
                        startActivity(WaveViewActivity.class);
                        break;
                    case 4:
                        break;
                }
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
