package com.reosrose.utildemo.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.reosrose.utildemo.R;
import com.reosrose.utildemo.view.Preview;

public class CameraActiity extends BaseActivity {

    private Preview preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.cameraLayout);
        setContentView(R.layout.activity_camera_actiity);
        initView();

        initEvent();
    }

    private void initView() {
        preview = (Preview) findViewById(R.id.phone_preview);
    }

    private void initEvent() {
        findViewById(R.id.phone_takepic).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.phone_takepic:
                if(preview != null){
                    preview.takePhoto();
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        preview.clickFoucs();
        return super.onTouchEvent(event);
    }
}
