package com.jhy.myspaceshopping.myspaceshopping.util;


import android.widget.BaseAdapter;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */

public abstract class JuBaseAdapter<T> extends BaseAdapter{
    List<T> list;

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
