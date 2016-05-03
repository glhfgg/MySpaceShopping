package com.jhy.myspaceshopping.myspaceshopping.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.activity.JuCommentActivity;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuFriendsAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.jhy.myspaceshopping.myspaceshopping.object.Post;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/14.
 */

public class JuFriendsFragment extends Fragment {

    List<JuUniversalData> listdata;

    PullToRefreshListView list;
    String  contentphoto;
    String  storephoto;
    List<String> ID;
    int j;
    int num = 5;
    TextView texts ;
    TextView zhuanfa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ju_list,container,false);

        list = (PullToRefreshListView) v.findViewById(R.id.listJu);
        list.setMode(PullToRefreshBase.Mode.BOTH);
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

        @Override
         public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            listdata = new ArrayList<>();
            new GetDataTask ().execute();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            listdata = new ArrayList<>();
              new GetDataTask ().execute();

         }
    });

        searchPost(5);

        return v;
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Call onRefreshComplete when the list has been refreshed.
            num = num+5;
            searchPost(num);

//            Log.i("gl", "加了" + shopDataPage + shopResult);
            list.onRefreshComplete();
            super.onPostExecute(result);
        }
    }


    private void searchPost(int num){
        listdata = new ArrayList<>();
        BmobQuery<Post> query = new BmobQuery<Post>();
        ID = new ArrayList<>();
        query.order("-updatedAt");
        query.setLimit(num);
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(this.getActivity(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> object) {
                // TODO Auto-generated method stub

                if(object.size() != 0){
                    for(int i=0;i<object.size();i++){
                        String  username  =  object.get(i).getAuthor().getPersonname();
                        String  userscore = object.get(i).getCreatedAt();

                        if(object.get(i).getAuthor().getIcon() != null){
                            storephoto= "http://file.bmob.cn/"+ object.get(i).getAuthor().getIcon().getUrl();
                        }else{
                            storephoto = "http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png";
                        }

                        String  storecontent= object.get(i).getContent();
                        String  praice="点赞"+i;

                        if(object.get(i).getImage() != null){
                            contentphoto = "http://file.bmob.cn/"+object.get(i).getImage().getUrl();
                        }else{
                            contentphoto = null;
                        }

                        String  Id =  object.get(i).getObjectId().toString();
                        ID.add(Id);
                        Log.i("result","IDDD-----------"+Id);
                        JuUniversalData data = new JuUniversalData(username,userscore,null,contentphoto,storephoto,storecontent,null,Id,praice);
                        listdata.add(data);
                        data = null;

                    }

                    JuFriendsAdapter adapter = new JuFriendsAdapter(JuFriendsFragment.this.getActivity(),listdata);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(itmeClick);
                    list.onRefreshComplete();

                }
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

                Toast.makeText(JuFriendsFragment.this.getActivity(),"查询失败:"+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    AdapterView.OnItemClickListener itmeClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), JuCommentActivity.class);
                           j = position;
//            listdata.get(position).getDistance();    //内容图片
//            listdata.get(position).getPhoto();       //用户头像
//            listdata.get(position).getContent();     //发布内容
//            listdata.get(position).getName();        //用户名
//            listdata.get(position).getScore();       //发布日期
//            listdata.get(position).getSalebefore();  //转发
//            listdata.get(position).getSalelater();   //评论
//            listdata.get(position).getSalenum();     // 点赞
            intent.putExtra("JuConImg", listdata.get(position-1).getDistance());
            intent.putExtra("JuUseImg", listdata.get(position-1).getPhoto());
            intent.putExtra("JuConTex", listdata.get(position-1).getContent());
            intent.putExtra("JuUseNam", listdata.get(position-1).getName());
            intent.putExtra("JuCrtTim", listdata.get(position-1).getScore());
            intent.putExtra("JuForNum", listdata.get(position-1).getSalebefore());
            intent.putExtra("JuConNum", listdata.get(position-1).getSalelater());
            intent.putExtra("JuPraNum", listdata.get(position-1).getSalenum());
            intent.putExtra("JuId", ID.get(position-1).toString());
            startActivity(intent);


            texts = (TextView) view.findViewById(R.id.ju_comments);
            texts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),JuCommentActivity.class);
                    intent.putExtra("JuId",ID.get(j).toString() );
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
                    MyUser user = BmobUser.getCurrentUser(getContext(),MyUser.class);
                    Post post = new Post();

                    post.setContent("转发：  "+listdata.get(j-1).getContent());
                    Log.i("result","zzz----------"+listdata.get(j-1).getContent());
                    post.setAuthor(user);
                    post.save(getContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            Toast.makeText(JuFriendsFragment.this.getContext(), "转发成功", Toast.LENGTH_SHORT).show();
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