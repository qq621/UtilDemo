package com.reosrose.utildemo.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.reosrose.utildemo.base.UtilApplication;
import com.reosrose.utildemo.utils.LogUtils;
import com.reosrose.utildemo.utils.PathUtil;
import com.reosrose.utildemo.utils.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 预览图片，相机
 * Created by yinsxi on 2017/11/12.
 */

public class Preview extends SurfaceView implements SurfaceHolder.Callback {

    // 处理surface对象
    private SurfaceHolder mSurfaceHolder;
    private Context mContext;
    private Camera mCamera;
    private Parameters mParameters;

    public Preview(Context context) {
        super(context);
        init(context);
    }


    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Preview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Preview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //默认打开后置相机  这里忽略高版本动态判断相机权限
            mCamera = Camera.open();
            setCamera();
        } catch (Exception e) {
            e.printStackTrace();

            showError();
        }
        startPreviewDisplay(holder);
    }

    /**
     * 设置相机参数
     */
    private void setCamera() {
        mParameters = mCamera.getParameters();
        if (isAutoFocusSupported()) {
            mParameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);
        }
        clickFoucs();
        //设置预览图片大小
        List<Camera.Size> supportedPreview = mParameters.getSupportedPreviewSizes();
        setPreviewSize(supportedPreview);
        //设置保存图片大小
        setPictureSize();
        mCamera.setParameters(mParameters);
    }

    /**
     * 设置保存图片大小  控制在  1080*1920
     */
    private void setPictureSize() {
        int height = 0;
        int width = 0;
        List<Camera.Size> supportedPictureSizes = mParameters.getSupportedPictureSizes();
        for (int i = 0; i < supportedPictureSizes.size(); i++) {
            Camera.Size size = supportedPictureSizes.get(i);
            height = size.height;
            width = size.width;
            if (height > 1000 && height < 2000 && width > 1000 && width < 2000) {
                LogUtils.d(" save width = " + width + " height = " + height);
                mParameters.setPictureSize(width, height);
                return;
            }
        }
    }

    /**
     * 设置预览图片大小
     * @param supportedPreview 支持的预览Size 集合
     */
    private void setPreviewSize(List<Camera.Size> supportedPreview) {
        int width = 0;
        int height = 0;
        for (int i = 0; i < supportedPreview.size(); i++) {
            Camera.Size size = supportedPreview.get(i);
            if (height < size.height) {
                height = size.height;
                width = size.width;

            }

        }
        LogUtils.d(" pre width = " + width + " height = " + height);
        mParameters.setPreviewSize(width, height);
    }

    /**
     * 自动和手动调用 实现自动和手动聚焦
     */
    public void clickFoucs() {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                mCamera.cancelAutoFocus();
            }
        });
    }
    /**顾名思义  当尺寸发生变化的时候调用，第一次进入会执行一次，除非发生横竖屏切换才会再次执行*/
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
        followScreenOrientation();
        stopPreviewDisplay();
        startPreviewDisplay(mSurfaceHolder);


    }

    /**
     * 判断是否支持自动聚焦
     * @return
     */
    private boolean isAutoFocusSupported() {
        List<String> modes = mParameters.getSupportedFocusModes();
        return modes.contains(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    /**
     * 判断屏幕方向
     */
    public void followScreenOrientation() {
        final int orientation = mContext.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mCamera.setDisplayOrientation(180);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            mCamera.setDisplayOrientation(90);
        }
    }

    /**
     * 开始预览
     * @param holder 操控Surface视图者
     */
    private void startPreviewDisplay(SurfaceHolder holder) {
        checkCamera();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            LogUtils.e("Error while start preview for camera" + e.toString());
        }
    }

    /**
     * 停止预览
     */
    private void stopPreviewDisplay() {
        checkCamera();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            LogUtils.e("Error while STOP preview for camera" + e.toString());
        }
    }
    /**检查相机状态*/
    private void checkCamera() {
        if (mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreviewDisplay();
        mCamera.release();

    }

    private void showError() {
        ToastUtil.show(UtilApplication.getContext(), "相机操作失败");
    }

    /**
     * 拍照调用
     */
    public void takePhoto() {
        if (mCamera != null) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                        mCamera.takePicture(null, null, new MyPictureCallBack());
                        mCamera.cancelAutoFocus();

                }
            });
        }
    }
    /**异步去保存图片到本地*/
    private class MyPictureCallBack implements Camera.PictureCallback {

        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {


                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String name = sdf.format(date);
                        String filePth = PathUtil.getAppDocPath() + name + ".jpg";
                        LogUtils.d(filePth);
                        File file = new File(filePth);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        bitmap = rotateBitmapByDegree(bitmap,90);
                        BufferedOutputStream bos = new BufferedOutputStream(
                                new FileOutputStream(file));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);

                        bos.flush();
                        bos.close();
                        bitmap.recycle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            /**每次拍完照后，会停止预览，所以要重新开始预览，，*/
            mCamera.startPreview();

        }
    }

    /**
     * 将图片旋转多少度
     * @param bm 图片
     * @param degree 旋转角度
     * @return
     */
    private  Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
}
