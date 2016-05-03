package com.jhy.myspaceshopping.myspaceshopping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.adapter.JuNearAdapter;
import com.jhy.myspaceshopping.myspaceshopping.fragment.JuNearbyFragment;
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
public class JuMyFansActivity extends Activity{

    TextView title;
    ListView list;
    ImageView back;

    int m=0;
    int s= 0;
    String img;
    int j;

    List<MyUser> object2;
    List<JuUniversalData> listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_popitem);
        title = (TextView) findViewById(R.id.ju_share_title);
        list = (ListView) findViewById(R.id.ju_share_list);
        back = (ImageView) findViewById(R.id.ju_share_back);
        back.setOnClickListener(click);
        title.setText("我的粉丝");
        searchAttMy();
    }

    private void searchAttMy(){
        listdata = new ArrayList<>();
        final MyUser user = BmobUser.getCurrentUser(this,MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("friend", true);
        query.findObjects(this, new FindListener<MyUser>() {

            @Override
            public void onSuccess(List<MyUser> object) {
                // TODO Auto-generated method stub
                Log.i("life", "查询个数："+object.size());
                object2 = object;
                for( j=0;j<object.size();j++){

                    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                    MyUser post = new MyUser();
                    post.setObjectId( object.get(j).getObjectId());
                    query.addWhereRelatedTo("likes", new BmobPointer(post));
                    query.findObjects(JuMyFansActivity.this, new FindListener<MyUser>() {

                        @Override
                        public void onSuccess(List<MyUser> objects) {
                            // TODO Auto-generated method stub

                            for(int i =0;i<objects.size();i++){

                                if(objects.get(i).getUsername().toString().equals(user.getUsername().toString())){
                                    s++;
                                    m=1;
                                    String name =  object2.get(j).getPersonname();
                                    String  storecontent =  object2.get(j).getContent();
                                    if(object2.get(j).getIcon() != null){
                                        img ="http://file.bmob.cn/"+ object2.get(j).getIcon().getUrl();
                                    }else{
                                        img = "http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png";
                                    }
                                    String us =  object2.get(j).getObjectId();

                                    JuUniversalData data = new JuUniversalData(name,us,null,"500m",img,storecontent,null,null,null);
                                    listdata.add(data);
                                    data =null;
                                }
                                JuNearAdapter adpater = new JuNearAdapter(JuMyFansActivity.this,listdata);
                                list.setAdapter(adpater);
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
            finish();
        }
    };


}
