package com.reosrose.utildemo.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reosrose.utildemo.R;
import com.reosrose.utildemo.utils.ScreenSwitchUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //监听当前activity 重力方向 跟随重力横竖屏
        ScreenSwitchUtil.getInstance().start(MainActivity.this);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewId);
        Glide.with(this)
                .load("").into(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消当前activity重力方向
        ScreenSwitchUtil.getInstance().stop();
    }
}
