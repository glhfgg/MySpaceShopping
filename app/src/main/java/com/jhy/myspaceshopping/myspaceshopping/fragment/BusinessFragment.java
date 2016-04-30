package com.jhy.myspaceshopping.myspaceshopping.fragment;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.activity.HomeSearchActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.MapBaiduActivity;
import com.jhy.myspaceshopping.myspaceshopping.adapter.PagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/28.
 */
public class BusinessFragment extends Fragment {
    View v;
    ViewPager vpagerStore;//ViewPager容器
    FragmentManager fragmentManager;//Fragment(自定义控件)管理器
    List<Fragment> list;//存放Fragment(自定义控件)的集合
    RadioGroup grpBusiness;
    RadioButton rbtnBusinessAll;//全部商家
    RadioButton rbtnBusinessCheap;//优惠商家

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //将布局文件转化成view对象
        View v = inflater.inflate(R.layout.fragment_business, container, false);
       init();
        //support.v4  低版本获得fragmentmanager
        fragmentManager = getFragmentManager();
        //fragmentMtanager管理的fragment
        data();
        //自定义的PagerAdapter，将ViewPager与fragment建立连接
        PagerAdapter pagerAdap = new PagerAdapter(fragmentManager, list);
        vpagerStore.setAdapter(pagerAdap);
        FragmentManager  childFragmentManager=getChildFragmentManager();
        FragmentTransaction transition =childFragmentManager.beginTransaction();
        UpButtonSetFragment upButtonSetFragment = new UpButtonSetFragment();
        transition.add(R.id.upbutton_container, upButtonSetFragment);
        transition.commit();
        onDetach();
       return v;
    }
    //控件初始化
    private void init(){
        vpagerStore = (ViewPager) v.findViewById(R.id.vpager_storeall);
        grpBusiness = (RadioGroup) v.findViewById(R.id.grp_business);
        rbtnBusinessAll = (RadioButton) v.findViewById(R.id.rbtn_businessall);
        rbtnBusinessCheap = (RadioButton) v.findViewById(R.id.rbtn_businesscheap);
        //添加监听事件
        //ViewPager监听
        vpagerStore.setOnPageChangeListener(vpagerListener);
        //RadioButton监听
        rbtnBusinessAll.setOnClickListener(rbtnListener);
        rbtnBusinessCheap.setOnClickListener(rbtnListener);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //ViewPager监听
    //当选择某一Fragment则切换到对应位置的RadioButton
    ViewPager.OnPageChangeListener vpagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //当选择某一Fragment则切换到对应位置的RadioButton
            RadioButton rb = (RadioButton) grpBusiness.getChildAt(position);
            rb.setChecked(true);
            rb.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //RadioButton监听 （全部商家、优惠商家）
    //当选择某一按钮则切换到对应位置的ViewPager中的Fragment
    View.OnClickListener rbtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rbtn_businessall:
                    vpagerStore.setCurrentItem(0);//当选择某一按钮则切换到对应位置的ViewPager中的Fragment
                    break;
                case R.id.rbtn_businesscheap:
                    vpagerStore.setCurrentItem(1);
                    break;
                default:
                    break;
            }
        }
    };

    //往FragmentManager中加入两个Fragment
    private void data() {
        list = new ArrayList<Fragment>();
        list.add(new StoreAllFragment());
        list.add(new StoreCheapFragment());
    }

    //地图控件点击事件
    public void onClickMap(View view) {
        Intent intentMap = new Intent(this.getActivity(), MapBaiduActivity.class);
        startActivity(intentMap);
    }

    //搜索控件点击事件
    public void onClickSearch(View view) {
        Intent intentSearch = new Intent(this.getActivity(), HomeSearchActivity.class);
        startActivity(intentSearch);
    }
}
