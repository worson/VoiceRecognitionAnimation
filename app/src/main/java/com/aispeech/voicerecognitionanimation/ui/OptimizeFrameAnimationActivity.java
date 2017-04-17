package com.aispeech.voicerecognitionanimation.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.aispeech.voicerecognitionanimation.R;

public class OptimizeFrameAnimationActivity extends Activity implements View.OnClickListener{
    private Button normal, advance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize_frame_animation);

        normal = (Button) findViewById(R.id.normal_anim);
        advance = (Button) findViewById(R.id.advance_anim);
        normal.setOnClickListener(this);
        advance.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(OptimizeFrameAnimationActivity.this, TestFrameActivityActivity.class);
        Intent intent = new Intent(getApplicationContext(), TestFrameActivityActivity.class);
        if(view.getId() == R.id.normal_anim){
            intent.putExtra("mode", 1);
        }else {
            intent.putExtra("mode", 2);
        }
        startActivity(intent);
    }

}
