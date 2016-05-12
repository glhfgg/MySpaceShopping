package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhy.myspaceshopping.myspaceshopping.R;

/**
 * Created by TOSHIBA on 2016/4/30.
 */
public class MoreFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_more,container,false);
        return view;
    }
}
