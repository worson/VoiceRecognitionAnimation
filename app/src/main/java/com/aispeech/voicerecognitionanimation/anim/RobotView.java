
package com.aispeech.voicerecognitionanimation.anim;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aispeech.voicerecognitionanimation.R;
import com.aispeech.voicerecognitionanimation.voice.IVoiceStatusViewLister;


/**
 * @desc 显示语音识别状态的MicphoneView。继承自RelativeLayout。
 * @auth AISPEECH
 * @date 2016-01-13
 * @copyright aispeech.com
 */
public class RobotView extends RelativeLayout implements IVoiceStatusViewLister {

    public static final String TAG = "AIOS-Adapter-RobotView";

    private ImageView mRobotIV;
    private AnimationDrawable anim;
    private Handler mHandler = new Handler();
    private boolean isShowSampleUI = false;

    /**
     * 当前麦克风状态
     */
    private MicState micState = new MicState();

    public RobotView(Context context) {
        this(context, null);
    }

    public RobotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RobotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        mRobotIV = (ImageView) findViewById(R.id.i_assist);
        isShowSampleUI = false;
        if (!isShowSampleUI) {
            mRobotIV.setImageResource(R.drawable.anima_mic2listen_0);
        } else {
            mRobotIV.setImageResource(R.drawable.anim_mic_sample);
        }
        super.onFinishInflate();
    }

    @Override
    public void onStart() {
        startListening();
    }

    @Override
    public void onRecognize() {
        startRecognition();
    }

    @Override
    public void onStop() {
        stopListeningOrRecognition();
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onTTS() {

    }

    @Override
    public void onUpdateVolume(int vol) {

    }

    /**
     * 启动录音机动画，倾听状态
     */
    public void startListening() {
        if (micState.getCurrState() == micState.STATE_STOP) {
            if (!isShowSampleUI) {
                mRobotIV.setImageResource(R.drawable.anim_mic_2_listen);//R.drawable.anim_mic_start
                anim = (AnimationDrawable) mRobotIV.getDrawable();
                anim.start();
                if (Build.VERSION.SDK_INT >= 21) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (micState.getCurrState() == micState.STATE_LISTEN) {
                                mRobotIV.setImageResource(R.drawable.anim_listen);
                                anim = (AnimationDrawable) mRobotIV.getDrawable();
                                anim.start();
                            }
                        }
                    }, 320);
                }
            } else {
                mRobotIV.setImageResource(R.drawable.anim_listen_sample);
                anim = (AnimationDrawable) mRobotIV.getDrawable();
                anim.start();
            }
        }

        micState.updateCurrState(micState.STATE_LISTEN);
    }

    /**
     * 显示识别动画
     */
    public void startRecognition() {
        if (micState.getCurrState() == micState.STATE_LISTEN) {
            if (!isShowSampleUI) {
                mRobotIV.setImageResource(R.drawable.anim_listen_2_loading);
                anim = (AnimationDrawable) mRobotIV.getDrawable();
                anim.start();
                if (Build.VERSION.SDK_INT >= 21) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (micState.getCurrState() == micState.STATE_RECOGNITION) {
                                mRobotIV.setImageResource(R.drawable.anim_loading);
                                anim = (AnimationDrawable) mRobotIV.getDrawable();
                                anim.start();
                            }
                        }
                    }, 360);
                }
            } else {
                mRobotIV.setImageResource(R.drawable.anim_loading_sample);
                anim = (AnimationDrawable) mRobotIV.getDrawable();
                anim.start();
            }
        }

        micState.updateCurrState(micState.STATE_RECOGNITION);
    }

    /**
     * 启动听歌识曲动画
     */
    public void startListenToSongsAnim(){
        mRobotIV.setImageResource(isShowSampleUI ? R.drawable.anim_loading_sample : R.drawable.anim_loading);
        anim = (AnimationDrawable) mRobotIV.getDrawable();
        anim.start();
        micState.updateCurrState(micState.STATE_RECOGNITION);
    }

    /**
     * 识别/监听结束动画
     */
    public void stopListeningOrRecognition() {
        if (!isShowSampleUI) {
            if (micState.getCurrState() == micState.STATE_LISTEN) {
                mRobotIV.setImageResource(R.drawable.anim_listen_2_mic);
                anim = (AnimationDrawable) mRobotIV.getDrawable();
                anim.start();
            } else if (micState.getCurrState() == micState.STATE_RECOGNITION) {
                mRobotIV.setImageResource(R.drawable.anim_loading_2_mic);
                anim = (AnimationDrawable) mRobotIV.getDrawable();
                anim.start();
            } else {
                mRobotIV.setImageResource(R.drawable.anima_mic2listen_0);
            }
        } else {
            mRobotIV.setImageResource(R.drawable.anim_mic_sample);
        }

        micState.updateCurrState(micState.STATE_STOP);
    }

    /**
     * mic当前状态
     * @return 3 停止; 1 监听; 2 识别
     */
    public int getCurrMicState(){
        if (micState.getCurrState() == micState.STATE_LISTEN) {
            return 1;
        } else if (micState.getCurrState() == micState.STATE_RECOGNITION) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * 麦克风状态
     */
    final class MicState {
        /**
         * 监听状态
         */
        int STATE_LISTEN = 1;
        /**
         * 识别状态
         */
        int STATE_RECOGNITION = 2;
        /**
         * 停止状态
         */
        int STATE_STOP = 3;

        private int currState = STATE_STOP;

        /**
         * 修改状态
         *
         * @param state
         */
        void updateCurrState(int state) {
            currState = state;

            //AILog.d(TAG, "anim mic state:" + (currState == STATE_LISTEN ? "STATE_LISTEN" : (currState == STATE_RECOGNITION ? "STATE_RECOGNITION" : "STATE_STOP")));
        }

        int getCurrState() {
            return currState;
        }
    }
}
