package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import android.widget.RadioGroup;


import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.fragment.BusinessFragment;
import com.jhy.myspaceshopping.myspaceshopping.fragment.HomeFragment;
import com.jhy.myspaceshopping.myspaceshopping.fragment.JuMainFragment;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/4/27.
 */

public class MainActivity extends FragmentActivity {
    RadioGroup rgMain;
    Fragment homeFragment;//首页-fragment
    Fragment businessFragment;//周边-fragment
    String city;
    JuMainFragment jufragment;
    MyUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (user == null) {
//            finish();
//            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
//
//        }
//    }

    private void init() {
        user = BmobUser.getCurrentUser(MainActivity.this, MyUser.class);
        FragmentManager fm4 = getSupportFragmentManager();
        FragmentTransaction ft4 = fm4.beginTransaction();

        rgMain = (RadioGroup) findViewById(R.id.rg_main);
        rgMain.setOnCheckedChangeListener(radioGroupListener);

        FragmentManager fm = getSupportFragmentManager();  //获得Fragment管理器
        FragmentTransaction ft = fm.beginTransaction(); //开启一个事务
        homeFragment = new HomeFragment();
        ft.add(R.id.fragment_container, homeFragment);
        ft.commit();

        if (user != null) {
            jufragment = new JuMainFragment();
            ft4.add(R.id.fragment_container, jufragment);
            ft4.commit();
//            Log.i("life","JU------init--");
        }
        Log.i("life","JU------init--"+(user==null));


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
            FragmentManager fm = getSupportFragmentManager();  //获得Fragment管理器
            FragmentTransaction ft = fm.beginTransaction(); //开启一个事务
            FragmentManager fm4 = getSupportFragmentManager();
            FragmentTransaction ft4 = fm4.beginTransaction();


            switch (checkedId) {
                case R.id.rbtn_main_zhuye:
                    // Log.i("gl","!!@#!!!!!!!!!"+"主页");

                        homeFragment = new HomeFragment();
                    ft.replace(R.id.fragment_container, homeFragment);
                    break;

                case R.id.rbtn_main_zhoubian:
//                    Log.i("gl","!@!@!@!@!!!!"+"周边");
                    businessFragment = new BusinessFragment();
                    ft.replace(R.id.fragment_container, businessFragment);
                    break;

                case R.id.rbtn_main_ju:
                    // Log.i("gl","!@!@!@!@!!!!"+"聚");

//                    Log.i("result","~~~~~~~~~~~~~~~"+user.getUsername().equals(null));
                    if (user == null) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        jufragment = new JuMainFragment();
                        ft4.replace(R.id.fragment_container, jufragment);
                        ft4.commit();
//                        Log.i("life","JU------Click--");
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


}
