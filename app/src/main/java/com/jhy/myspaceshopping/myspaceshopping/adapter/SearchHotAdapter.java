package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/19.
 */
public class SearchHotAdapter extends BaseAdapter {
    Context context;
    List<String> list;
    public SearchHotAdapter(Context context,List<String> list){
        super();
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
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
        TextView textHotSearch;
        if(convertView==null){
            textHotSearch=new TextView(context);
        }
        textHotSearch=(TextView)convertView;
        textHotSearch.setText(list.get(position));
        return textHotSearch;
    }
}
