package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */

public class JuPeripheryAdapter extends BaseAdapter{

    List<JuUniversalData> list;
    LayoutInflater inflater;
    Context context;

    public JuPeripheryAdapter(Context context, List<JuUniversalData> listdata){
             this.list = listdata;
             this.context = context;
        inflater = LayoutInflater.from(context);
    }

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          ViewHolder holder;
        if(convertView ==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_ju_periphery,null);
            holder.name = (TextView) convertView.findViewById(R.id.ju_storname);
            holder.score = (TextView) convertView.findViewById(R.id.ju_userscore);
            holder.distance = (TextView) convertView.findViewById(R.id.ju_storedistance);
            holder.storephoto = (ImageView) convertView.findViewById(R.id.ju_storephoto);
            holder.storecontent = (TextView) convertView.findViewById(R.id.ju_storecontent);
            holder.salebefore = (TextView) convertView.findViewById(R.id.ju_salebefore);
            holder.salelater = (TextView) convertView.findViewById(R.id.ju_salebelater);
            holder.salenum = (TextView) convertView.findViewById(R.id.ju_salenum);
           convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        JuUniversalData data = list.get(position);
        holder.name.setText(data.getName());
        holder.score.setText(data.getScore());
        holder.distance.setText(data.getDistance());
        Picasso.with(context).load(data.getPhoto()).into( holder.storephoto);
        holder.storecontent.setText(data.getContent());
        holder.salebefore.setText(data.getSalebefore());
        holder.salelater.setText(data.getSalelater());
        holder.salenum.setText(data.getSalenum());

        return convertView;
    }
}

class ViewHolder{
//    String name ; //商户的名字
//    String score;    //用户的评分
//    String location; //商户的地点
//    String distance; //商户的距离
//    String  storephoto;   //商户图片
//    String  storecontent; //团购内容
//    String  salebefore;   //打折前的价格
//    String  salelater;    //折后价
//    String  salenum;      //销量

    TextView name;
    TextView score;
    TextView distance;
    ImageView storephoto;
    TextView storecontent;
    TextView salebefore;
    TextView salelater;
    TextView salenum;

}