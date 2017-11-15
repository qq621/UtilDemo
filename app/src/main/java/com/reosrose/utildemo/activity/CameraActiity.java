package com.reosrose.utildemo.activity;

import android.os.Bundle;

import com.reosrose.utildemo.R;
import com.reosrose.utildemo.view.Preview;

public class CameraActiity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preview preview = new Preview(this);
//        setContentView(R.layout.activity_camera_actiity);
        setContentView(preview);
    }
}
