package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.SecondClassItemModel;


import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/22.
 */
public class SecondClassAdapter extends BaseAdapter {
    Context context;
    List<SecondClassItemModel> list;
    LayoutInflater inflater;
    int selectPosition=0;
    public SecondClassAdapter(Context context, List<SecondClassItemModel> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
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
        Holder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_list_popupright,null);
            holder=new Holder();
            holder.rightItemName=(TextView)convertView.findViewById(R.id.right_item_name);
            convertView.setTag(holder);
        }
        holder=(Holder)convertView.getTag();
        holder.rightItemName.setText(list.get(position).getName());
        return convertView;
    }
    class Holder{
        TextView rightItemName;
    }
}
