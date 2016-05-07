package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.FirstClassAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.SecondClassAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.FirstClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.SecondClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.util.OnReceiveLocationListener;
import com.jhy.myspaceshopping.myspaceshopping.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/19.
 */
public class MapBaiduActivity extends Activity implements OnReceiveLocationListener{
    MapView mapvSlideMap = null;// MapView 显示百度地图
    TextView textMapXY=null;//地图定位后的地点结果
    RadioButton rbtnMaptypes;//按类别查询当前位置附近的商店
    public LocationClient mLocationClient = null;
    BaiduMap mBaiduMap;//定位图层

    double  mLongitude;//经度
    double mLatitude;//纬度
    float  mRadius;//半径范围
    BitmapDescriptor  mCurrentMarker;
    PoiSearch mPoiSearch;//POI检索对象
    List<Poi> list;//poi数据

    //popupWindow
    /**
     * 左侧一级分类的数据
     */
    private List<FirstClassItemModel> firstList;
    /**
     * 右侧二级分类的数据
     */
    private List<SecondClassItemModel> secondList;
    /**
     * 使用PopupWindow显示一级分类和二级分类
     */
    private PopupWindow popupWindow;

    /**
     * 左侧和右侧两个ListView--popupWindow
     */
    private ListView leftLV, rightLV;

    //弹出PopupWindow时，背景变暗的动画
    private Animation animIn, animOut;

    //定义Maker坐标点
    LatLng point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //控件初始化
       init();
        //地图图层
        mBaiduMap = mapvSlideMap.getMap();

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
                onReceive(bdLocation);
                textMapXY.setText(bdLocation.getAddrStr());

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


        //定义Maker坐标点

        //构建Marker图标
       /* mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.biaozhu);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(mCurrentMarker);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);*/

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

