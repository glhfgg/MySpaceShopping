package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 俊峰 on 2016/4/17.
 */
public class JuCommentAdapter extends BaseAdapter{

    LayoutInflater inflater;
    List<JuUniversalData> list;
    Context context;

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
        holder.time.setText(data.getScore());
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