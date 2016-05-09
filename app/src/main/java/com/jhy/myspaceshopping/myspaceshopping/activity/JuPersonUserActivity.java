package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuFriendsAdapter;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuNearAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.jhy.myspaceshopping.myspaceshopping.object.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/5/3.
 */
public class JuPersonUserActivity extends Activity{


    String  storephoto;
    String contentphoto;
    ImageView back;
    ListView list;
    Context context=this;
    List<JuUniversalData> listdata = new ArrayList<>();

    //ListHeader
    TextView name;
    TextView time;
    TextView content;
    TextView city;
    ImageView img;
    Intent intent;
    MyUser user;
    List<String> ID;
    View v;
    int j;
    TextView texts ;
    TextView zhuanfa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_person);
        //初始化控件 添加listView 的 Header
        user = new MyUser();
         intent = getIntent();
        init();
        setListHeader();
//        searchPost();
        back.setOnClickListener(click);
        list.setOnItemClickListener(Click);

    }


    private void init(){
        list = (ListView) findViewById(R.id.ju_person_list);
        back = (ImageView) findViewById(R.id.ju_person_back);

        LayoutInflater inflater = LayoutInflater.from(this);
         v = inflater.inflate(R.layout.item_ju_person_header,null);

        //初始化控件
        name = (TextView) v.findViewById(R.id.ju_person_name);
        time = (TextView) v.findViewById(R.id.ju_person_creattime);
        content = (TextView) v.findViewById(R.id.ju_person_content);
        city = (TextView) v.findViewById(R.id.ju_person_location);
        img = (ImageView) v.findViewById(R.id.ju_person_bigphoto);
        //添加header

    }

    private void setListHeader(){
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", intent.getExtras().getString("PersonUserName").toString());
        query.findObjects(context, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub
//
                Toast.makeText(JuPersonUserActivity.this, "查询用户成功", Toast.LENGTH_SHORT).show();
                name.setText(object.get(0).getPersonname());
                Log.i("life",object.get(0).getPersonname());
                time.setText(object.get(0).getCreatedAt());
                content.setText(object.get(0).getContent());
                city.setText(object.get(0).getCity());

                if( object.get(0).getIcon() != null){
                    Picasso.with(context).load("http://file.bmob.cn/"+object.get(0).getIcon().getUrl().toString()).into(img);
                }else{
                    Picasso.with(context).load("http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png").into(img);
                }
                list.addHeaderView(v);
                searchPost();
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
//
                Toast.makeText(JuPersonUserActivity.this, "查询用户失败"+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void searchPost(){
        ID = new ArrayList<>();
      final  MyUser user = new MyUser();

        user.setObjectId(intent.getExtras().getString("PersonUser").toString());
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", intent.getExtras().getString("PersonUser").toString());    // 查询当前用户的所有帖子

        Log.i("life","------------------!!!!Intent="+intent.getExtras().getString("PersonUser").toString());
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> object) {
                // TODO Auto-generated method stub
                Log.i("life","------------------!!!!"+object.size());
                if (object.size() != 0) {
                    for (int i = 0; i < object.size(); i++) {
                        String username =intent.getExtras().getString("PersonUserName").toString();
                        Log.i("life","------------------!!!!Name"+ user.getPersonname());
                        String userscore = object.get(i).getCreatedAt();

                            storephoto = intent.getExtras().getString("PersonUserPhoto").toString();

                        String storecontent = object.get(i).getContent();
                        String forwarding = "转发";
                        String comments = "评论";
                        String praice = "点赞" + i;
                        if (object.get(i).getImage() != null) {
                            contentphoto =  object.get(i).getImage().getUrl().toString();
                        } else {
                            contentphoto = null;
                        }

                        JuUniversalData data = new JuUniversalData(username, userscore, null, contentphoto, storephoto, storecontent, forwarding, comments, praice);
                        listdata.add(data);
                        data = null;

                        String Id = object.get(i).getObjectId();
                        ID.add(Id);
                        Id = null;
                    }
                }
                JuFriendsAdapter adapter = new JuFriendsAdapter(JuPersonUserActivity.this,listdata);
                list.setAdapter(adapter);
            }

                @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
//                toast("查询失败:"+msg);
            }
        });

    }


    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    AdapterView.OnItemClickListener Click = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(JuPersonUserActivity.this, JuCommentActivity.class);
            j = position;
//            listdata.get(position).getDistance();    //内容图片
//            listdata.get(position).getPhoto();       //用户头像
//            listdata.get(position).getContent();     //发布内容
//            listdata.get(position).getName();        //用户名
//            listdata.get(position).getScore();       //发布日期
//            listdata.get(position).getSalebefore();  //转发
//            listdata.get(position).getSalelater();   //评论
//            listdata.get(position).getSalenum();     // 点赞

            if(position ==0){

            }

            if(position >=1){
                intent.putExtra("JuConImg", listdata.get(position-1).getDistance());
                intent.putExtra("JuUseImg", listdata.get(position-1).getPhoto());
                intent.putExtra("JuConTex", listdata.get(position-1).getContent());
                intent.putExtra("JuUseNam", listdata.get(position-1).getName());
                intent.putExtra("JuCrtTim", listdata.get(position-1).getScore());
                intent.putExtra("JuForNum", listdata.get(position-1).getSalebefore());
                intent.putExtra("JuConNum", listdata.get(position-1).getSalelater());
                intent.putExtra("JuPraNum", listdata.get(position-1).getSalenum());
                intent.putExtra("JuId", ID.get(position-1).toString());
                Log.i("result","ID-----------"+ ID.get(position-1).toString());
                startActivity(intent);
            }


            }





    };

}