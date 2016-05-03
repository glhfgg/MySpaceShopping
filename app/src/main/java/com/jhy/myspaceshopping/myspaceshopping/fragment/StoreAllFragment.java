package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.StoreAdapter;
import com.jhy.myspaceshopping.myspaceshopping.dao.BusinessConfig;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.StoreBaseModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/14.
 */

//activity_business.xml 中的正文部分的布局
//全部商家--列表
public class StoreAllFragment extends Fragment {

    ListView listStoreMsg;//商店具体信息
    List<StoreBaseModel> list;//商店信息List<T>

    String responseBack;//响应返回得到的数据
    BusinessConfig url=new BusinessConfig();// api --得到网络上的动态数据
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //将布局文件转化成view对象
        View view=inflater.inflate(R.layout.fragment_storegeneralmessage,container,false);
        //当前位置==ListView的header，当前手机智能定位的地点
        View viewLocation=inflater.inflate(R.layout.location_plate_layout, null);
        //控件初始化
        listStoreMsg= (ListView) view.findViewById(R.id.lv_storemsg);
        //实现TextView与ListView一起滑动
        listStoreMsg.addHeaderView(viewLocation);
        //适配器：连接ListView对象和数据的
        storeAllData();
        StoreAdapter storeAllAdap=new StoreAdapter(this.getActivity(),list);
        listStoreMsg.setAdapter(storeAllAdap);
        //ListView对象item的点击监听事件
        listStoreMsg.setOnItemClickListener(listener);
        return view;
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
    private List<StoreBaseModel> storeAllData(){
        list=new ArrayList<StoreBaseModel>();
        StoreBaseModel storeBaseModel;
        for(int i=0;i<20;i++){
            storeBaseModel =new StoreBaseModel("all"+i,3,"all"+i,"all"+i,"all"+i,"all"+i);
            list.add(storeBaseModel);
            storeBaseModel =null;
        }
      return list;
    }
    //从网络上获得动态数据


}
