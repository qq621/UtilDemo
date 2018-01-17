package com.reosrose.utildemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reosrose.utildemo.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by yinsxi on 2018/1/17.
 */

public class GlideUtils {
    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }
    public static void load(ImageView imageView, Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(mContext).load(bytes).error(R.mipmap.ic_launcher).into(imageView);
    }
}
