package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class MovieReleaseAdapter extends BaseAdapter {
    Context context;
    List<Movie> list;
    LayoutInflater inflater;

    public MovieReleaseAdapter(Context context, List list) {
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
        ViewHolder holder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.hlistview_movie_release_item,null);
            holder = new ViewHolder();
            holder.hlist_image = (ImageView) convertView.findViewById(R.id.hlist_image);
            holder.hlist_title = (TextView) convertView.findViewById(R.id.hlist_title);
            holder.hlist_score = (TextView) convertView.findViewById(R.id.hlist_score);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Picasso.with(context).load(list.get(position).getImage()).into(holder.hlist_image);
        holder.hlist_title.setText(list.get(position).getTitle());
        holder.hlist_score.setText(list.get(position).getScore()+"分");
        return convertView;
    }
    public class ViewHolder{
        ImageView hlist_image;
        TextView hlist_title;
        TextView hlist_score;

    }
}
