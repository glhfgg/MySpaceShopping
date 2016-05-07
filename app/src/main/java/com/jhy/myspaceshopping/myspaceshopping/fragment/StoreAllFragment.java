package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.activity.WebViewActivity;
import com.jhy.myspaceshopping.myspaceshopping.adapter.NearbyShopsAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.StoreAdapter;
import com.jhy.myspaceshopping.myspaceshopping.dao.BusinessConfig;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.StoreBaseModel;
import com.jhy.myspaceshopping.myspaceshopping.util.OkHttpUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TOSHIBA on 2016/4/14.
 */

//activity_business.xml 中的正文部分的布局
//全部商家--列表
public class StoreAllFragment extends Fragment {

    PullToRefreshListView listStoreMsg;//商店具体信息
    List<NearbyShops.Data.Deals> list;//商店信息List<T>
    String responseBack;//响应返回得到的数据
    BusinessConfig url=new BusinessConfig();// api --得到网络上的动态数据
    NearbyShops nearbyShops;
    int shopDataPage =1;
    boolean first = true;
    StoreAdapter storeAllAdap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //将布局文件转化成view对象
        View view=inflater.inflate(R.layout.fragment_storegeneralmessage,container,false);
        //当前位置==ListView的header，当前手机智能定位的地点
        View viewLocation=inflater.inflate(R.layout.location_plate_layout, null);
        //控件初始化
        listStoreMsg= (PullToRefreshListView) view.findViewById(R.id.lv_storemsg);
        //实现TextView与ListView一起滑动
        listStoreMsg.getRefreshableView().addHeaderView(viewLocation);
        listSetListener();
        list=new ArrayList<>();
        getShopDataRun(1);
        //ListView对象item的点击监听事件
        listStoreMsg.setOnItemClickListener(listener);
        Log.i("gl","!!!!!!!!!!@@@@@@@@@@@@@"+"我的StoreAllFragment Create了");
        return view;
     }

    /**
     * list每项的item点击事件
     */
    private void listSetListener() {
        listStoreMsg.setMode(PullToRefreshBase.Mode.BOTH);
        listStoreMsg.setOnRefreshListener(shoListListener);
        listStoreMsg.setOnItemClickListener(shopListViewItemListener);
    }

    ListView.OnItemClickListener shopListViewItemListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String shopUrl = list.get(position - 2).getDeal_murl();
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("shopUrl", shopUrl);
            startActivity(intent);
        }
    };

    /**
     * 上下拉刷行的监听
     */
    PullToRefreshBase.OnRefreshListener2 shoListListener = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            new GetDataTask().execute();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {

            new GetDataTask().execute();
        }
    };
    /**
     * 刷新完成的线程控制
     */
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Call onRefreshComplete when the list has been refreshed.
            ++shopDataPage;
            getShopDataRun(shopDataPage);
            //Log.i("gl", "加了" + shopDataPage + shopResult);
            listStoreMsg.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
    //ListView对象item的点击监听事件
    //点击跳转到指定的StoreDetail页面
     AdapterView.OnItemClickListener listener=new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent=new Intent(Intent.ACTION_VIEW);
             intent.setData(Uri.parse("http://www.baidu.com"));
             startActivity(intent);
         }
     };
    //适配器装配的数据
   private void storeAllData(){
       Gson gson = new Gson();
       nearbyShops = gson.fromJson(responseBack,NearbyShops.class);
       for(int i=0;i<nearbyShops.getData().getDeals().size();i++){
           list.add(nearbyShops.getData().getDeals().get(i));
       }
       if (first == true) {

           storeAllAdap=new StoreAdapter(this.getActivity(),list);
           listStoreMsg.setAdapter(storeAllAdap);
           first = false;
       } else {
           storeAllAdap.notifyDataSetChanged();
       }

    }
    //从网络上获得动态数据
    private void getShopDataRun(int i) {
        //Log.i("gl","!!!!!!!"+"我执行了run");

        Map<String, String> shopMap = new HashMap<>();

        shopMap.put("city_id", "400010000");
        shopMap.put("page", i + "");
        shopMap.put("radius", "1000");
        shopMap.put("sort", "5");
        shopMap.put("page_size", "10");
        shopMap.put("deals_per_shop", "10");


        OkHttpUtils.getInstance().doGet("http://apis.baidu.com/baidunuomi/openapi/searchdeals", shopMap, "eca37bd5318ddde48b144c6a37bd82e5").excute(new OkHttpUtils.OKCallBack() {
            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onResponse(String message) {
                //Log.i("gl", "!!!!!!!" + message);
                responseBack = message;
                storeAllData();
            }
        });


    }

}
