package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import android.widget.RadioGroup;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.fragment.BannerFragment;
import com.jhy.myspaceshopping.myspaceshopping.fragment.HomeFragment;
import com.jhy.myspaceshopping.myspaceshopping.fragment.JuMainFragment;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MainActivity extends FragmentActivity {
    RadioGroup rgMain;
    Fragment homeFragment;
    Fragment bannerFragment;
    String city;
    JuMainFragment  jufragment;
    MyUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user != null) {
            jufragment = new JuMainFragment();
            android.support.v4.app.FragmentManager fm4 = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft4 = fm4.beginTransaction();
            ft4.add(R.id.fragment_container, jufragment);
            ft4.commit();
        }
    }

    private void init() {
        user = BmobUser.getCurrentUser(MainActivity.this,MyUser.class);
        android.support.v4.app.FragmentManager fm4 = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft4 = fm4.beginTransaction();

        rgMain = (RadioGroup) findViewById(R.id.rg_main);
        rgMain.setOnCheckedChangeListener(radioGroupListener);

        FragmentManager fm = getFragmentManager();  //获得Fragment管理器
        FragmentTransaction ft = fm.beginTransaction(); //开启一个事务

        homeFragment = new HomeFragment();
        ft.add(R.id.fragment_container, homeFragment);
        ft.commit();

           if( user != null){
               jufragment = new JuMainFragment();
               ft4.add(R.id.fragment_container,jufragment);
               ft4.show(jufragment);
               ft4.commit();
           }
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

            android.support.v4.app.FragmentManager fm4 = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft4 = fm4.beginTransaction();

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
                    Log.i("gl","!@!@!@!@!!!!"+"聚");

//                    Log.i("result","~~~~~~~~~~~~~~~"+user.getUsername().equals(null));
                    if(user==null){
                        Intent intent = new Intent(MainActivity.this ,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        if (jufragment == null) {
                            jufragment = new JuMainFragment();

                            ft4.show(jufragment);
                            ft4.commit();
                        }
                    }

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
        if (bannerFragment != null) {
            fragmentTransaction.hide(bannerFragment);
        }
    }



}
