package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
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

    TextView forwarding2;     //转发
    TextView comment2;        //评论
    TextView paris2;         //赞


    TextView username;
    TextView createtime;
    TextView content;

    EditText text;

    AlertDialog.Builder dialog;

    String texts;
    String img;
    String  i ;
    String num;  //评论数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_comment);
        linear = (LinearLayout) findViewById(R.id.ju_comment_linear);
        setList();
        setMyDialog();
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
        createtime.setText(intent.getExtras().getString("JuCrtTim").toString());
        content.setText(intent.getExtras().getString("JuConTex").toString());
        i = intent.getExtras().getString("JuId").toString();
    }

    private  void setList(){
        list = (ListView) findViewById(R.id.ju_comment_list);
        LayoutInflater inflater = LayoutInflater.from(this);
        v = inflater.inflate(R.layout.item_ju_friends,null);

        content = (TextView) v.findViewById(R.id.ju_usercontents);
        userphoto = (ImageView) v.findViewById(R.id.ju_userphoto);

        forwarding = (TextView) v.findViewById(R.id.ju_forwarding);
        comment = (TextView) v.findViewById(R.id.ju_comments);
        paris = (TextView) v.findViewById(R.id.ju_clickpraise);



        username = (TextView) v.findViewById(R.id.ju_username);
        createtime = (TextView) v.findViewById(R.id.ju_usertime);
        contentphoto = (ImageView) v.findViewById(R.id.ju_contentphoto);

        setIntentData();
        setData();

        list.addHeaderView(v);
        list.setOnScrollListener(Scrol);
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

    private  void setMyDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View viewdialog = inflater.inflate(R.layout.dialog_ju_modify,null);
        dialog = new AlertDialog.Builder(this);

        dialog.setTitle("请输入评论");
        dialog.setView(viewdialog);
        text = (EditText) viewdialog.findViewById(R.id.ju_dialog_message);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                texts = text.getText().toString();
                saveComment();;
                ((ViewGroup) viewdialog.getParent()).removeView(viewdialog);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((ViewGroup) viewdialog.getParent()).removeView(viewdialog);
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
                if(paris2 != null){
                    paris2.setText(num);
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


    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             switch (v.getId()){
                 case R.id.ju_forwarding:
                     Intent  intent = getIntent();
                             MyUser user = BmobUser.getCurrentUser(JuCommentActivity.this,MyUser.class);
                             Post post = new Post();

                             post.setContent("转发：  "+intent.getExtras().getString("JuConTex").toString());

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
                                 }
                             });

                     break;
                 case R.id.ju_comments:
                     dialog.create().show();
                     break;
                 case R.id.ju_clickpraise:
                     setParis();
                     setParisNum();
                     break;
             }

        }
    };

    View.OnClickListener Click2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ju_forwarding2:
                    Intent  intent = getIntent();
                    MyUser user = BmobUser.getCurrentUser(JuCommentActivity.this,MyUser.class);
                    Post post = new Post();

                    post.setContent("转发：  "+intent.getExtras().getString("JuConTex").toString());

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
                        }
                    });

                    break;
                case R.id.ju_comments2:
                    dialog.create().show();
                    break;
                case R.id.ju_clickpraise2:
                    setParis();
                    setParisNum();
                    break;
            }

        }
    };

    AbsListView.OnScrollListener Scrol = new AbsListView.OnScrollListener(){
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                // 当不滚动时
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    // 判断滚动到底部
                    if (list.getLastVisiblePosition() == (list.getCount() - 1)) {
                        Log.i("result","-------------------底部"+list.getCount());
                    }
                    // 判断滚动到顶部
                    if(list.getFirstVisiblePosition() > 0){
                        Log.i("result","-------------------顶部");
                        linear.setVisibility(View.VISIBLE);
                        forwarding2 = (TextView)findViewById(R.id.ju_forwarding2);
                        comment2 = (TextView)findViewById(R.id.ju_comments2);
                        paris2 = (TextView)findViewById(R.id.ju_clickpraise2);
                        forwarding2.setOnClickListener(Click2);
                        comment2.setOnClickListener(Click2);
                        paris2.setOnClickListener(Click2);
                        paris2.setText(num);




                    }else{
                        linear.setVisibility(View.GONE);
                    }
                    break;
            }
        }





        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };


}
