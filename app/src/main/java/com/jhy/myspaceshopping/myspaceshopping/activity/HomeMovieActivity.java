package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.DistancePopListAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.MovieReleaseShopAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.NearbyShops;
import com.jhy.myspaceshopping.myspaceshopping.util.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
public class HomeMovieActivity extends Activity {
    PullToRefreshListView listView;
    ListView distancePopList;
    LayoutInflater inflate;
    List<NearbyShops.Data.Deals> list;
    List<String> distanceList;
    String movieResult;
    NearbyShops nearMovie;
    Context context;
    ImageView btn_movie_back;
    TextView btnMovieBrand;
    TextView btnMovieCity;
    TextView btnMovieDistance;
    TextView btnMovieEffects;
    WindowManager.LayoutParams lp;
    String[] arr = {"综合排序", "价格最低", "价格最高", "折扣最高", "销量最高", "离我最近", "好评优先"};
    int movieDataPage = 1;//电影数据的返回页面
    int sort = 8;//电影返回数据排序方式
    Boolean first = true;//是否第一次进来
    Boolean refsh = false;//判断是刷新还是加数据
    DistancePopListAdapter distanceListAdapter;
    MovieReleaseShopAdapter movieReleasesAdapter;
    PopupWindow distancePopupWindow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        context = this;
        inflate = LayoutInflater.from(this);
        init();
        doGetData(movieDataPage, sort);
        Log.i("gl","!@!@!#!!@!@电影Activity是新的"+"!!!!!!!!!!");
    }


    private void init() {
        list = new ArrayList<>();
        View viewHeader = inflate.inflate(R.layout.listview_movie_header, null);
        listView = (PullToRefreshListView) findViewById(R.id.list_movie_shop);
        listView.getRefreshableView().addHeaderView(viewHeader);
        btnMovieBrand = (TextView) findViewById(R.id.btn_movie_brand);
        btnMovieCity = (TextView) findViewById(R.id.btn_movie_city);
        btnMovieDistance = (TextView) findViewById(R.id.btn_movie_distance);
        btnMovieEffects = (TextView) findViewById(R.id.btn_movie_effects);
        btn_movie_back = (ImageView) findViewById(R.id.btn_movie_back);
        btn_movie_back.setOnClickListener(back);
        btnMovieBrand.setOnClickListener(l);
        btnMovieCity.setOnClickListener(l);
        btnMovieDistance.setOnClickListener(l);
        btnMovieEffects.setOnClickListener(l);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(pullListener);
        listView.setOnItemClickListener(movieShopListListener);

    }

    /**
     * 电影list列表的监听
     */
    ListView.OnItemClickListener movieShopListListener  = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String shopUrl = list.get(position-2).getDeal_murl();
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("shopUrl",shopUrl);
            startActivity(intent);
        }
    };

    View.OnClickListener back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    PullToRefreshBase.OnRefreshListener2 pullListener = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            new GetDataTask().execute();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            ++movieDataPage;
            doGetData(movieDataPage, sort);
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

            listView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_movie_brand:
                    Toast.makeText(context,"更多功能敬请期待！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_movie_city:
                    Toast.makeText(context,"更多功能敬请期待！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_movie_distance:
                    showDistancePop();
                    break;
                case R.id.btn_movie_effects:
                    Toast.makeText(context,"更多功能敬请期待！",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private void showDistancePop() {
        lp = getWindow().getAttributes();
        View pupView = LayoutInflater.from(context).inflate(R.layout.popuwindow_movie_distance, null);
        distancePopList = (ListView) pupView.findViewById(R.id.list_movie_distance_pop);
        distancePopupWindow1 = new PopupWindow(pupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        distancePopupWindow1.setFocusable(true);
        distancePopupWindow1.setOutsideTouchable(true);
        distancePopupWindow1.setBackgroundDrawable(getResources().getDrawable(R.mipmap.background));
        setDistancePopList();
        distancePopList.setOnItemClickListener(distanceListListener);
        distancePopupWindow1.showAsDropDown(btnMovieBrand);
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        //popupwindow 消失时的监听
        distancePopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

    }

    AdapterView.OnItemClickListener distanceListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position ==6){
                refsh = true;
                doGetData(1,8);
            }else{
                Log.i("gl","!!!!!!!!我的位置"+position);
                refsh = true;
                doGetData(1,position);
            }
            distancePopupWindow1.dismiss();
        }
    };

    private void setDistancePopList() {
        distanceList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            distanceList.add(arr[i]);
        }
        distanceListAdapter = new DistancePopListAdapter(context, distanceList);
        distancePopList.setAdapter(distanceListAdapter);

    }


    private void setListData() {
        if (refsh) {
            list = null;
            list = new ArrayList<>();
            refsh = false;
            for (int i = 0; i < nearMovie.getData().getDeals().size(); i++) {
                list.add(nearMovie.getData().getDeals().get(i));
            }
            movieReleasesAdapter = new MovieReleaseShopAdapter(context, list);
            listView.setAdapter(movieReleasesAdapter);
        }
        for (int i = 0; i < nearMovie.getData().getDeals().size(); i++) {
            list.add(nearMovie.getData().getDeals().get(i));
        }
        if (first) {
            movieReleasesAdapter = new MovieReleaseShopAdapter(context, list);
            listView.setAdapter(movieReleasesAdapter);
            first = false;
        } else {
            movieReleasesAdapter.notifyDataSetChanged();
        }

    }

    private void doGetData(int i, int sort) {
        Map<String, String> shopMap = new HashMap<>();
        shopMap.put("city_id", "400010000");
        shopMap.put("page", i + "");
        shopMap.put("cat_ids", "323");
        shopMap.put("subcat_ids", "345");
        shopMap.put("radius", "3000");
        shopMap.put("sort", sort + "");
        shopMap.put("page_size", "10");


        OkHttpUtils.getInstance().doGet("http://apis.baidu.com/baidunuomi/openapi/searchdeals", shopMap).excute(new OkHttpUtils.OKCallBack() {
            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onResponse(String message) {
                movieResult = message;
                Gson gson = new Gson();
                nearMovie = gson.fromJson(movieResult, NearbyShops.class);
                //Log.i("gl","现在的电影数据是:"+movieResult);
                setListData();

            }
        });
    }
}
