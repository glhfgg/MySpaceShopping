package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.activity.WebViewActivity;
import com.jhy.myspaceshopping.myspaceshopping.adapter.MovieReleaseAdapter;
import com.jhy.myspaceshopping.myspaceshopping.objectmode.Movie;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;

/**
 * Created by Administrator on 2016/4/20.
 */
public class MovieReleaseFragment extends Fragment {
    HListView hlistView;
    View view;
    Context contex;
    List<Movie> list;
    String[] movies={"http://www.iqiyi.com/lib/m_209860914.html","http://www.iqiyi.com/lib/m_207907614.html","http://www.iqiyi.com/lib/m_205337914.html?src=search",
    "http://www.iqiyi.com/lib/m_205510214.html","http://www.iqiyi.com/lib/m_205492114.html","http://www.iqiyi.com/lib/m_207969714.html",
    "http://www.iqiyi.com/lib/m_209665014.html","http://www.iqiyi.com/lib/m_209488314.html","http://www.iqiyi.com/lib/m_209047514.html?src=search",
    "http://www.iqiyi.com/lib/m_209170214.html"};

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_release, container, false);
        contex = this.getActivity();
        init();

        return view;
    }

    private void init() {
        hlistView = (HListView) view.findViewById(R.id.list_movie_release);
        sethListData();
        MovieReleaseAdapter adapter = new MovieReleaseAdapter(contex, list);
        hlistView.setAdapter(adapter);
        hlistView.setOnItemClickListener(hlistViewListener);
    }
    it.sephiroth.android.library.widget.AdapterView.OnItemClickListener hlistViewListener = new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(contex, WebViewActivity.class);
            intent.putExtra("shopUrl",movies[i]);
            startActivity(intent);
        }
    };
    private void sethListData() {
        list =new ArrayList<>();
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48100974","夺命推理","6.6"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48100988","我的特工爷爷","5.5"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48101076","3D食人虫","5.5"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48100951","伦敦陷落","6.7"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48101103","我的新野蛮女友","6.8"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48100949","奇幻森林","7.7"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48101229","白毛女","6.2"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48100972","猫脸老太太","3.7"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48129535","太空熊猫英雄归来","3.5"));
        list.add(new Movie("http://v.juhe.cn/movie/picurl?48100986","睡在我上铺的兄弟","6"));

    }
}
