package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.jhy.myspaceshopping.myspaceshopping.R;
import android.support.v4.app.FragmentTransaction;
import com.jhy.myspaceshopping.myspaceshopping.fragment.BusinessFragment;


public class BusinessActivity extends FragmentActivity {
    FragmentTransaction transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        //布局中加入自定义的Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();// //获得Fragment管理器
        transition = fragmentManager.beginTransaction();//开启一个事务
        BusinessFragment businessFragment = new BusinessFragment();
        transition.add(R.id.business_container, businessFragment);
        transition.commit();
    }
}
