package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.FirstClassAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.SecondClassAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.FirstClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.SecondClassItemModel;
import com.jhy.myspaceshopping.myspaceshopping.util.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/19.
 */
public class MapBaiduActivity extends Activity {
    MapView mapvSlideMap = null;// MapView 显示百度地图
    TextView textMapXY = null;//地图定位后的地点结果
    RadioButton rbtnMaptypes;//按类别查询当前位置附近的商店
    public LocationClient mLocationClient = null;
    BaiduMap mBaiduMap;//定位图层
    ImageButton imgDingwei;//更新图层，刷新当前位置
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
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //注册监听函数
        //接收异步返回的定位结果
        //监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation== null) {
                    return;
                }
                //定位的结果显示在TextView中
                textMapXY.setText(bdLocation.getAddrStr());
                // 构造定位数据
                MyLocationData data = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude()).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(data);
                //定义Maker坐标点
                LatLng latLng = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());

                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(bdLocation.getTime());
                sb.append("\nerror code : ");
                sb.append(bdLocation.getLocType());
                sb.append("\nlatitude : ");
                sb.append(bdLocation.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(bdLocation.getLongitude());
                sb.append("\nradius : ");
                sb.append(bdLocation.getRadius());
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
                sb.append("\nlocationdescribe : ");
                sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
                List<Poi> list = bdLocation.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                Log.i("BaiduLocationApiDem", sb.toString());
            }

        });
        initLocation();
        mLocationClient.start();

    }
    //控件初始化
    private void init() {
        mapvSlideMap = (MapView) findViewById(R.id.mapv_slidemap);//获取地图控件引用
        textMapXY = (TextView) findViewById(R.id.text_mapxy);//最终定位的地址结果
        rbtnMaptypes = (RadioButton) findViewById(R.id.rbtn_maptypes);//点击获得附近商店的位置
        imgDingwei=(ImageButton) findViewById(R.id.img_dingwei);//刷新当前位置
        //RadioButton监听
        rbtnMaptypes.setOnClickListener(rbtnListener);
        //更新当前位置
        imgDingwei.setOnClickListener(imgListener);
        //弹出PopupWindow时，背景变暗的动画
        animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_anim);
        animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
    }

    //RadioButton监听 --全部分类
    View.OnClickListener rbtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            typeData();
            initPopup();
            btnOnClick();
        }
    };

    View.OnClickListener imgListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mapvSlideMap.refreshDrawableState();
        }
    };
    //PopupWindow中ListView中存进的数据
    //全部分类--data
    private void typeData() {
        firstList = new ArrayList<FirstClassItemModel>();

        //1
        firstList.add(new FirstClassItemModel(1, "全部分类", new ArrayList<SecondClassItemModel>()));
        //2
        ArrayList<SecondClassItemModel> secondList2 = new ArrayList<SecondClassItemModel>();
        secondList2.add(new SecondClassItemModel(201, "自助餐"));
        secondList2.add(new SecondClassItemModel(202, "西餐"));
        secondList2.add(new SecondClassItemModel(203, "川菜"));
        firstList.add(new FirstClassItemModel(2, "美食", secondList2));
        //3
        ArrayList<SecondClassItemModel> secondList3 = new ArrayList<SecondClassItemModel>();
        secondList3.add(new SecondClassItemModel(301, "天津"));
        secondList3.add(new SecondClassItemModel(302, "北京"));
        secondList3.add(new SecondClassItemModel(303, "秦皇岛"));
        secondList3.add(new SecondClassItemModel(304, "沈阳"));
        secondList3.add(new SecondClassItemModel(305, "大连"));
        secondList3.add(new SecondClassItemModel(306, "哈尔滨"));
        secondList3.add(new SecondClassItemModel(307, "锦州"));
        secondList3.add(new SecondClassItemModel(308, "上海"));
        secondList3.add(new SecondClassItemModel(309, "杭州"));
        secondList3.add(new SecondClassItemModel(310, "南京"));
        secondList3.add(new SecondClassItemModel(311, "嘉兴"));
        secondList3.add(new SecondClassItemModel(312, "苏州"));
        firstList.add(new FirstClassItemModel(3, "旅游", secondList3));
        //4
        ArrayList<SecondClassItemModel> secondList4 = new ArrayList<SecondClassItemModel>();
        secondList4.add(new SecondClassItemModel(401, "南开区"));
        secondList4.add(new SecondClassItemModel(402, "和平区"));
        secondList4.add(new SecondClassItemModel(403, "河西区"));
        secondList4.add(new SecondClassItemModel(404, "河东区"));
        secondList4.add(new SecondClassItemModel(405, "滨海新区"));
        firstList.add(new FirstClassItemModel(4, "电影", secondList4));
        //5
        firstList.add(new FirstClassItemModel(5, "手机", new ArrayList<SecondClassItemModel>()));
        //6
        firstList.add(new FirstClassItemModel(6, "娱乐", null));
        //7
        firstList.add(new FirstClassItemModel(7, "全部分类", null));
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

    }

    //刷新右侧ListView
    private void updateSecondListView(List<SecondClassItemModel> listSecond, SecondClassAdapter secondAdapter) {
        secondList.clear();
        secondList.addAll(listSecond);
        secondAdapter.notifyDataSetChanged();
    }

    //处理点击结果
    private void result(String selectedName) {
        Toast.makeText(this, selectedName, Toast.LENGTH_SHORT).show();
        rbtnMaptypes.setText(selectedName);

    }

    //返回控件点击事件
    public void onClickBack(View view) {
        Intent intent = new Intent(MapBaiduActivity.this, BusinessActivity.class);
        startActivity(intent);
    }

}
