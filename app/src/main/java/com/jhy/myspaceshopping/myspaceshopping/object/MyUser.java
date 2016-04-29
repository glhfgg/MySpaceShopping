package com.jhy.myspaceshopping.myspaceshopping.object;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 俊峰 on 2016/4/22.
 */

public class MyUser extends BmobUser{

    private String personname; // 用户昵称
    private String content;   // 用户的自我介绍
    private Boolean Sex;      // 用户性别
    private String UserImg;   // 用户头像
    private String birthday;  // 用户生日
    private String QQ;        // 用户QQ
    private Boolean friend;   //是否公开交友
    private BmobFile icon;    //用户头像
    private BmobRelation likes;//多对多关系：用于存储喜欢该帖子的所有用户


    public BmobRelation getLikes() {return likes;  }

    public void setLikes(BmobRelation likes) { this.likes = likes; }

    public BmobFile getIcon() { return icon;}

    public void setIcon(BmobFile icon) {this.icon = icon; }

    public Boolean getFriend() {
        return friend;
    }

    public void setFriend(Boolean friend) {
        this.friend = friend;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;  // 用户的所在城市

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSex() {
        return Sex;
    }

    public void setSex(Boolean sex) {
        Sex = sex;
    }

    public String getUserImg() {
        return UserImg;
    }

    public void setUserImg(String userImg) {
        UserImg = userImg;
    }
}
