package com.aispeech.voicerecognitionanimation.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 */
public class WaveView extends View implements View.OnClickListener {

    private boolean mIsFillRect = false;

    //波浪画笔
    private Paint mPaint;
    //测试红点画笔
    private Paint mCyclePaint;

    //波浪Path类
    private Path mPath;
    //一个波浪长度
    private int mWaveLength = 500;
    //波纹个数
    private int mWaveCount;
    //平移偏移量
    private int mOffset;
    //波纹的中间轴
    private int mCenterY;

    //屏幕高度
    private int mScreenHeight;
    //屏幕宽度
    private int mScreenWidth;

    /**
     * 传进来的最大音量
     */
    private float maxVolume = 100;

    /**
     * 振幅
     */
    private float amplitude = 100;
    private float volume = 10;
    private float targetVolume = 1;

    private ValueAnimator mVolumeanimator;
    private int mVolumDuration = 300;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        setOnClickListener(this);

//        mCyclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mCyclePaint.setColor(Color.RED);
//        mCyclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    public void setVolume(final float volume) {
        this.volume = volume;
    }

    public void updateVolume(final float volume) {
        if (mVolumeanimator == null) {
            mVolumeanimator = ValueAnimator.ofFloat(this.volume, volume);
            mVolumeanimator.setDuration(mVolumDuration);
            mVolumeanimator.setInterpolator(new LinearInterpolator());
            mVolumeanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setVolume((Float) animation.getAnimatedValue());
                }
            });
        }else {
            if (mVolumeanimator.isStarted()) {
                mVolumeanimator.cancel();
                mVolumeanimator.setFloatValues(this.volume, volume);
                mVolumeanimator.setDuration(mVolumDuration);
            }
        }
        mVolumeanimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        //加1.5：至少保证波纹有2个，至少2个才能实现平移效果
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
        mCenterY = mScreenHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        amplitude=volume*2;
        mPath.reset();
        //移到屏幕外最左边
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            //正弦曲线
            mPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset, mCenterY + amplitude, (-mWaveLength / 2) + (i * mWaveLength) + mOffset, mCenterY);
            mPath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mOffset, mCenterY - amplitude, i * mWaveLength + mOffset, mCenterY);
            //测试红点
//            canvas.drawCircle((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset, mCenterY + 60, 5, mCyclePaint);
//            canvas.drawCircle((-mWaveLength / 2) + (i * mWaveLength) + mOffset, mCenterY, 5, mCyclePaint);
//            canvas.drawCircle((-mWaveLength / 4) + (i * mWaveLength) + mOffset, mCenterY - 60, 5, mCyclePaint);
//            canvas.drawCircle(i * mWaveLength + mOffset, mCenterY, 5, mCyclePaint);
        }
        //填充矩形
        if(mIsFillRect) {
            mPath.lineTo(mScreenWidth, mScreenHeight);
            mPath.lineTo(0, mScreenHeight);
            mPath.close();
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 开始动画
     */
    public void start(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
    @Override
    public void onClick(View view) {

    }
}
