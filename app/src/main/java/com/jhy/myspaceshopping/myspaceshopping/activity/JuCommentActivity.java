package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuCommentAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.Comment;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.jhy.myspaceshopping.myspaceshopping.object.Post;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 俊峰 on 2016/4/17.
 */

public class JuCommentActivity extends Activity {

    ListView list;
    List<JuUniversalData> listdata;
    LinearLayout linear;
    View v;

    ImageView contentphoto;
    ImageView userphoto;
    TextView forwarding;     //转发
    TextView comment;        //评论
    TextView paris;         //赞
    EditText huifu;
    TextView enter;


    TextView username;
    TextView createtime;
    TextView content;

    EditText text;

    AlertDialog.Builder dialog;

    String texts;
    String img;
    String  i ;
    String num;  //评论数

    //获取系统时间
    SimpleDateFormat sDateFormat    =   new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
    String    date    =    sDateFormat.format(new    java.util.Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_comment);
        setList();
        setParisNum();
    }

    private void setIntentData(){

        Intent  intent = getIntent();
        if( intent.getExtras().getString("JuUseImg") != null){
            Picasso.with(this).load( intent.getExtras().getString("JuUseImg").toString()).into(userphoto);
        }else{
            Picasso.with(this).load("http://www.qqzhi.com/uploadpic/2014-10-08/051328157.jpg").into(userphoto);
        }
        if(intent.getExtras().getString("JuConImg") != null){
            Picasso.with(this).load( intent.getExtras().getString("JuConImg").toString()).into(contentphoto);
        }

        username.setText(intent.getExtras().getString("JuUseNam").toString());

        String []str =intent.getExtras().getString("JuCrtTim").toString().split(" ");
//        Log.i("life","my time------"+data.getScore());
        String s1=str[0];
        String s2=str[1];

        String [] year = s1.split("-");
        String year1 = year[0];
        String year2 = year[1];
        String year3 = year[2];

        String [] hours = s2.split(":");
        String hours1 = hours[0];
        String hours2 = hours[1];
        String hours3 = hours[2];

        int years = Integer.parseInt(year1);
        int month = Integer.parseInt(year2);
        int day = Integer.parseInt(year3);
        int hour = Integer.parseInt(hours1);
        int min = Integer.parseInt(hours2);
        int sec = Integer.parseInt(hours3);

        String [] sstr = date.split("    ");
        Log.i("life","system time------"+date);
        String ss1=sstr[0];
        String ss2=sstr[1];
        //
        String [] syears = ss1.split("-");
        String syear1 = syears[0];
        String syear2 = syears[1];
        String syear3 = syears[2];
        //
        String [] shours = ss2.split(":");
        String shours1 = shours[0];
        String shours2 = shours[1];
        String shours3 = shours[2];

        int syear = Integer.parseInt(syear1);
        int smonth = Integer.parseInt(syear2);
        int sday = Integer.parseInt(syear3);
        int shour = Integer.parseInt(shours1);
        int smin = Integer.parseInt(shours2);
        int ssec = Integer.parseInt(shours3);  if(years == syear){
            if(month == smonth){
                if(day == sday ){
                    if(hour == shour+12){
                        if(smin == min){
                            createtime.setText( "1分钟内");

                        }else{
                            int mins = smin - min;
                            if(mins <0){
                                createtime.setText( "1分钟内");
                            }else{
                                createtime.setText( mins+"分钟前");
                            }
                        }

                    }else if((hour - shour+12)==1){
                        int mins =  (60-min)+smin;
                        createtime.setText( mins+"分钟前");

                    }else{

                        int hous =12+shour-hour;
                        createtime.setText(hous+"小时前");
                    }
                }else{
                    int days = sday - day;
                    if(days == 1){
                        createtime.setText("昨天"+hour+" "+":"+min);
                    }else if(days ==2 ){
                        createtime.setText("前天"+hour+" "+":"+min);
                    }else{
                        createtime.setText(days+"天前"+" "+hour+":"+min);
                    }

                }
            }else{
                createtime.setText(month+"月"+day+"日"+" "+hour+":"+min);
            }
        }else{
            createtime.setText(intent.getExtras().getString("JuCrtTim").toString());
        }

        content.setText(intent.getExtras().getString("JuConTex").toString());
        i = intent.getExtras().getString("JuId").toString();
    }

    private  void setList(){
        list = (ListView) findViewById(R.id.ju_comment_list);
        forwarding = (TextView)findViewById(R.id.ju_forwarding2);
        comment = (TextView)findViewById(R.id.ju_comments2);
        paris = (TextView)findViewById(R.id.ju_clickpraise2);
        huifu = (EditText) findViewById(R.id.ju_comment_huifu);
        enter = (TextView) findViewById(R.id.ju_comment_enter);


        LayoutInflater inflater = LayoutInflater.from(this);
        v = inflater.inflate(R.layout.item_ju_friends,null);
        content = (TextView) v.findViewById(R.id.ju_usercontents);
        userphoto = (ImageView) v.findViewById(R.id.ju_userphoto);
        linear = (LinearLayout) v.findViewById(R.id.ju_friends_linear);
        linear.setVisibility(View.GONE);
        username = (TextView) v.findViewById(R.id.ju_username);
        createtime = (TextView) v.findViewById(R.id.ju_usertime);
        contentphoto = (ImageView) v.findViewById(R.id.ju_contentphoto);

        setIntentData();
        setData();

        list.addHeaderView(v);
        forwarding.setOnClickListener(Click);
        comment.setOnClickListener(Click);
        paris.setOnClickListener(Click);


    }

