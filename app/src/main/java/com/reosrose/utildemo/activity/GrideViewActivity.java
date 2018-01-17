package com.reosrose.utildemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.reosrose.utildemo.R;
import com.reosrose.utildemo.adapter.PhotoWallAdapter;
import com.reosrose.utildemo.utils.ImageUrls;

public class GrideViewActivity extends AppCompatActivity {

    private GridView mPhotoWall;
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gride_view);
        mPhotoWall = (GridView)findViewById(R.id.phone_gridview);
        adapter = new PhotoWallAdapter(this, ImageUrls.imageUrls,mPhotoWall);
        mPhotoWall.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cancelAllTasks();
    }
}
