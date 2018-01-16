package com.reosrose.utildemo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reosrose.utildemo.R;

/**
 * 项目通用adapter
 * Created by yinsxi on 2017/12/5.
 */

public class CommonAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder{
        TextView title;
        
        public ViewHolder(View view){
            title  = (TextView) view.findViewById(R.id.title);
        }

    }
}
