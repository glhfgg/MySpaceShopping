package com.jhy.myspaceshopping.myspaceshopping.util;

import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.jhy.myspaceshopping.myspaceshopping.activity.MapBaiduActivity;

import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/26.
 *
 * 实现BDLocationListener接口
 * 接收异步返回的定位结果
 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
*/
public class MyLocationListener implements BDLocationListener {


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if(bdLocation==null){
            return;
        }
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

}


