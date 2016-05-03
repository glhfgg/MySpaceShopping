package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Application;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by TOSHIBA on 2016/5/3.
 */
public class MapApplication extends Application {
    @Override
    public void onCreate() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        //Log.i("result","--------------------sdk");
        super.onCreate();
    }
}
