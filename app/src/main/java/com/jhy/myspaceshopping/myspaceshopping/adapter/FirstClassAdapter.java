package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.FirstClassItemModel;


import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/22.
 */
public class FirstClassAdapter extends BaseAdapter {
    Context context;
    List<FirstClassItemModel> list;
    LayoutInflater inflater;
    int selectPosition=0;

    public FirstClassAdapter(Context context, List<FirstClassItemModel> list) {
        this.context = context;
        this.list = list;
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

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_list_popupleft,null);
            holder=new Holder();
            holder.leftItemName=(TextView)convertView.findViewById(R.id.left_item_name);
            convertView.setTag(holder);
        }
        holder=(Holder)convertView.getTag();
        holder.leftItemName.setText(list.get(position).getName());
        //选中和没选中时，设置不同的颜色
        if(position==selectPosition){
            convertView.setBackgroundResource(R.color.bg_popup_right);
        }else{
            convertView.setBackgroundResource(R.drawable.popup_left_selector);
        }
        //判断一级分类是否有二级分类，如果有这加上下级图片
        if (list.get(position).getSecondList() != null && list.get(position).getSecondList().size() > 0) {
            holder.leftItemName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.arrow_right, 0);
        } else {
            holder.leftItemName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
         return convertView;
    }
    class Holder{
        TextView leftItemName;
    }
}
