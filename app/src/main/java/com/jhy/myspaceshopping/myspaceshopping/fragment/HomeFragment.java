package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.activity.HomeSearchActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.ListTestActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.WebViewActivity;
import com.jhy.myspaceshopping.myspaceshopping.adapter.CityAreaAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.NearbyShopsAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.CityArea;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.jhy.myspaceshopping.myspaceshopping.util.OkHttpUtils;
import com.jhy.myspaceshopping.myspaceshopping.util.OkHttpUtils.OKCallBack;

public class HomeFragment extends Fragment {

    String cityResult;
    String shopResult;
    TextView tv;
    TextView imageCity;
    View view;
    int width;
    WindowManager.LayoutParams lp;//窗口管理对象
    GridView gridView;
    TextView searchCity;
    List<String> list;//城市地区集合
    List<NearbyShops.Data.Deals> dealsList;//附近商铺集合
    CityArea cityArea;//城市地区模板类
    NearbyShops nearbyShops;//附近店铺模板类
    Context context;
    PullToRefreshListView shopListView;
    LayoutInflater inflater;
    TextView editTextSearch;
    View header;
    NearbyShopsAdapter nearbyShopsAdapter;
    int shopDataPage = 1;
    Boolean first = true;
    String cityName;
    PopupWindow popupWindow1;
    View homeFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeFragmentView = inflater.inflate(R.layout.activity_home_main,container,false);
        view = homeFragmentView.findViewById(R.id.btn_city_choose);

        this.inflater = inflater;
        init();
        getCityDataRun();//开启异步线程获取城市数据
        getShopDataRun(shopDataPage);//开启了一个异步线程获取网络数据
        return homeFragmentView;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        view = findViewById(R.id.btn_city_choose);
        context = getActivity();
        inflater = LayoutInflater.from(context);
        init();
        getCityDataRun();//开启异步线程获取城市数据
        getShopDataRun(shopDataPage);//开启了一个异步线程获取网络数据
    }*/

   /* @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        cityName = intent.getStringExtra("cityName");
    }*/

    @Override
    public void onResume() {
        super.onResume();
        context = getActivity();
        cityName = getActivity().getIntent().getStringExtra("city");
        Log.i("gl", "!!!!!!!!!!!!!" + "我进Resum了" + cityName);
        if (cityName != null) {
            imageCity.setText(cityName);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 初始化控件
     */
    private void init() {


        imageCity = (TextView) homeFragmentView.findViewById(R.id.btn_city_choose);
        imageCity.setOnClickListener(l);
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        shopListView = (PullToRefreshListView) homeFragmentView.findViewById(R.id.home_listView);
        shopListView.setMode(PullToRefreshBase.Mode.BOTH);
        header = inflater.inflate(R.layout.listview_home_header, null);
        shopListView.getRefreshableView().addHeaderView(header);
        //shopListView.addHeaderView(header);
        editTextSearch = (TextView) homeFragmentView.findViewById(R.id.edt_search);
        editTextSearch.setOnClickListener(search);
        shopListView.setOnRefreshListener(shoListListener);
        dealsList = new ArrayList<>();//产生一个装商店数据的集合
        shopListView.setOnItemClickListener(shopListViewItemListener);


    }

    /**
     * 为商铺listView设置点击事件
     */
    ListView.OnItemClickListener shopListViewItemListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String shopUrl = dealsList.get(position-2).getDeal_murl();
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("shopUrl",shopUrl);
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
            shopListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopuwindow();//显示点击城市后弹出选择城市的popuwindow

        }
    };
    View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, HomeSearchActivity.class);
            startActivity(intent);//跳转到搜索页面
        }
    };


    /**
     * 显示该城市区的popuwindow
     */
    private void showPopuwindow() {
        lp = getActivity().getWindow().getAttributes();
        View pupView = LayoutInflater.from(context).inflate(R.layout.activity_home_searchcity, null);
        popupWindow1 = new PopupWindow(pupView);
        popupWindow1.setWidth(width - 40);
        popupWindow1.setHeight(500);
        popupWindow1.setFocusable(true);
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.background));
        searchCity = (TextView) pupView.findViewById(R.id.search_city);
        if (cityName != null) {
            searchCity.setText(cityName);
        }
        gridView = (GridView) pupView.findViewById(R.id.gridCity);
        Context context = pupView.getContext();
        setCityListData();//设置List数据
        CityAreaAdapter cityAreaAdapter = new CityAreaAdapter(context, list);
        gridView.setAdapter(cityAreaAdapter);
        popupWindow1.showAsDropDown(view, 20, 15);

        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        //popupwindow 消失时的监听
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        searchCity.setOnClickListener(searchCityListener);

    }

    View.OnClickListener searchCityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ListTestActivity.class);
            popupWindow1.dismiss();
            startActivity(intent);
        }
    };


    private void setHomeList() {
        //Log.i("gl",""+shopResult.toString()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        analyticalShopData();

        if (nearbyShops != null) {
            for (int i = 0; i < nearbyShops.getData().getDeals().size(); i++) {
                dealsList.add(nearbyShops.getData().getDeals().get(i));
            }
        }
        if (first == true) {

            nearbyShopsAdapter = new NearbyShopsAdapter(context, dealsList);
            shopListView.setAdapter(nearbyShopsAdapter);
            first = false;
        } else {
            nearbyShopsAdapter.notifyDataSetChanged();
        }

    }


    /**
     * 设置Popuwindow中的值
     */
    private void setCityListData() {
        analyticalCityData();
        list = new ArrayList();
        if (cityArea != null) {

            for (int i = 0; i < cityArea.getDistricts().size(); i++) {
                list.add(cityArea.getDistricts().get(i).getDistrict_name());
            }
        }
    }

    /**
     * 解析json数据
     */
    private void analyticalCityData() {
        Gson gson = new Gson();
        cityArea = gson.fromJson(cityResult, CityArea.class);

    }

    private void analyticalShopData() {
        Gson gson = new Gson();
        //Log.i("gl", "++++++" + shopResult);
        nearbyShops = gson.fromJson(shopResult, NearbyShops.class);


    }


    /**
     * 从服务器API接口获取当前城市的区
     *
     * @return
     */

    private void getShopDataRun(int i) {
        //Log.i("gl","!!!!!!!"+"我执行了run");

        Map<String, String> shopMap = new HashMap<>();

        shopMap.put("city_id", "400010000");
        shopMap.put("page", i + "");
        shopMap.put("cat_ids", "320");
        shopMap.put("radius", "1000");
        shopMap.put("page_size", "10");
        shopMap.put("deals_per_shop", "10");


        OkHttpUtils.getInstance().doGet("http://apis.baidu.com/baidunuomi/openapi/searchdeals", shopMap,"eca37bd5318ddde48b144c6a37bd82e5").excute(new OKCallBack() {
            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onResponse(String message) {
                //Log.i("gl", "!!!!!!!" + message);
                shopResult = message;
                setHomeList();
            }
        });


    }

    private void getCityDataRun() {

        Map<String, String> cityMap = new HashMap<>();
        cityMap.put("city_id", "400010000");
        OkHttpUtils.getInstance().doGet("http://apis.baidu.com/baidunuomi/openapi/districts", cityMap,"eca37bd5318ddde48b144c6a37bd82e5").excute(new OKCallBack() {
            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onResponse(String message) {
                cityResult = message;
            }
        });
    }
    public interface setCityInterface{
        void setCity();
    }

}
