package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by TOSHIBA on 2016/4/18.
 */

//ViewPager容器
// 适配器PagerAdapter ：与ListView功能差不多
public class PagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> list;
    public PagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("gl","####################");
        return list.get(position);
    }

    @Override
    public int getCount() {
        Log.i("gl","!!!!!!!!!!!@@@@@@@@@@@@@@"+list.size());
        return list==null?0:list.size();
    }
}
