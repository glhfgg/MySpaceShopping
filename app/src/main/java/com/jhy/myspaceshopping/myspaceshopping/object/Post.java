package com.jhy.myspaceshopping.myspaceshopping.object;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;


/**
 * Created by Administrator on 2016/4/25.
 */
public class Post extends BmobObject{

    private String content;       //内容
    private MyUser author;       //帖子的作者
    private BmobFile image;
    private BmobRelation transpondnum;//多对多关系：转发数
    private BmobRelation commentnum;//多对多关系：评论数
    private BmobRelation likes;//多对多关系：用于存储喜欢该帖子的所有用户


    public BmobRelation getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(BmobRelation commentnum) {
        this.commentnum = commentnum;
    }



    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }



    public MyUser getAuthor() { return author; }

    public void setAuthor(MyUser author) {this.author = author; }

    public String getContent() { return content; }

    public void setContent(String content) {this.content = content;  }

    public BmobRelation getTranspondnum() { return transpondnum;}

    public void setTranspondnum(BmobRelation transpondnum) {this.transpondnum = transpondnum;}


    public BmobFile getImage() {return image;}

    public void setImage(BmobFile image) { this.image = image; }



}
