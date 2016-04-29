package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class NearbyShopsAdapter extends BaseAdapter {
    List<NearbyShops.Data.Deals> list;
    Context context;
    LayoutInflater inflater;

    public NearbyShopsAdapter(Context context, List<NearbyShops.Data.Deals> list) {
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
        NearbyShops.Data.Deals deals = list.get(position);
        viewHolder.list_title.setText(deals.getTitle());
        viewHolder.list_description.setText(deals.getDescription());
        viewHolder.list_current_price.setText("￥ "+deals.getCurrent_price());
        viewHolder.list_market_price.setText(deals.getMarket_price()+"");
        viewHolder.list_score.setText(deals.getScore()+"");
        Picasso.with(context).load(deals.getTiny_image()).into(viewHolder.list_image);
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
  /*  public Bitmap getImage(String str){
        Bitmap bitmap = null;
        URL url;
        try {
            url = new URL(str);
            InputStream in = url.openConnection().getInputStream();
            BufferedInputStream bi = new BufferedInputStream(in);
            bitmap = BitmapFactory.decodeStream(bi);
            bi.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }*/
}
