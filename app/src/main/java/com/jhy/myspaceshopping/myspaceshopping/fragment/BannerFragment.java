package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.BannerViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class BannerFragment extends Fragment {
    ViewPager banner_viewPager;
    Context context;
    View viewHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
        viewHeader =inflater.inflate(R.layout.viewheader_banner,container,false);
        context = this.getActivity();
        init();
        showBanner();
        return viewHeader;
    }

    /**
     * 初始化控件
     */
    private void init() {

        banner_viewPager = (ViewPager)viewHeader.findViewById(R.id.banner_viewPager);

    }

    /**
     * 显示banner
     */
    public void showBanner(){
        List<View> bannerList = new ArrayList<>();
        // View view = LayoutInflater.from(context).inflate(R.layout.viewpager_one,null);
        bannerList.add(setImageView(R.mipmap.banner_one));
        bannerList.add(setImageView(R.mipmap.banner_two));
        bannerList.add(setImageView(R.mipmap.banner_three));
        BannerViewPagerAdapter bannerViewPagerAdapter = new BannerViewPagerAdapter(context,bannerList);
        banner_viewPager.setAdapter(bannerViewPagerAdapter);
    }

    /**
     * 为banner填充图片
     * @param resId
     * @return
     */
    private View setImageView(int resId) {
        ImageView imageview = new ImageView(context);
        imageview.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT));
        imageview.setImageResource(resId);
        return imageview;
    }

}
