package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.SearchShop;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class SearchShopAdapter extends BaseAdapter{
    List<SearchShop.Data.Shops> list;
    Context context;
    LayoutInflater inflater;

    public SearchShopAdapter(Context context, List<SearchShop.Data.Shops> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_home_item, null);
            viewHolder.list_image = (ImageView) convertView.findViewById(R.id.list_image);
            viewHolder.list_title = (TextView) convertView.findViewById(R.id.list_title);
            viewHolder.list_description = (TextView) convertView.findViewById(R.id.list_description);
            viewHolder.list_current_price = (TextView) convertView.findViewById(R.id.list_current_price);
            viewHolder.list_market_price = (TextView) convertView.findViewById(R.id.list_market_price);
            viewHolder.list_score = (TextView) convertView.findViewById(R.id.list_score);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        SearchShop.Data.Shops shops = list.get(position);
        viewHolder.list_title.setText(shops.getShop_name());
        viewHolder.list_description.setText(shops.getDeals().get(0).getDescription());
        viewHolder.list_current_price.setText("￥ "+shops.getDeals().get(0).getCurrent_price());
        viewHolder.list_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.list_market_price.setText(shops.getDeals().get(0).getMarket_price()+"");
        viewHolder.list_score.setText(shops.getDeals().get(0).getScore()+"");
        Picasso.with(context).load(shops.getDeals().get(0).getTiny_image()).into(viewHolder.list_image);
        //Log.i("gl","!!!!!"+"我进NearbyShopAdapter了"+"!!!!!!!!!!!!!");
        return convertView;
    }

    class ViewHolder {
        ImageView list_image;
        TextView list_title;
        TextView list_description;
        TextView list_current_price;
        TextView list_market_price;
        TextView list_score;

    }
}
