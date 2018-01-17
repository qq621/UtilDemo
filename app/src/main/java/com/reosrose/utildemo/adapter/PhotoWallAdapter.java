package com.reosrose.utildemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.reosrose.utildemo.R;
import com.reosrose.utildemo.utils.ImageUrls;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * 照片墙适配器
 * Created by yinsxi on 2018/1/17.
 */

public class PhotoWallAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    /**
     * 记录所有正在下载或等待下载的任务。
     */
    private Set<BitmapWorkerTask> taskCollection;
    private final Context mContext;
    private final String[] mImageUrls;
    private final GridView mPhotoWall;
    private final LruCache<String, Bitmap> mMemoryCache;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private boolean isFirstEnter;

    public PhotoWallAdapter(Context context, String[]urls, GridView gridView){
        this.mContext = context;
        this.mImageUrls = urls;
        this.mPhotoWall = gridView;
        taskCollection = new HashSet<>();
        //拿到可用的内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize  = maxMemory / 8;
        //设置图片缓存为应用的8分之一
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        mPhotoWall.setOnScrollListener(this);
    }
    @Override
    public int getCount() {
        return mImageUrls.length;
    }

    @Override
    public String getItem(int position) {
        return mImageUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.photo_layout,null);
        }else {
            view = convertView;
        }
        ImageView photo = (ImageView) view.findViewById(R.id.photo);
        final String url = getItem(position);
        // 设置Tag，方便上下滑动的时候查找到对应的ImageView
        photo.setTag(url);
        //设置显示图片，这时候只会显示默认图片，还没有下载
        setImageView(url, photo);
        return view;
    }

    private void setImageView(String url, ImageView photo) {
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if (bitmap != null) {
            photo.setImageBitmap(bitmap);
        } else {
            photo.setImageResource(R.mipmap.ic_launcher);
        }
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @param bitmap
     *            LruCache的键，这里传入从网络上下载的Bitmap对象。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }
    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
        if (scrollState == SCROLL_STATE_IDLE) {
            loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
        } else {
            cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        // 下载的任务应该由onScrollStateChanged里调用，但首次进入程序时onScrollStateChanged并不会调用，
        // 因此在这里为首次进入程序开启下载任务。
        if (isFirstEnter && visibleItemCount > 0) {
            loadBitmaps(firstVisibleItem, visibleItemCount);
            isFirstEnter = false;
        }
    }
    private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
        try {
            for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
                String imageUrl = ImageUrls.imageUrls[i];
                Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
                //如果缓存中不存在图片，开始一个异步任务去下载图片，并添加到Set集合，随时停止和开始当前任务
                if (bitmap == null) {
                    BitmapWorkerTask task = new BitmapWorkerTask();
                    taskCollection.add(task);
                    task.execute(imageUrl);
                } else {
                    //上下滑动的时候，查找到对应的ImageView line 85
                    ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWorkerTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }

    private class BitmapWorkerTask extends AsyncTask<String,Void,Bitmap>{
        /**
         * 图片的URL地址
         */
        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadBitmap(params[0]);
            if (bitmap != null) {
                // 图片下载完成后缓存到LrcCache中
                addBitmapToMemoryCache(params[0], bitmap);
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
                // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
                ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }
        /**
         * 建立HTTP请求，并获取Bitmap对象。
         *
         * @param imageUrl
         *            图片的URL地址
         * @return 解析后的Bitmap对象
         */
        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds  = false;
                options.inSampleSize = 5;
                bitmap = BitmapFactory.decodeStream(con.getInputStream(),null,options);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }

    }
}
