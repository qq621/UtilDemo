package com.reosrose.utildemo.view;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.reosrose.utildemo.utils.ToastUtil;

/**
 * Created by yinsxi on 2017/11/12.
 */

public class Preview extends SurfaceView implements SurfaceHolder.Callback {

    // 处理surface对象
    private final SurfaceHolder mHolder;
    private final Context mContext;
    private Camera mCamera;
    private Parameters mParameters;

    public Preview(Context context) {
        super(context);
        this.mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();

            showError();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mParameters = mCamera.getParameters();


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    private void showError(){
        ToastUtil.show(mContext.getApplicationContext(),"相机操作失败");
    }
}
