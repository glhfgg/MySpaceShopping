package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.adapter.JuFriendsAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.R;
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
 * Created by Administrator on 2016/4/15.
 */
public class JuPersonActivity extends Activity {
    ImageView back;
    ListView list;
    Context context=this;
    List<JuUniversalData> listdata = new ArrayList<>();

    String  contentphoto;
    String  storephoto;

    //ListHeader
    TextView name;
    TextView time;
    TextView content;
    TextView city;
    ImageView img;
    Intent intent;

    TextView texts ;
    TextView zhuanfa;
    int j;
    List<String> ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_person);
        //初始化控件 添加listView 的 Header
        init();
        getMyIntent();
        searchPost();
        back.setOnClickListener(click);
        list.setOnItemClickListener(Click);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        MyUser  user = BmobUser.getCurrentUser(this, MyUser.class);
//
//        Log.i("result","user0--------------"+user.getIcon().getUrl().toString());
//
//        Picasso.with(this).load("http://file.bmob.cn/"+user.getIcon().getUrl().toString()).into(img);
//    }

    private void getMyIntent(){
        //获取传过来的图片URL
        MyUser user = BmobUser.getCurrentUser(this,MyUser.class);

        if( user.getIcon() != null){
            Picasso.with(context).load("http://file.bmob.cn/"+user.getIcon().getUrl().toString()).into(img);
        }else{
            Picasso.with(context).load("http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png").into(img);
        }
    }


    private void init(){
        list = (ListView) findViewById(R.id.ju_person_list);
        back = (ImageView) findViewById(R.id.ju_person_back);


        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.item_ju_person_header,null);
        list.addHeaderView(v);
        //初始化控件
        name = (TextView) v.findViewById(R.id.ju_person_name);
        time = (TextView) v.findViewById(R.id.ju_person_creattime);
        content = (TextView) v.findViewById(R.id.ju_person_content);
        city = (TextView) v.findViewById(R.id.ju_person_location);
        img = (ImageView) v.findViewById(R.id.ju_person_bigphoto);
        //添加header
        setListHeader();

    }

    private void setListHeader(){

        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", MyUser.getCurrentUser(this).getUsername());
        query.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub
                Toast.makeText(JuPersonActivity.this, "查询用户成功"+object.size(), Toast.LENGTH_SHORT).show();
                name.setText(object.get(0).getPersonname());
                time.setText(object.get(0).getCreatedAt());
                content.setText(object.get(0).getContent());
                city.setText(object.get(0).getCity());

            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(JuPersonActivity.this,"查询用户失败："+msg, Toast.LENGTH_SHORT).show();

            }
        });
        img.setOnClickListener(ViewClick);
        content.setOnClickListener(ViewClick);

    }

//    String name ;         //商户的名字              用户的名字
//    String score;         //用户的评分              发布时间
//    String location;      //商户的地点              微博来源
//    String distance;      //商户的距离              微博内容头像
//    String  photo;        //商户图片                用户头像
//    String  content;      //团购内容                发布微博的内容
//    String  salebefore;   //打折前的价格            转发数
//    String  salelater;    //折后价                  评论数
//    String  salenum;      //销量                    点赞数


    private void searchPost(){
        ID = new ArrayList<>();
      final   MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> object) {
                // TODO Auto-generated method stub

                if(object.size() != 0){
                    for(int i=0;i<object.size();i++){
                        String  username  = user.getPersonname();
                        String  userscore = object.get(i).getCreatedAt();
                        if(user.getIcon() != null){
                              storephoto= "http://file.bmob.cn/"+user.getIcon().getUrl();;
                        }else{
                              storephoto = "http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png";
                        }
                        String  storecontent= object.get(i).getContent();;
                        String  forwarding="转发";
                        String  comments="评论";
                        String  praice="点赞"+i;
                        if(object.get(i).getImage() != null){
                              contentphoto = "http://file.bmob.cn/"+object.get(i).getImage().getUrl();
                        }else{
                              contentphoto = null;
                        }

                        JuUniversalData data = new JuUniversalData(username,userscore,null,contentphoto,storephoto,storecontent,forwarding,comments,praice);
                        listdata.add(data);
                        data = null;

                        String  Id =  object.get(i).getObjectId();
                        ID.add(Id);
                        Id = null;
                    }
                }

                JuFriendsAdapter adapter = new JuFriendsAdapter(JuPersonActivity.this,listdata);
                list.setAdapter(adapter);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(JuPersonActivity.this, "查询失败:"+msg, Toast.LENGTH_SHORT).show();
            }
        });


    }


    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener ViewClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ju_person_content:
                     intent = new Intent(JuPersonActivity.this,JuPersonModifyActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_person_bigphoto:
                     intent = new Intent(JuPersonActivity.this,JuUploadImgActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    AdapterView.OnItemClickListener Click = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(JuPersonActivity.this, JuCommentActivity.class);
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
                intent = new Intent(JuPersonActivity.this,JuPersonModifyActivity.class);
                startActivity(intent);
            }

            if(position >= 1){
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

            if(j>=1){
                texts = (TextView) view.findViewById(R.id.ju_comments);
                texts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(JuPersonActivity.this,JuCommentActivity.class);
                        intent.putExtra("JuId",ID.get(j-1).toString() );
                        intent.putExtra("JuConImg", listdata.get(j-1).getDistance());
                        intent.putExtra("JuUseImg", listdata.get(j-1).getPhoto());
                        intent.putExtra("JuConTex", listdata.get(j-1).getContent());
                        intent.putExtra("JuUseNam", listdata.get(j-1).getName());
                        intent.putExtra("JuCrtTim", listdata.get(j-1).getScore());
                        intent.putExtra("JuForNum", listdata.get(j-1).getSalebefore());
                        intent.putExtra("JuConNum", listdata.get(j-1).getSalelater());
                        intent.putExtra("JuPraNum", listdata.get(j-1).getSalenum());
                        startActivity(intent);
                    }
                });

                zhuanfa = (TextView) view.findViewById(R.id.ju_forwarding);
                zhuanfa.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        MyUser user = BmobUser.getCurrentUser(JuPersonActivity.this,MyUser.class);
                        Post post = new Post();

                        post.setContent("转发：  "+listdata.get(j-1).getContent());
                        Log.i("result","zzz----------"+listdata.get(j-1).getContent());
                        post.setAuthor(user);
                        post.save(JuPersonActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                Toast.makeText(JuPersonActivity.this, "转发成功", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                            }
                        });
                    }
                });
            }




        }
    };

}
