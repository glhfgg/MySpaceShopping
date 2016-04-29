package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.CityAreaAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.jhy.myspaceshopping.myspaceshopping.util.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
public class HomeSearchActivity extends Activity {
    ImageView btnSearchBack;
    AutoCompleteTextView editSearch;
    TextView btnSearchFind;
    GridView gridSearch;
    String shopResult;
    List<String> list;
    List<String> shopUrlList;
    Context context;
    NearbyShops shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        init();
        getSerchData();
    }

    private void init() {
        btnSearchBack = (ImageView) findViewById(R.id.btn_search_back);
        editSearch = (AutoCompleteTextView) findViewById(R.id.edit_search);
        btnSearchFind = (TextView) findViewById(R.id.btn_search_find);
        gridSearch = (GridView) findViewById(R.id.grid_Search);
        gridSearch.setOnItemClickListener(gridListener);
        btnSearchBack.setOnClickListener(btnSearchBackListener);
    }
    AdapterView.OnItemClickListener gridListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String shopUrl = shopUrlList.get(position);
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("shopUrl",shopUrl);
            startActivity(intent);
        }
    };
    View.OnClickListener btnSearchBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * 得到附近热门店铺的信息
     */
    private void getSerchData() {
        Map<String, String> shopMap = new HashMap<>();
        shopMap.put("city_id", "400010000");
        shopMap.put("cat_ids", "323,326,320");
        shopMap.put("sort", "4");
        shopMap.put("radius", "3000");
        shopMap.put("page_size", "50");
        shopMap.put("deals_per_shop", "50");

        OkHttpUtils.getInstance().doGet("http://apis.baidu.com/baidunuomi/openapi/searchdeals", shopMap,"eca37bd5318ddde48b144c6a37bd82e5").excute(new OkHttpUtils.OKCallBack() {
            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onResponse(String message) {
                shopResult = message;
                //Log.i("gl", "热门shop" + shopResult + "!!!!!!!!!!@!@$$");
                Gson gson = new Gson();
                shop = gson.fromJson(shopResult, NearbyShops.class);
                setListData();
                CityAreaAdapter adapter = new CityAreaAdapter(context, list);
                gridSearch.setAdapter(adapter);
            }
        });



    }

    private void setListData() {
        list = new ArrayList<>();
        shopUrlList = new ArrayList<>();
        for (int i=0;i<50;i++){
            if(shop.getData().getDeals().get(i).getTitle().length()<6){
                list.add(shop.getData().getDeals().get(i).getTitle());
                shopUrlList.add(shop.getData().getDeals().get(i).getDeal_murl());
            }
            if(list.size()>=9){
                break;
            }
        }
    }


}
