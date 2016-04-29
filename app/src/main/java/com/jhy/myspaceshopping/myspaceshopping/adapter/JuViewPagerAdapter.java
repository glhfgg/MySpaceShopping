package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class JuViewPagerAdapter extends FragmentPagerAdapter{

    List<Fragment> list;

    public JuViewPagerAdapter(android.support.v4.app.FragmentManager manager,List<Fragment> list){
        super(manager);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

}