    private void init(){
        mapvSlideMap = (MapView) findViewById(R.id.mapv_slidemap);//获取地图控件引用
        textMapXY = (TextView) findViewById(R.id.text_mapxy);//最终定位的地址结果
        rbtnMaptypes=(RadioButton) findViewById(R.id.rbtn_maptypes);//点击获得附近商店的位置
        //RadioButton监听
        rbtnMaptypes.setOnClickListener(rbtnListener);
        //弹出PopupWindow时，背景变暗的动画
        animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_anim);
        animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
    }
    //RadioButton监听 --全部分类
    View.OnClickListener rbtnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            typeData();
            initPopup();
            btnOnClick();
        }
    };
    //PopupWindow中ListView中存进的数据
    //全部分类--data
    private void typeData() {
        firstList = new ArrayList<FirstClassItemModel>();
        //1
        ArrayList<SecondClassItemModel> secondList1 = new ArrayList<SecondClassItemModel>();
        secondList1.add(new SecondClassItemModel(101, "自助餐"));
        secondList1.add(new SecondClassItemModel(102, "西餐"));
        secondList1.add(new SecondClassItemModel(103, "川菜"));
        firstList.add(new FirstClassItemModel(1, "美食", secondList1));
        //2
        ArrayList<SecondClassItemModel> secondList2 = new ArrayList<SecondClassItemModel>();
        secondList2.add(new SecondClassItemModel(201, "天津"));
        secondList2.add(new SecondClassItemModel(202, "北京"));
        secondList2.add(new SecondClassItemModel(203, "秦皇岛"));
        secondList2.add(new SecondClassItemModel(204, "沈阳"));
        secondList2.add(new SecondClassItemModel(205, "大连"));
        secondList2.add(new SecondClassItemModel(206, "哈尔滨"));
        secondList2.add(new SecondClassItemModel(207, "锦州"));
        secondList2.add(new SecondClassItemModel(208, "上海"));
        secondList2.add(new SecondClassItemModel(209, "杭州"));
        secondList2.add(new SecondClassItemModel(210, "南京"));
        secondList2.add(new SecondClassItemModel(211, "嘉兴"));
        secondList2.add(new SecondClassItemModel(212, "苏州"));
        firstList.add(new FirstClassItemModel(2, "旅游", secondList2));
        //3
        ArrayList<SecondClassItemModel> secondList3 = new ArrayList<SecondClassItemModel>();
        secondList3.add(new SecondClassItemModel(301, "南开区"));
        secondList3.add(new SecondClassItemModel(302, "和平区"));
        secondList3.add(new SecondClassItemModel(303, "河西区"));
        secondList3.add(new SecondClassItemModel(304, "河东区"));
        secondList3.add(new SecondClassItemModel(305, "滨海新区"));
        firstList.add(new FirstClassItemModel(3, "电影", secondList3));
        //4
        firstList.add(new FirstClassItemModel(4, "手机", new ArrayList<SecondClassItemModel>()));
        //5
        firstList.add(new FirstClassItemModel(5, "娱乐", null));
        //6
        firstList.add(new FirstClassItemModel(6, "全部分类", null));
        //copy
        firstList.addAll(firstList);
    }
    //PopupWindow
    private void initPopup() {
        popupWindow = new PopupWindow(this);
        //存放popupWindow中的空间的view
        View view = LayoutInflater.from(this).inflate(R.layout.popuplevel_layout, null);
        leftLV = (ListView) view.findViewById(R.id.pop_listview_left);
        rightLV = (ListView) view.findViewById(R.id.pop_listview_right);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);
        //LayoutParams 属性参数
        popupWindow.setHeight(ScreenUtils.getScreenH(this) * 2 / 3);
        popupWindow.setWidth(ScreenUtils.getScreenW(this));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                leftLV.setSelection(0);
                rightLV.setSelection(0);
            }
        });

        //为了方便扩展，这里的两个ListView均使用BaseAdapter.如果分类名称只显示一个字符串，建议改为ArrayAdapter.
        //加载一级分类
        final FirstClassAdapter firstAdapter = new FirstClassAdapter(this, firstList);
        leftLV.setAdapter(firstAdapter);

        //加载左侧第一行对应右侧二级分类
        secondList = new ArrayList<SecondClassItemModel>();
        secondList.addAll(firstList.get(0).getSecondList());
        final SecondClassAdapter secondAdapter = new SecondClassAdapter(this, secondList);
        rightLV.setAdapter(secondAdapter);

        //左侧ListView点击事件
        leftLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //二级数据
                List<SecondClassItemModel> listSecond = firstList.get(position).getSecondList();
                //如果没有二级类，则直接跳转
                if (listSecond == null || listSecond.size() == 0) {
                    popupWindow.dismiss();

                    int firstId = firstList.get(position).getId();
                    String selectedName = firstList.get(position).getName();
                    result(selectedName);
                    return;
                }

                FirstClassAdapter adapter = (FirstClassAdapter) (parent.getAdapter());
                //如果上次点击的就是这一个item，则不进行任何操作
                if (adapter.getSelectPosition() == position) {
                    return;
                }

                //根据左侧一级分类选中情况，更新背景色
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();

                //显示右侧二级分类
                updateSecondListView(listSecond, secondAdapter);
            }
        });

        //右侧ListView点击事件
        rightLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupWindow，显示用户选择的分类
                popupWindow.dismiss();

                int firstPosition = firstAdapter.getSelectPosition();
                int firstId = firstList.get(firstPosition).getId();
                int secondId = firstList.get(firstPosition).getSecondList().get(position).getId();
                String selectedName = firstList.get(firstPosition).getSecondList().get(position)
                        .getName();
                result(selectedName);
            }
        });
    }

    //Button控件的点击事件
    private void btnOnClick() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(findViewById(R.id.linear_line));
            popupWindow.setFocusable(true);
        }
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

    //刷新右侧ListView
    private void updateSecondListView(List<SecondClassItemModel> listSecond, SecondClassAdapter secondAdapter) {
        secondList.clear();
        secondList.addAll(listSecond);
        secondAdapter.notifyDataSetChanged();
    }


    //处理点击结果
    private void  result(String selectedName) {
        Toast.makeText(this, selectedName, Toast.LENGTH_SHORT).show();
        rbtnMaptypes.setText(selectedName);

        }
    //返回控件点击事件
    public void onClickBack(View view) {
        Intent intent = new Intent(MapBaiduActivity.this, BusinessActivity.class);
        startActivity(intent);
    }

    @Override
    public void onReceive(BDLocation location) {
        point = new LatLng(location.getLongitude(),location.getLatitude());
        Toast.makeText(this, ""+location.getLongitude()+location.getLatitude(), Toast.LENGTH_SHORT).show();
    }
}
