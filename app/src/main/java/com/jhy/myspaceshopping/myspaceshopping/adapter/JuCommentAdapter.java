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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 俊峰 on 2016/4/17.
 */
public class JuCommentAdapter extends BaseAdapter{

    LayoutInflater inflater;
    List<JuUniversalData> list;
    Context context;
    SimpleDateFormat sDateFormat    =   new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
    String    date    =    sDateFormat.format(new    java.util.Date());

   public JuCommentAdapter(Context context, List<JuUniversalData> list){
       this.list = list;
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
        ViewHolderComment holder;
        if(convertView == null){
            holder = new ViewHolderComment();
            convertView = inflater.inflate(R.layout.item_ju_comment_list,null);
            holder.name = (TextView) convertView.findViewById(R.id.ju_comment_item_name);
            holder.time = (TextView) convertView.findViewById(R.id.ju_comment_item_time);
            holder.content = (TextView) convertView.findViewById(R.id.ju_comment_item_content);
            holder.photo = (ImageView) convertView.findViewById(R.id.ju_comment_item_photo);
            convertView.setTag(holder);
        }

        holder = (ViewHolderComment) convertView.getTag();
        JuUniversalData data = list.get(position);
        holder.name.setText(data.getName());

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
                    if(hour == shour+12){
                        if(smin == min){
                            holder.time.setText( "1分钟内");

                        }else{
                            int mins = smin - min;
                            if(mins <0){
                                holder.time.setText( "1分钟内");
                            }else{
                                holder.time.setText( mins+"分钟前");
                            }
                        }
                    }else if((hour - shour+12)==1){
                        int mins =  (60-min)+smin;
                        holder.time.setText( mins+"分钟前");

                    }else{

                        int hous =12+shour-hour;
                        holder.time.setText(hous+"小时前");
                    }
                }else{
                    int days = sday - day;
                    if(days == 1){
                        holder.time.setText("昨天"+hour+" "+":"+min);
                    }else if(days ==2 ){
                        holder.time.setText("前天"+hour+" "+":"+min);
                    }else{
                        holder.time.setText(days+"天前"+" "+hour+":"+min);
                    }

                }
            }else{
                holder.time.setText(month+"月"+day+"日"+" "+hour+":"+min);
            }
        }else{
            holder.time.setText(data.getScore());
        }


        holder.content.setText(data.getContent());
        Picasso.with(context).load(data.getPhoto()).into( holder.photo);

        return convertView;
    }
}
    //Perphery                                 Near                    Comment
//    String name ;         //商户的名字              用户的名字               附近人的名字             用户的名字
//    String score;         //用户的评分              发布时间                 时间                    发布时间
//    String location;      //商户的地点              微博来源                 Null                    Null
//    String distance;      //商户的距离              Null                     附近人的距离            Null
//    String  photo;        //商户图片                用户头像                 附近人的头像             用户头像
//    String  content;      //团购内容                发布微博的内容           附近人的个人信息         评论内容
//    String  salebefore;   //打折前的价格            转发数                   Null                    Null
//    String  salelater;    //折后价                  评论数                   Null                    Null
//    String  salenum;      //销量                    点赞数                   Null                    Null
class ViewHolderComment{
    TextView name;
    TextView time;
    TextView content;
    ImageView photo;
}