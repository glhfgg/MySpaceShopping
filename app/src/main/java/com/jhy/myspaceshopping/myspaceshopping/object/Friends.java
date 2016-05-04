package com.jhy.myspaceshopping.myspaceshopping.object;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2016/5/3.
 */

public class Friends extends BmobObject{

    private MyUser user;
    private BmobRelation fans;


    public BmobRelation getFans() { return fans; }

    public void setFans(BmobRelation fans) {  this.fans = fans; }


    public MyUser getUser() { return user; }

    public void setUser(MyUser user) {this.user = user;}



}
