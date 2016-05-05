package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.jhy.myspaceshopping.myspaceshopping.object.Post;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/4/14.
 *   String name ;         //商户的名字              用户的名字
 String score;         //用户的评分              发布时间
 String location;      //商户的地点              微博来源
 String distance;      //商户的距离              Null
 String  photo;        //商户图片                用户头像
 String  content;      //团购内容                发布微博的内容
 String  salebefore;   //打折前的价格            转发数
 String  salelater;    //折后价                  评论数
 String  salenum;      //销量                    点赞数
 */
public class JuFriendsAdapter extends BaseAdapter {

    List<JuUniversalData> list;
    LayoutInflater inflater;
    Context context;
    TextView textss;
    String datas;
    //获取系统时间
    SimpleDateFormat    sDateFormat    =   new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
    String    date    =    sDateFormat.format(new    java.util.Date());

    public JuFriendsAdapter(Context context, List<JuUniversalData> listdata){
        this.list = listdata;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final   MyUser user = BmobUser.getCurrentUser(context, MyUser.class);
        ViewHolderFriends holder;
        if(convertView ==null){
            holder = new ViewHolderFriends();
            convertView = inflater.inflate(R.layout.item_ju_friends,null);
            holder.names = (TextView) convertView.findViewById(R.id.ju_username);
            holder.score = (TextView) convertView.findViewById(R.id.ju_usertime);
            holder.storephoto = (ImageView) convertView.findViewById(R.id.ju_userphoto);
            holder.storecontent = (TextView) convertView.findViewById(R.id.ju_usercontents);
            holder.salebefore = (TextView) convertView.findViewById(R.id.ju_forwarding);
            holder.salenum = (TextView) convertView.findViewById(R.id.ju_clickpraise);

            textss =   holder.salenum;
            holder.contentphoto = (ImageView) convertView.findViewById(R.id.ju_contentphoto);
            convertView.setTag(holder);
        }

        holder = (ViewHolderFriends) convertView.getTag();

        final  JuUniversalData data = list.get(position);




        String []str = data.getScore().split(" ");
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
//        Log.i("life","system time------"+date);
        String ss1=sstr[0];
        String ss2=sstr[1];
        Log.i("","");
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
        int ssec = Integer.parseInt(shours3);

        if(years == syear){
          if(month == smonth){
              if(day == sday ){
                   if(hour == shour){
                           int mins = smin - min;
                       if(mins <=0 ){
                           int secs = ssec - sec;
                           holder.score.setText( secs+"秒前");
                       }else{
                           holder.score.setText( mins+"分钟前");
                       }
                   }else if((hour - shour<1)){
                       int mins =  (60-min)+(60-smin);
                       holder.score.setText( mins+"分钟前");

                   }else{
                       int hous = (12-shour)+(12-hour);
                       holder.score.setText(hous+"小时前");
                   }
              }else{
                  int days = sday - day;
                  if(days == 1){
                      holder.score.setText("昨天"+hour+" "+":"+min);
                  }else if(days ==2 ){
                      holder.score.setText("前天"+hour+" "+":"+min);
                  }else{
                      holder.score.setText(days+"天前"+" "+hour+":"+min);
                  }

              }
          }else{
              holder.score.setText(month+"月"+day+"日"+" "+hour+":"+min);
          }
        }else{
            holder.score.setText(data.getScore());
        }



        holder.names.setText(data.getName());
        Picasso.with(context).load(data.getPhoto()).into( holder.storephoto);
        holder.storecontent.setText(data.getContent());
        holder.salebefore.setText(data.getSalebefore());
        Picasso.with(context).load(data.getDistance()).into( holder.contentphoto);
        datas = data.getSalelater();


        holder.salenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
//                Log.i("life","------------------=2"+data.getSalelater());
                post.setObjectId(data.getSalelater());
                //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                BmobRelation relation = new BmobRelation();
                //将当前用户添加到多对多关联中
                relation.add(user);
                //多对多关联指向`post`的`likes`字段
                post.setLikes(relation);
                post.update(context, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        Log.i("life","多对多关联添加成功S");
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Log.i("life","多对多关联添加失败");
                    }
                });
            }
        });

        return convertView;
    }
}

class ViewHolderFriends{
//    String name ; //商户的名字
//    String score;    //用户的评分
//    String location; //商户的地点
//    String distance; //商户的距离
//    String  storephoto;   //商户图片
//    String  storecontent; //团购内容
//    String  salebefore;   //打折前的价格
//    String  salelater;    //折后价
//    String  salenum;      //销量

    TextView names;
    TextView score;
    ImageView storephoto;
    ImageView contentphoto;
    TextView storecontent;
    TextView salenum;
    TextView salebefore;
}