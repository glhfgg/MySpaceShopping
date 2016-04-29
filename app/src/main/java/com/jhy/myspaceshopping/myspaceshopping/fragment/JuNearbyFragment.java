package com.jhy.myspaceshopping.myspaceshopping.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuNearAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2016/4/14.
 * String name ;         //商户的名字              用户的名字               附近人的名字
 String score;         //用户的评分              发布时间                 时间
 String location;      //商户的地点              微博来源                 Null
 String distance;      //商户的距离              Null                     附近人的距离
 String  photo;        //商户图片                用户头像                 附近人的头像
 String  content;      //团购内容                发布微博的内容           附近人的个人信息
 String  salebefore;   //打折前的价格            转发数                   Null
 String  salelater;    //折后价                  评论数                   Null
 String  salenum;      //销量                    点赞数                   Null
 */
public class JuNearbyFragment extends Fragment {

    List<JuUniversalData> listdata;
    PullToRefreshListView list;
    String img;
    List<String> User;
     int num = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ju_list,container,false);

        list = (PullToRefreshListView) v.findViewById(R.id.listJu);
        listdata = new ArrayList<>();
        list.setMode(PullToRefreshBase.Mode.BOTH);
        searchUser(num);
        list.setOnItemClickListener(itemClick);

        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                listdata = new ArrayList<>();
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                listdata = new ArrayList<>();
                      new GetDataTask().execute();
            }
        });

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
            searchUser(num);
            //Log.i("gl", "加了" + shopDataPage + shopResult);
            list.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private void searchUser(int num){

        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
       //查询playerName叫“比目”的数据
        query.addWhereEqualTo("friend", true);
       //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(num);
        //执行查询方法
        query.findObjects(this.getActivity(), new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub

                Toast.makeText(JuNearbyFragment.this.getActivity(), "查询成功：共"+object.size()+"条数据。", Toast.LENGTH_SHORT).show();
                for (int i =0;i<object.size();i++) {
                    //获得Name的信息
               String name =  object.get(i).getPersonname();
                    //获得用户自我介绍
               String  storecontent =  object.get(i).getContent();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
               String score = object.get(i).getCreatedAt();
                    User  = new ArrayList<String>();
                    //获取用户头像
                    if(object.get(i).getIcon() != null){
                         img ="http://file.bmob.cn/"+ object.get(i).getIcon().getUrl();
                    }else{
                         img = "http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png";
                    }

                    String us =  object.get(i).getObjectId();
                    JuUniversalData data = new JuUniversalData(name,us,null,"500m",img,storecontent,null,null,null);
                    Log.i("result","++++____"+img);
                    listdata.add(data);
                    data = null;



                }

                JuNearAdapter adapter = new JuNearAdapter(JuNearbyFragment.this.getActivity(),listdata);
                list.setAdapter(adapter);
                list.setOnItemClickListener(itemClick);
                list.onRefreshComplete();
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

                Toast.makeText(JuNearbyFragment.this.getActivity(),"查询失败："+msg, Toast.LENGTH_SHORT).show();
            }
        });

    }


    OnItemClickListener itemClick = new OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent = new Intent(getActivity(), JuPersonActivity.class);
//            startActivity(intent);


        }
    };

}
