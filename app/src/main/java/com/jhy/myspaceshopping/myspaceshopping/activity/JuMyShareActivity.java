package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuFriendsAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.jhy.myspaceshopping.myspaceshopping.object.Post;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 俊峰 on 2016/5/1.
 */
public class JuMyShareActivity extends Activity{
    ImageView back;
    ListView list;
    List<JuUniversalData> listdata = new ArrayList<>();
    String  contentphoto;
    String  storephoto;
    Intent intent;
    List<String> ID;
    TextView texts;
    TextView zhuanfa;

    int j;
    int num = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_popitem);
        //初始化控件 添加listView 的 Header
        init();
        back.setOnClickListener(click);
        list.setOnItemClickListener(Click);
        searchPost(5);
    }

    private void init(){
        list = (ListView) findViewById(R.id.ju_share_list);
        back = (ImageView) findViewById(R.id.ju_share_back);
    }

    private void searchPost(int num){
        listdata = new ArrayList<>();
        ID = new ArrayList<>();
        final   MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        query.setLimit(num);
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> object) {
                // TODO Auto-generated method stub

                if(object.size() != 0){
                    for(int i=0;i<object.size();i++){
                        String  username  = object.get(i).getAuthor().getPersonname();
                        String  userscore = object.get(i).getCreatedAt();
                        if(object.get(i).getAuthor().getIcon() != null){
                            storephoto= "http://file.bmob.cn/"+object.get(i).getAuthor().getIcon().getUrl();;
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
                JuFriendsAdapter adapter = new JuFriendsAdapter(JuMyShareActivity.this,listdata);
                list.setAdapter(adapter);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(JuMyShareActivity.this, "查询失败:"+msg, Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(JuMyShareActivity.this, JuCommentActivity.class);
            j = position;

            intent.putExtra("JuConImg", listdata.get(position).getDistance());
            intent.putExtra("JuUseImg", listdata.get(position).getPhoto());
            intent.putExtra("JuConTex", listdata.get(position).getContent());
            intent.putExtra("JuUseNam", listdata.get(position).getName());
            intent.putExtra("JuCrtTim", listdata.get(position).getScore());
            intent.putExtra("JuForNum", listdata.get(position).getSalebefore());
            intent.putExtra("JuConNum", listdata.get(position).getSalelater());
            intent.putExtra("JuPraNum", listdata.get(position).getSalenum());
            intent.putExtra("JuId", ID.get(position).toString());
            Log.i("result","ID-----------"+ ID.get(position).toString());
            startActivity(intent);

            texts = (TextView) view.findViewById(R.id.ju_comments);
            texts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JuMyShareActivity.this,JuCommentActivity.class);
                    intent.putExtra("JuId",ID.get(j).toString() );
                    intent.putExtra("JuConImg", listdata.get(j).getDistance());
                    intent.putExtra("JuUseImg", listdata.get(j).getPhoto());
                    intent.putExtra("JuConTex", listdata.get(j).getContent());
                    intent.putExtra("JuUseNam", listdata.get(j).getName());
                    intent.putExtra("JuCrtTim", listdata.get(j).getScore());
                    intent.putExtra("JuForNum", listdata.get(j).getSalebefore());
                    intent.putExtra("JuConNum", listdata.get(j).getSalelater());
                    intent.putExtra("JuPraNum", listdata.get(j).getSalenum());
                    startActivity(intent);
                }
            });

            zhuanfa = (TextView) view.findViewById(R.id.ju_forwarding);
            zhuanfa.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    MyUser user = BmobUser.getCurrentUser(JuMyShareActivity.this,MyUser.class);
                    Post post = new Post();

                    post.setContent("转发：  "+listdata.get(j).getContent());
                    Log.i("result","zzz----------"+listdata.get(j).getContent());
                    post.setAuthor(user);
                    post.save(JuMyShareActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            Toast.makeText(JuMyShareActivity.this, "转发成功", Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
            });
        }
    };
}
