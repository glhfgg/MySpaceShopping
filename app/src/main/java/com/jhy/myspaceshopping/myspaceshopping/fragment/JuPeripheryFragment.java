package com.jhy.myspaceshopping.myspaceshopping.fragment;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuPeripheryAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.Peri;
import com.jhy.myspaceshopping.myspaceshopping.util.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/4/13.
 */

public class JuPeripheryFragment extends Fragment {

    PullToRefreshListView list;
    String messages;
    Peri peri ;
    List<JuUniversalData> listdata;
    int pager=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("ftest","Peri------------------");

        View v = inflater.inflate(R.layout.fragment_ju_list, container, false);
        list = (PullToRefreshListView) v.findViewById(R.id.listJu);
        list.setMode(PullToRefreshBase.Mode.BOTH);
        listdata = new ArrayList<>();
        setUtils(pager);
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new  GetDataTask().execute();
            }
        });
        return v;
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Call onRefreshComplete when the list has been refreshed.
            ++pager;
            setUtils(pager);
            //Log.i("gl", "加了" + shopDataPage + shopResult);
            list.onRefreshComplete();
            super.onPostExecute(result);
        }
    }




    public void setGson() {
        Gson gson = new Gson();
        Log.i("result","----------------messages"+messages);
        peri = gson.fromJson(messages, Peri.class);
    }

    private void setData() {

//    String name ; //商户的名字
//    String score;    //用户的评分
//    String location; //商户的地点
//    String distance; //商户的距离
//    String  storephoto;   //商户图片
//    String  storecontent; //团购内容
//    String  salebefore;   //打折前的价格
//    String  salelater;    //折后价
//    String  salenum;      //销量
        Log.i("result","-----"+(peri==null));
        Log.i("result","------messages----------setData"+messages);
        if(peri!=null){
            for (int i = 0; i < peri.getData().getShops().size(); i++) {
                String name = peri.getData().getShops().get(i).getShop_name();
                String score ="用户评分:"+peri.getData().getShops().get(i).getDeals().get(0).getScore();
                String distance = peri.getData().getShops().get(i).getDistance(); //商户的距离
                String storephoto = peri.getData().getShops().get(i).getDeals().get(0).getImage();   //商户图片
                String storecontent = peri.getData().getShops().get(i).getDeals().get(0).getDescription(); //团购内容
                double salebefores = peri.getData().getShops().get(i).getDeals().get(0).getMarket_price();   //打折前的价格
                double salelaters = peri.getData().getShops().get(i).getDeals().get(0).getCurrent_price();    //折后价
                String salenum ="销量:"+peri.getData().getShops().get(i).getDeals().get(0).getSale_num();      //销量
                String salebefore = "￥"+salebefores/100;//折后价
                String salelater = "￥"+salelaters/100;//打折前的价格

                JuUniversalData data = new JuUniversalData(name, score, null, distance, storephoto, storecontent, salelater, salebefore, salenum);
                listdata.add(data);
                data = null;
                JuPeripheryAdapter adapter = new JuPeripheryAdapter(this.getActivity(), listdata);
                list.setAdapter(adapter);

            }
        }
        list.onRefreshComplete();
    }

    private void setUtils(int pager) {

        Map<String ,String > map = new HashMap<>();
        map.put("city_id", "400010000");
        map.put("cat_ids", "320");
        map.put("radius", "3000");
        map.put("page", ""+pager);
        map.put("page_size", "10");
        map.put("deals_per_shop", "10");

        OkHttpUtils.getInstance().doGet("http://apis.baidu.com/baidunuomi/openapi/searchshops", map, "810a97d3f9fddd4b85069625400f9b0e").excute(new OkHttpUtils.OKCallBack() {
            @Override
            public void onFailure(String message) {
                Log.i("result","----------------onFailure");
            }

            @Override
            public void onResponse(String message) {
                messages = message;
                setGson();
                setData();
            }
        });

    }





//    @Override
//    public void onFailure(String message) {
//
//    }
//
//    @Override
//    public void onResponse(String message) {
//        messages = message;
//        Log.i("result","----------------messages"+messages);
//        setGson();
//    }
}

