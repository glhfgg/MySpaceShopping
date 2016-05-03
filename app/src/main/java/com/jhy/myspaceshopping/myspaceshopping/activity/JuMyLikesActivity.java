package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuNearAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 俊峰 on 2016/5/2.
 */

public class JuMyLikesActivity  extends Activity{

    String img;
    TextView title;
    ImageView back;
    ListView list;
    List<JuUniversalData> listdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_popitem);

        title = (TextView) findViewById(R.id.ju_share_title);
        list = (ListView) findViewById(R.id.ju_share_list);
        back = (ImageView) findViewById(R.id.ju_share_back);

        title.setText("我的关注");
        back.setOnClickListener(click);
        searchUser();
        list.setOnItemClickListener(ListClick);
    }



    private void searchUser(){
        listdata = new ArrayList<>();
        MyUser user = BmobUser.getCurrentUser(this,MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("likes", new BmobPointer(user));
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //执行查询方法
        query.findObjects(JuMyLikesActivity.this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub

                Toast.makeText(JuMyLikesActivity.this, "查询成功：共"+object.size()+"条数据。", Toast.LENGTH_SHORT).show();
                for (int i =0;i<object.size();i++) {
                    //获得Name的信息
                    String name =  object.get(i).getPersonname();
                    //获得用户自我介绍
                    String  storecontent =  object.get(i).getContent();
                    String userName = object.get(i).getUsername();

                    //获取用户头像
                    if(object.get(i).getIcon() != null){
                        img ="http://file.bmob.cn/"+ object.get(i).getIcon().getUrl();
                    }else{
                        img = "http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png";
                    }

                    String us =  object.get(i).getObjectId();
                    JuUniversalData data = new JuUniversalData(name,us,null,"500m",img,storecontent,userName,null,null);
                    Log.i("result","++++____"+img);
                    listdata.add(data);
                    data = null;

                }

                JuNearAdapter adapter = new JuNearAdapter(JuMyLikesActivity.this,listdata);
                list.setAdapter(adapter);

            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(JuMyLikesActivity.this,"查询失败："+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    AdapterView.OnItemClickListener ListClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


             Intent intent = new Intent(JuMyLikesActivity.this, JuPersonUserActivity.class);
            intent.putExtra("PersonUser",listdata.get(position).getScore());
            intent.putExtra("PersonUserName",listdata.get(position).getSalebefore());
            intent.putExtra("PersonUserPhoto",listdata.get(position).getPhoto());
            startActivity(intent);
        }
    };

}
