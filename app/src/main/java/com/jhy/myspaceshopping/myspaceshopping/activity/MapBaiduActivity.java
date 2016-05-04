package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jhy.myspaceshopping.myspaceshopping.R;

import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/19.
 */
public class MapBaiduActivity extends Activity {
    MapView mapvSlideMap = null;// MapView 显示百度地图
    TextView textMapXY=null;//地图定位后的地点
    public LocationClient mLocationClient = null;
    BaiduMap mBaiduMap;

    double  mLongitude;//经度
    double mLatitude;//纬度
    float  mRadius;//半径范围
    BitmapDescriptor  mCurrentMarker;
    PoiSearch mPoiSearch;//POI检索对象
    List<Poi> list;//poi数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //控件初始化
        mapvSlideMap = (MapView) findViewById(R.id.mapv_slidemap);//获取地图控件引用
        textMapXY = (TextView) findViewById(R.id.text_mapxy);//最终定位的地址结果
        //地图图层
        mBaiduMap = mapvSlideMap.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //普通地图  地图类型：普通地图 卫星地图 空白地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);
       // 开启百度城市热力图
       // mBaiduMap.setBaiduHeatMapEnabled(true);

        //Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //注册监听函数
        //接收异步返回的定位结果
        //监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation==null){
                    return;
                }
                textMapXY.setText(bdLocation.getAddrStr());
                mLongitude=bdLocation.getLongitude();
                mLatitude=bdLocation.getLatitude();
                mRadius= bdLocation.getRadius();
                 list= bdLocation.getPoiList();// POI数据
                //定位没结果时返回数据
                StringBuffer sb= new StringBuffer(256);
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(bdLocation.getSpeed());// 单位：公里每小时
                    sb.append("\nsatellite : ");
                    sb.append(bdLocation.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(bdLocation.getAltitude());// 单位：米
                    sb.append("\ndirection : ");
                    sb.append(bdLocation.getDirection());// 单位度
                    sb.append("\naddr : ");
                    sb.append(bdLocation.getAddrStr());
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");

                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("\naddr : ");
                    sb.append(bdLocation.getAddrStr());
                    //运营商信息
                    sb.append("\noperationers : ");
                    sb.append(bdLocation.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.i("Location result","-------------"+sb.toString());
            }
        });

       //POI检索
        //创建POI检索实例
        mPoiSearch=PoiSearch.newInstance();
        //创建POI检索监听者
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
            public void onGetPoiResult(PoiResult result){
                //获取POI检索结果
                StringBuffer sbPoiresult= new StringBuffer(256);
                if(list!=null){
                    for (Poi p : list) {
                        sbPoiresult.append("\npoi= : ");
                        sbPoiresult.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                Log.i("Poi result","-------------"+ sbPoiresult.toString());
            }
            public void onGetPoiDetailResult(PoiDetailResult result){
                //获取Place详情页检索结果

                Log.i("PoiDetail result","-------------"+ result);
            }
        };
        //设置POI检索监听者
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        //发起检索请求
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("武汉")
                .keyword("美食")
                .pageNum(10));
       initLocation();
       mLocationClient.start();
    }

    //LocationClientOption类，该类用来设置定位SDK的定位方式
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapvSlideMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapvSlideMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapvSlideMap.onDestroy();
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //释放POI检索实例
        mPoiSearch.destroy();
    }

    //返回控件点击事件
    public void onClickBack(View view) {
        Intent intent = new Intent(MapBaiduActivity.this, BusinessActivity.class);
        startActivity(intent);
    }

}
