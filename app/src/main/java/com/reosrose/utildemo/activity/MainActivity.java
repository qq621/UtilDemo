package com.reosrose.utildemo.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reosrose.utildemo.R;
import com.reosrose.utildemo.utils.GoActivityUtil;
import com.reosrose.utildemo.utils.ScreenSwitchUtil;

import org.litepal.LitePal;

public class MainActivity extends BaseActivity{
    String str = "http://b261.photo.store.qq.com/psb?/V11mmPKr4bsGFW/CTLIa0RhfaTvhG.ydyvAN8oC8jX8veg6WyI6D*APyNQ!/b/dAUBAAAAAAAA&bo=WAIgA1gCIAMFCSo!&rf=viewer_4";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //监听当前activity 重力方向 跟随重力横竖屏
        ScreenSwitchUtil.getInstance().start(MainActivity.this);
        imageView = (ImageView) findViewById(R.id.imageViewId);

        initEvent();
    }

    private void initEvent() {
        imageView.setOnClickListener(this);
        findViewById(R.id.open_camera).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消当前activity重力方向
        ScreenSwitchUtil.getInstance().stop();
    }

    @Override
    public void onClick(View v) {
        int id =  v.getId();
        switch (id){
            case R.id.imageViewId:
//                startAnimation1(v);
                startAnimation2(v);
                break;
            case R.id.open_camera:
                GoActivityUtil.goToCameraActivity(MainActivity.this);
                break;
            default:
                break;

        }
    }

    private void startAnimation1(View v){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "translationX", 0f, 100f);
        objectAnimator.start();
    }
    private void startAnimation2(final View v){
        ObjectAnimator objectAnimator =ObjectAnimator.ofFloat(v,"haha",0f,200f);
        objectAnimator.setDuration(1000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                v.setAlpha((100+value)/400);//0.5- 1
                v.setScaleX((100+value)/400);
                v.setScaleY((100+value)/400);
            }
        });
        objectAnimator.start();
    }
    private void startAnimation3(final View v){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,200f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                v.setAlpha((100+value)/400);//0.5- 1
                v.setScaleX((100+value)/400);
                v.setScaleY((100+value)/400);
            }
        });
    }
    private void startAnimation4(View v){
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("alpha",1f,0.5f,1f);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleX",1f,0.5f,1f);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("scaleY",1f,0.5f,1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether();
    }
}
