package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.NearbyShopsAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.SearchShopAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.SearchShop;
import com.jhy.myspaceshopping.myspaceshopping.util.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/3.
 */
public class HomeSearchDegitalActivity extends Activity {
    PullToRefreshListView search_shop_digital;
    String shopName;
    String shopResult;
    SearchShop searchShop;
    List<SearchShop.Data.Shops> dealsList;//附近商铺集合
    Context context;
    int shopDataPage = 1;
    Boolean isFirst = true;
    SearchShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search_digital);
        dealsList = new ArrayList<>();
        context = this;
        shopName = getIntent().getStringExtra("shopName");
        getshopData(1);
        init();
    }


    private void init() {
        search_shop_digital = (PullToRefreshListView) findViewById(R.id.search_shop_digital);
        search_shop_digital.setMode(PullToRefreshBase.Mode.BOTH);
        search_shop_digital.setOnRefreshListener(refreshListener);
        search_shop_digital.setOnItemClickListener(itemListener);
    }

    PullToRefreshBase.OnRefreshListener2 refreshListener = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            new GetDataTask().execute();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            new GetDataTask().execute();
        }
    };

    AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String shopUrl = dealsList.get(position).getShop_murl();
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("shopUrl", shopUrl);
            startActivity(intent);
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
            getshopData(shopDataPage);
            //Log.i("gl", "加了" + shopDataPage + shopResult);
            search_shop_digital.onRefreshComplete();
            super.onPostExecute(result);
        }
    }


    private void getshopData(int i) {
        Map<String, String> shopMap = new HashMap<>();

        shopMap.put("city_id", "400010000");
        shopMap.put("page", i + "");
        shopMap.put("keyword", shopName);
        shopMap.put("radius", "3000");
        shopMap.put("page_size", "10");
        shopMap.put("deals_per_shop", "10");


        OkHttpUtils.getInstance().doGet("http://apis.baidu.com/baidunuomi/openapi/searchshops", shopMap, "eca37bd5318ddde48b144c6a37bd82e5").excute(new OkHttpUtils.OKCallBack() {
            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onResponse(String message) {
                Log.i("gl", "!!!!!!!" + message);
                shopResult = message;
                setList();
            }


        });
    }

    private void setList() {
        Gson gson = new Gson();
        searchShop = gson.fromJson(shopResult, SearchShop.class);
        Log.i("gl", "!!!!!!!" + searchShop.getErrno());
        if (searchShop.getErrno() == 1005) {
            Toast.makeText(context, "您所搜索的店铺不存在，请重新输入", Toast.LENGTH_SHORT).show();
            finish();
        } else if (searchShop.getData().getShops().size() != 0 && searchShop != null) {

            for (int i = 1; i < searchShop.getData().getShops().size(); i++) {
                dealsList.add(searchShop.getData().getShops().get(i));
            }
        } else {
            Toast.makeText(context, "您所搜索的店铺不存在，请重新输入", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (isFirst) {
            adapter = new SearchShopAdapter(context, dealsList);
            search_shop_digital.setAdapter(adapter);
            isFirst = false;
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}
