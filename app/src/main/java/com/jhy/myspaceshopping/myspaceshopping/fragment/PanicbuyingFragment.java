package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhy.myspaceshopping.myspaceshopping.R;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PanicbuyingFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_panicbuying,container,false);
        return view;
    }
}
