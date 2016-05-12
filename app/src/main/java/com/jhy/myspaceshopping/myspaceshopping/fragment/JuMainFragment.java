package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.activity.JuCreatePostActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.JuMyFansActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.JuMyLikesActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.JuMyShareActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.JuPersonModifyActivity;
import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.activity.JuNewsActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.JuPersonActivity;
import com.jhy.myspaceshopping.myspaceshopping.activity.MainActivity;
import com.jhy.myspaceshopping.myspaceshopping.adapter.PagerAdapter;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/4/14.
 */

public class JuMainFragment extends Fragment {

    ImageView img;          //fragment_ju_main 的 用户头像
    ImageView news;         //fragment_ju_main 的 新消息
    ImageView more;         //fragment_ju_main 的 更多
    TextView name;

    View v1;
    PopupWindow  pop;
    RadioGroup radioGroup;  //fragment_ju_main 的RadioGrouo
    ViewPager viewPager;    // fragment_ju_main 的ViewPager
    LayoutInflater inflater1;

    //Pop
    TextView modify;  //资料修改
    TextView share;   //我的分享
    TextView fans;    //我的粉丝
    TextView likes;   //我的关注
    TextView post;

    String s ;   // Bmob 上传图片的URL
    String t;
    MyUser  user;
    //ViewPager 的页面位置常量
    private static final int PERIPHERY_PAGE =0;
    private static final int FRIENDS_PAGE =1;
    private static final int NEAR_PAGE =2;

    ArrayList<Fragment> listFragmentsa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user = BmobUser.getCurrentUser(this.getActivity(), MyUser.class);
        if (user == null) {
            getActivity().finish();
            Intent intent = new Intent(JuMainFragment.this.getActivity(), MainActivity.class);
            startActivity(intent);
        }

        inflater1 = inflater;
        View v = inflater.inflate(R.layout.fragment_ju_main, container, false);
        v1 = v;
        Log.i("stest","sssss---------=onCreateView");
        //初始化ViewPager控件 并且设置点击事件
        img = (ImageView) v.findViewById(R.id.ju_main_userphoto);
        news = (ImageView) v.findViewById(R.id.ju_main_news);
        more = (ImageView) v.findViewById(R.id.ju_main_more);
        radioGroup = (RadioGroup) v.findViewById(R.id.ju_main_radio);
        viewPager = (ViewPager) v.findViewById(R.id.ju_viewpager);
        name = (TextView) v.findViewById(R.id.ju_main_username);


        if(user == null){
        }else{  name.setText(user.getPersonname());}