//    //Perphery                               Near                    Comment
//    String name ;         //商户的名字              用户的名字               附近人的名字             用户的名字
//    String score;         //用户的评分              发布时间                 时间                    发布时间
//    String location;      //商户的地点              微博来源                 Null                    Null
//    String distance;      //商户的距离              Null                     附近人的距离            Null
//    String  photo;        //商户图片                用户头像                 附近人的头像             用户头像
//    String  content;      //团购内容                发布微博的内容           附近人的个人信息         评论内容
//    String  salebefore;   //打折前的价格            转发数                   Null                    Null
//    String  salelater;    //折后价                  评论数                   Null                    Null
//    String  salenum;      //销量                    点赞数                   Null                    Null

   private  void setData() {
       listdata = new ArrayList<>();
       BmobQuery<Comment> query = new BmobQuery<Comment>();

       Post post = new Post();
       post.setObjectId(i);
       query.addWhereEqualTo("post", new BmobPointer(post));

       query.include("user,post.author");
       query.findObjects(this, new FindListener<Comment>() {

           @Override
           public void onSuccess(List<Comment> object) {
               // TODO Auto-generated method stub
               for (int i = 0; i < object.size(); i++) {
                   String name = object.get(i).getUser().getPersonname();
                   String time = object.get(i).getCreatedAt();
                   if (object.get(i).getUser().getIcon() != null) {
                       img = "http://file.bmob.cn/" + object.get(i).getUser().getIcon().getUrl();
                   } else {
                       img = "http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png";
                   }

                   String content = object.get(i).getContent();
                   JuUniversalData data = new JuUniversalData(name, time, null, null, img, content, null, null, null);
                   listdata.add(data);
                   data = null;
               }
               JuCommentAdapter adapter = new JuCommentAdapter(JuCommentActivity.this, listdata);
               list.setAdapter(adapter);
           }

           @Override
           public void onError(int code, String msg) {
               // TODO Auto-generated method stub

           }
       });
   }


    private void saveComment(){
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        Post post = new Post();
        post.setObjectId(i);
        final Comment comment = new Comment();
        comment.setContent(texts);
        comment.setPost(post);
        comment.setUser(user);
        comment.save(this, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.i("life","评论发表成功");
                setData();
                huifu.setVisibility(View.GONE);
                huifu.setText("");
                enter.setVisibility(View.GONE);
                Toast.makeText(JuCommentActivity.this, "回复Ok~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Log.i("life","评论失败");
            }
        });


    }


    //查询该帖子点赞数
    private void setParisNum(){
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        Post post = new Post();
        post.setObjectId(i);
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("likes", new BmobPointer(post));
        query.findObjects(this, new FindListener<MyUser>() {

            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub
                Log.i("life", "查询个数："+object.size());
                num = "点赞"+object.size();
                paris.setText(num);
                if(paris != null){
                    paris.setText(num);
                }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Log.i("life", "查询失败："+code+"-"+msg);
            }
        });
    }

    //设置用户点赞
    private void setParis(){
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        Post post = new Post();
        post.setObjectId(i);
        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(user);
        //多对多关联指向`post`的`likes`字段
        post.setLikes(relation);
        post.update(this, new UpdateListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.i("life","多对多关联添加成功");
            }
            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.i("life","多对多关联添加失败");
            }
        });
    }

    View.OnClickListener EnterClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            texts = huifu.getText().toString();
            saveComment();
        }
    };

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             switch (v.getId()){
                 case R.id.ju_forwarding2:
                     Intent  intent = getIntent();
                             MyUser user = BmobUser.getCurrentUser(JuCommentActivity.this,MyUser.class);
                             Post post = new Post();

                             post.setContent("转发："+intent.getExtras().getString("JuConTex").toString());
                     Log.i("life","IMG---------"+intent.getExtras().getString("JuConImg").toString());

                             BmobFile  bmobfile = new BmobFile( new File(intent.getExtras().getString("JuConImg").toString()));
                             post.setImage(bmobfile);
                             post.setAuthor(user);
                             post.save(JuCommentActivity.this, new SaveListener() {
                                 @Override
                                 public void onSuccess() {
                                     // TODO Auto-generated method stub
                                     Toast.makeText(JuCommentActivity.this, "转发成功", Toast.LENGTH_SHORT).show();

                                 }
                                 @Override
                                 public void onFailure(int code, String msg) {
                                     // TODO Auto-generated method stub
                                     Toast.makeText(JuCommentActivity.this, "失败"+msg, Toast.LENGTH_SHORT).show();
                                 }
                             });
                     break;
                 case R.id.ju_comments2:

                     if(enter.getVisibility()==View.VISIBLE){
                         enter.setVisibility(View.GONE);
                         huifu.setVisibility(View.GONE);
                     }else{
                         huifu.setVisibility(View.VISIBLE);
                         enter.setVisibility(View.VISIBLE);
                         enter.setOnClickListener(EnterClick);
                     }
                     break;
                 case R.id.ju_clickpraise2:
                     setParis();
                     setParisNum();
                     break;
             }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            if(enter.getVisibility()==View.VISIBLE){
                enter.setVisibility(View.GONE);
                huifu.setVisibility(View.GONE);
            }else{
                finish();
            }

            return true;
        } else if(keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截菜单键
        } else if(keyCode == KeyEvent.KEYCODE_HOME) {
            //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
        }

        return super.onKeyDown(keyCode, event);

    }
}
