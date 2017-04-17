package com.aispeech.voicerecognitionanimation.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.aispeech.voicerecognitionanimation.R;
import com.aispeech.voicerecognitionanimation.ui.style.VoiceWaterView;
import com.aispeech.voicerecognitionanimation.ui.style.WaveViewAdapter;
import com.aispeech.voicerecognitionanimation.ui.view.WaveView;
import com.aispeech.voicerecognitionanimation.voice.IVoiceStatusViewLister;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener ,Runnable{

    public static final String TAG = MainActivity.class.getSimpleName();


    //语音输入
    private MediaRecorder mMediaRecorder;
    private boolean isAlive = true;


    //收音动画
    private IVoiceStatusViewLister mVoiceStatusViewLister = null;
    private ViewGroup mVoiceViewGroup = null;

    private View mVoiceView = null;

    private WaveViewAdapter mWaveViewAdapter;
    private WaveView voiceLineView;

    private static final int MODE_TURE_VOICE = 0;
    private static final int MODE_SIMU_VOICE = 1;
    private static final int MODE_NO_VOICE = 2;
    private int mVoiceType = MODE_TURE_VOICE;


    private Class[] mVoiceAnimSytleClass = new Class[]{VoiceWaterView.class};


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / 100;
            double db = 0;// 分贝
            if (ratio > 1)
                db = 30 * Math.log10(ratio);
            if(mVoiceType == MODE_TURE_VOICE) {
                if (mVoiceStatusViewLister != null) {
                    mVoiceStatusViewLister.onUpdateVolume((int) db);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSeekBar();
        initMediaRecorder();
        initSpinner();
        initButton();


    }

    private void initSeekBar() {
        SeekBar seekBar = (SeekBar)findViewById(R.id.speech_seekbar);
        seekBar.setProgress(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mVoiceType == MODE_SIMU_VOICE) {
                    if (mVoiceStatusViewLister != null) {
                        mVoiceStatusViewLister.onUpdateVolume(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initMediaRecorder() {
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();

            /* ②setAudioSource/setVedioSource */
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            /* ③准备 */
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "hello.log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMediaRecorder.setOutputFile(file.getAbsolutePath());
        mMediaRecorder.setMaxDuration(1000 * 60 * 10);
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
            /* ④开始 */
        try {
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(this);
        thread.start();
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
                        clearSpeechViewGroup();

                        if (mVoiceView==null) {
                            mVoiceView = getLayoutInflater().inflate(R.layout.robot_view,null,false);
                        }
                        mVoiceViewGroup.addView(mVoiceView);
                        mVoiceStatusViewLister = (IVoiceStatusViewLister) mVoiceView;
                        break;
                    case 1:
                        startActivity(OptimizeFrameAnimationActivity.class);
                        break;
                    case 2:
                        startActivity(VoiceLineActivity.class);
                        break;
                    case 3:
                        clearSpeechViewGroup();
                        if (mWaveViewAdapter==null) {
                            mWaveViewAdapter = new WaveViewAdapter(getApplicationContext());
                        }
                        mVoiceViewGroup.addView(mWaveViewAdapter.getWaveView());
                        mVoiceStatusViewLister= mWaveViewAdapter;
//                        startActivity(WaveViewActivity.class);
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

    private void clearSpeechViewGroup() {
        if (mVoiceViewGroup==null) {
            mVoiceViewGroup = (ViewGroup)findViewById(R.id.voice_layout);
        }
        if (mVoiceViewGroup.getChildCount()>0) {
            mVoiceViewGroup.removeAllViews();
        }
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

    @Override
    public void run() {
        while (isAlive) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mMediaRecorder.stop();
        super.onDestroy();
    }
}
