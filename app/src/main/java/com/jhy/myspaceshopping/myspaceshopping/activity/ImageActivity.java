package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ImageActivity extends Activity{

    ImageView img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        img = (ImageView) findViewById(R.id.img);
        Intent intent = getIntent();
        Picasso.with(this).load(   intent.getExtras().getString("img")).into( img);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
           finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截菜单键
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
        }
        return super.onKeyDown(keyCode, event);
    }


}
