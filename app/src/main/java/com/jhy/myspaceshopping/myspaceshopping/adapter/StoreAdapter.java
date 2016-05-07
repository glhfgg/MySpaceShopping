package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.StoreBaseModel;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/14.
 */

//商家-- 适配器StoreAdapter ：连接ListView对象和数据的
public class StoreAdapter extends BaseAdapter {

    Context context;
    List<NearbyShops.Data.Deals> list;
    LayoutInflater inflater;//动态载入布局文件

    //通过构造方法传递ListView需要的显示的数据集合
    public StoreAdapter(Context context, List<NearbyShops.Data.Deals> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    //当前adapteView包含的item的数量
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    //返回指定位置所对应的对象
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    //返回当前对象的item所对应的ID
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //将布局文件转化成View对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_storegeneralmessagelayout, null);
            holder = new Holder();
            // holder.imgThemePicture= (ImageView) convertView.findViewById(R.id.img_themepicture);
            holder.textStoreName = (TextView) convertView.findViewById(R.id.text_storename);
            holder.barStar = (RatingBar) convertView.findViewById(R.id.bar_star);
            holder.textComment = (TextView) convertView.findViewById(R.id.text_comment);
            holder.textAverage = (TextView) convertView.findViewById(R.id.text_average);
            holder.textDistance = (TextView) convertView.findViewById(R.id.text_distance);
            holder.imgThemePicture = (ImageView) convertView.findViewById(R.id.img_themepicture);
            convertView.setTag(holder);
        }
        holder = (Holder) convertView.getTag();
        NearbyShops.Data.Deals item = list.get(position);
        // Picasso.with(context).load(item.getImgThemePicture()).into(holder.imgThemePicture);
        holder.textStoreName.setText(item.getTitle());
        holder.barStar.setRating((float) item.getScore());
        holder.textComment.setText(item.getComment_num() + "");
        holder.textAverage.setText("￥" + item.getCurrent_price());
        holder.textDistance.setText(item.getDistance() + "");
        Picasso.with(context).load(item.getTiny_image()).into(holder.imgThemePicture);
        return convertView;
    }

    //优化 --以后可动态的改变控件数量
    class Holder {
        ImageView imgThemePicture;
        TextView textStoreName;
        RatingBar barStar;
        TextView textComment;
        TextView textAverage;
        TextView textDistance;


    }
}
