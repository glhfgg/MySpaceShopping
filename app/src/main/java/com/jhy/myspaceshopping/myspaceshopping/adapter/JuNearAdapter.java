package com.jhy.myspaceshopping.myspaceshopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.object.Friends;
import com.jhy.myspaceshopping.myspaceshopping.object.JuUniversalData;
import com.jhy.myspaceshopping.myspaceshopping.object.MyUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 *
 *  String name ;         //商户的名字              用户的名字               附近人的名字
 String score;         //用户的评分              发布时间                 时间
 String location;      //商户的地点              微博来源                 Null
 String distance;      //商户的距离              Null                     附近人的距离
 String  photo;        //商户图片                用户头像                 附近人的头像
 String  content;      //团购内容                发布微博的内容           附近人的个人信息
 String  salebefore;   //打折前的价格            转发数                   Null
 String  salelater;    //折后价                  评论数                   Null
 String  salenum;      //销量                    点赞数                   Null
 * Created by Administrator on 2016/4/14.
 */
public class JuNearAdapter extends BaseAdapter {

    List<JuUniversalData> list;
    LayoutInflater inflater;
    Context context;

    public JuNearAdapter(Context context, List<JuUniversalData> listdata){
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
        ViewHolderNear holder;
        if(convertView ==null){
            holder = new ViewHolderNear();
            convertView = inflater.inflate(R.layout.item_ju_near,null);
            holder.name = (TextView) convertView.findViewById(R.id.ju_nearname);
            holder.score = (TextView) convertView.findViewById(R.id.ju_neartime);
            holder.distance = (TextView) convertView.findViewById(R.id.ju_neardistance);
            holder.storephoto = (ImageView) convertView.findViewById(R.id.ju_nearphoto);
            holder.storecontent = (TextView) convertView.findViewById(R.id.ju_nearcontent);
            holder.likes = (CheckBox) convertView.findViewById(R.id.ju_focus);
            convertView.setTag(holder);
        }

        holder = (ViewHolderNear) convertView.getTag();
        final JuUniversalData data = list.get(position);

        holder.name.setText(data.getName());
        holder.distance.setText(data.getDistance());
        Picasso.with(context).load(data.getPhoto()).into( holder.storephoto);
        holder.storecontent.setText(data.getContent());

        final   MyUser user = BmobUser.getCurrentUser(context, MyUser.class);
        final MyUser likes = new MyUser();
        holder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobRelation relation = new BmobRelation();
                likes.setObjectId(data.getScore());
                relation.add(likes);
                user.setLikes(relation);
                Log.i("result","res----------"+data.getScore());
                Log.i("result","res----------"+user.getObjectId());
                user.update(context,user.getObjectId(), new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        Log.i("life","多对多关联添加成功");
                    }
                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Log.i("life","多对多关联添加失败"+arg1);
                    }
                });

            }
        });

        return convertView;
    }
}
class ViewHolderNear{

    TextView name;
    TextView score;
    TextView distance;
    ImageView storephoto;
    TextView storecontent;
    CheckBox likes;

}
