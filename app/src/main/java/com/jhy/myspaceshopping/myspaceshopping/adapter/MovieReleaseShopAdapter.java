package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;

import java.util.List;

/**
 * Created by Administrator on 2016/4/22.
 */
public class MovieReleaseShopAdapter extends BaseAdapter {
    Context context;
    List<NearbyShops.Data.Deals> list;
    LayoutInflater inflater;

    public MovieReleaseShopAdapter(Context context, List<NearbyShops.Data.Deals> list) {
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
        MovieReleaseShopAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_movie_item, null);
            viewHolder.movieListTitle = (TextView) convertView.findViewById(R.id.movie_list_title);
            viewHolder.movieListScore = (TextView) convertView.findViewById(R.id.movie_list_score);
            viewHolder.movieListPrice = (TextView) convertView.findViewById(R.id.movie_list_price);
            viewHolder.movieDistance = (TextView) convertView.findViewById(R.id.movie_list_distance);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.movieListTitle.setText(list.get(position).getTitle());
        viewHolder.movieListScore.setText(list.get(position).getScore()+"分");
        viewHolder.movieListPrice.setText("￥ "+list.get(position).getCurrent_price());
        viewHolder.movieDistance.setText(list.get(position).getDistance()+"km");
        return convertView;
    }

    class ViewHolder {
        TextView movieListTitle;
        TextView movieListScore;
        TextView movieListPrice;
        TextView movieDistance;
    }
}