        searchImg();
        setPopuWindow();
        setViewPager();//设置 ViewPager的Fragmnet嵌套到 MainFragmnet中
        setClick(); //绑定监听事件
            return v;
        }

    @Override
    public void onResume() {
        super.onResume();
        MyUser  user = BmobUser.getCurrentUser(this.getActivity(), MyUser.class);
        if (user == null) {
            getActivity().finish();
            Intent intent = new Intent(JuMainFragment.this.getActivity(), MainActivity.class);
            startActivity(intent);
        }
        name.setText(user.getPersonname());
        Log.i("stest","sssss---------=onResume");
        if(user.getIcon() != null){
            if(user.getLoginQQ()){
                Picasso.with(this.getActivity()).load(user.getIcon().getUrl()).into(img);
            }else{
                Picasso.with(this.getActivity()).load("http://file.bmob.cn/"+user.getIcon().getUrl().toString()).into(img);
            }
        }
    }


    //绑定监听事件
    private void setClick(){
        img.setOnClickListener(click);
        radioGroup.setOnCheckedChangeListener(changeListener);
        news.setOnClickListener(newsClick);
        more.setOnClickListener(moreClick);
        name.setOnClickListener(click);
    }

    //设置 ViewPager的Fragmnet嵌套到 MainFragmnet中
    private void setViewPager(){

        listFragmentsa = new ArrayList<>();
        listFragmentsa.add(new JuPeripheryFragment());
        listFragmentsa.add(new JuFriendsFragment());
        listFragmentsa.add(new JuNearbyFragment());
        FragmentManager manager = getActivity().getSupportFragmentManager();

        PagerAdapter adapter = new PagerAdapter(manager,listFragmentsa);

        viewPager.setAdapter(adapter);
        viewPager .setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(pagerchange);
        
    }

     //ViewPager的Change事件
    ViewPager.OnPageChangeListener pagerchange = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(position);
            rb.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

    };

    //创建PopuWindow
    private void setPopuWindow(){
        Drawable drawable = getResources().getDrawable(R.drawable.common_textfield_background);
        LayoutInflater inflater =LayoutInflater.from(this.getActivity());
        View popu = inflater.inflate(R.layout.item_ju_main_popu,null);
        modify = (TextView) popu.findViewById(R.id.ju_more_changedata);
        share = (TextView) popu.findViewById(R.id.ju_more_myshare);   //我的分享
        fans = (TextView) popu.findViewById(R.id.ju_more_myfans);    //我的粉丝
        likes = (TextView) popu.findViewById(R.id.ju_more_myfocus);   //我的关注
        post = (TextView) popu.findViewById(R.id.ju_more_createpost);

        post.setOnClickListener(modifyClick);
        modify.setOnClickListener(modifyClick);
        share.setOnClickListener(modifyClick);
        fans.setOnClickListener(modifyClick);
        likes.setOnClickListener(modifyClick);

        pop = new PopupWindow(popu,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setTouchable(true);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(drawable);
    }

    //fragment_ju_main 的RadioGrouo 的点击事件
    RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.ju_main_periphery:
                    viewPager.setCurrentItem(PERIPHERY_PAGE);
                    Log.i("ftest","-----PERIPHERY_PAGE");
                    break;
                case R.id.ju_main_friends:
                    viewPager.setCurrentItem(FRIENDS_PAGE);
                    Log.i("ftest","-----FRIENDS_PAGE");
                    break;
                case R.id.ju_main_near:
                    viewPager.setCurrentItem(NEAR_PAGE);
                    Log.i("ftest","-----NEAR_PAGE");
                    break;
            }
        }
    };

    //fragment_ju_main 的用户头像 的点击事件
    OnClickListener click = new OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
              case   R.id.ju_main_userphoto:
                  Intent intent = new Intent(getActivity(),JuPersonActivity.class);
                  startActivity(intent);
                break;

                case   R.id.ju_main_username:
                    intent = new Intent(getActivity(),JuCreatePostActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    //新消息 的点击事件
    OnClickListener newsClick = new OnClickListener(){
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(getActivity(), JuNewsActivity.class);
            startActivity(intent);
        }
    };


    //更多 的点击事件
    OnClickListener moreClick = new OnClickListener(){
        @Override
        public void onClick(View v) {
            pop.showAsDropDown(more);
        }
    };

    OnClickListener modifyClick = new OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.ju_more_changedata:
                     intent = new Intent(getActivity(), JuPersonModifyActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_more_myfans:
                     intent = new Intent(getActivity(), JuMyFansActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_more_myfocus:
                     intent = new Intent(getActivity(), JuMyLikesActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_more_myshare:
                     intent = new Intent(getActivity(), JuMyShareActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ju_more_createpost:
                    intent = new Intent(getActivity(),JuCreatePostActivity.class);
                    startActivity(intent);
            }
        }
    };

    //搜素查询当前用户登陆头像
   private void searchImg(){
       BmobQuery<MyUser> query = new BmobQuery<MyUser>();
       query.addWhereEqualTo("username", user.getUsername());
       query.findObjects(this.getActivity(), new FindListener<MyUser>() {
           @Override
           public void onSuccess(List<MyUser> list) {
                   if(list.get(0).getIcon() != null){
                       MyUser user = BmobUser.getCurrentUser(getContext(),MyUser.class);
                       if(user.getLoginQQ()){
                           Picasso.with(JuMainFragment.this.getActivity()).load(list.get(0).getIcon().getUrl()).into(img);
                       }else{
                           Picasso.with(JuMainFragment.this.getActivity()).load("http://file.bmob.cn/"+ list.get(0).getIcon().getUrl().toString()).into(img);
                       }

                   }else{
                       t = "http://file.bmob.cn/M03/46/56/oYYBAFcfIiGAIh3gAAAEw_gSloU510.png";

                       Picasso.with(JuMainFragment.this.getActivity()).load(t).into(img);

                   }
           }

           @Override
           public void onError(int i, String s) {

               Toast.makeText(JuMainFragment.this.getActivity(), "头像加载失败"+s, Toast.LENGTH_SHORT).show();

           }
       });

   }


}


