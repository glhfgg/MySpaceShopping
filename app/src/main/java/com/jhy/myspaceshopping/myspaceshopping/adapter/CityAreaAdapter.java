package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class CityAreaAdapter extends BaseAdapter {
    Context context;//传入的上下文
    List<String> list;
    LayoutInflater layoutInflater;

    public CityAreaAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView v;
        if (convertView == null) {
            convertView = new TextView(context);
        }
        v = (TextView) convertView;
        v.setText(list.get(position));
        v.setHeight(100);
        v.setBackgroundColor(255250205);
        v.setGravity(Gravity.CENTER);
        return v;
    }
}
