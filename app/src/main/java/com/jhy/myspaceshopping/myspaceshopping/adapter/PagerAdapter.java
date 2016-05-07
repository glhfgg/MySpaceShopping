package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/18.
 */

//ViewPager容器
// 适配器PagerAdapter ：与ListView功能差不多
public class PagerAdapter extends FragmentPagerAdapter {

    List<Fragment> list;
    public PagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
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
