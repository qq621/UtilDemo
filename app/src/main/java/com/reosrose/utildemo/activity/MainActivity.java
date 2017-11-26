package com.reosrose.utildemo.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reosrose.utildemo.R;
import com.reosrose.utildemo.utils.ScreenSwitchUtil;

import org.litepal.LitePal;

public class MainActivity extends BaseActivity {
    String str = "http://b261.photo.store.qq.com/psb?/V11mmPKr4bsGFW/CTLIa0RhfaTvhG.ydyvAN8oC8jX8veg6WyI6D*APyNQ!/b/dAUBAAAAAAAA&bo=WAIgA1gCIAMFCSo!&rf=viewer_4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //监听当前activity 重力方向 跟随重力横竖屏
        ScreenSwitchUtil.getInstance().start(MainActivity.this);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewId);

//        Glide.with(this)
//                .load(str).into(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消当前activity重力方向
        ScreenSwitchUtil.getInstance().stop();
    }

}
