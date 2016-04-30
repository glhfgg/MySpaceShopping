package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.fragment.BannerFragment;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MainActivity extends FragmentActivity {
    RadioGroup rgMain;
    Fragment homeFragment;//首页-fragment
    Fragment bannerFragment;
    Fragment businessFragment;//周边-fragment
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ssssss
        int sss;
        init();
    }

    private void init() {
        rgMain = (RadioGroup) findViewById(R.id.rg_main);
        rgMain.setOnCheckedChangeListener(radioGroupListener);
        FragmentManager fm = getFragmentManager();  //获得Fragment管理器
        FragmentTransaction ft = fm.beginTransaction(); //开启一个事务
        homeFragment = new HomeFragment();
        ft.add(R.id.fragment_container, homeFragment);
        ft.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        city = intent.getStringExtra("city");
    }

    RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentManager fm = getFragmentManager();  //获得Fragment管理器
            FragmentTransaction ft = fm.beginTransaction(); //开启一个事务
            hideFragment(ft);
            switch (checkedId) {
                case R.id.rbtn_main_zhuye:
                    Log.i("gl","!!@#!!!!!!!!!"+"主页");
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    ft.show(homeFragment);
                    break;
                case R.id.rbtn_main_zhoubian:
                    Log.i("gl","!@!@!@!@!!!!"+"周边");
                    if (bannerFragment == null) {
                        bannerFragment = new BannerFragment();
                    }
                    ft.show(bannerFragment);
                    break;
                case R.id.rbtn_main_ju:
                    break;
                case R.id.rbtn_main_wode:
                    break;
                case R.id.rbtn_main_genduo:

                    break;
            }
            ft.commit();
        }
    };

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (bannerFragment != null) {
            fragmentTransaction.hide(bannerFragment);
        }
    }
}
