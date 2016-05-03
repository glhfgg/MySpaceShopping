package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2016/4/15.
 */




public class JuNewsActivity extends Activity{
    ImageView back;

    TextView att;
    TextView pinlun;
    TextView zan;
    //小红点
    TextView attRed;
    TextView pinlunRed;
    TextView zanRed;
    final  List<Integer> num = new ArrayList<Integer>();
    int s= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_news);
        init();
        searchAttMy();

    }

    private void init(){
        back = (ImageView) findViewById(R.id.ju_news_back);
        att = (TextView) findViewById(R.id.ju_news_atttmy);
        pinlun = (TextView) findViewById(R.id.ju_news_pinlun);
        zan = (TextView) findViewById(R.id.ju_news_dianzan);

        attRed = (TextView) findViewById(R.id.att_xiaohongdian);
        pinlunRed = (TextView) findViewById(R.id.pinlun_xiaohongdian);
        zanRed = (TextView) findViewById(R.id.zan_xiaohongdian);

        att.setOnClickListener(click);
        pinlun.setOnClickListener(click);
        zan.setOnClickListener(click);
        back.setOnClickListener(click);
    }

    private void searchAttMy(){

     final  MyUser user = BmobUser.getCurrentUser(this,MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("friend", true);
        query.findObjects(this, new FindListener<MyUser>() {

            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub
                Log.i("life", "查询个数："+object.size());

               for(int i=0;i<object.size();i++){

                   BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                   MyUser post = new MyUser();
                   post.setObjectId( object.get(i).getObjectId());
                   query.addWhereRelatedTo("likes", new BmobPointer(post));
                   query.findObjects(JuNewsActivity.this, new FindListener<MyUser>() {

                       @Override
                       public void onSuccess(List<MyUser> object) {
                           // TODO Auto-generated method stub

                           for(int i =0;i<object.size();i++){

                               if(object.get(i).getUsername().toString().equals(user.getUsername().toString())){
                                   s++;
                               }
                           }
                           Log.i("life", "查询个数sssss："+s);
                           if(s==0){
                           }else{
                               attRed.setText(""+s);
                               attRed.setVisibility(View.VISIBLE);
                           }

                       }

                       @Override
                       public void onError(int code, String msg) {
                           // TODO Auto-generated method stub
                           Log.i("life", "查询失败："+code+"-"+msg);
                       }
                   });
               }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Log.i("life", "查询失败："+code+"-"+msg);

            }
        });
    }


    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ju_news_atttmy:
                    Intent intent = new Intent(JuNewsActivity.this,JuMyFansActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_news_pinlun:
                    intent = new Intent(JuNewsActivity.this, JuMyShareActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_news_dianzan:
                    intent = new Intent(JuNewsActivity.this, JuMyShareActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_news_back:
                    finish();
                    break;

            }
        }
    };


}
