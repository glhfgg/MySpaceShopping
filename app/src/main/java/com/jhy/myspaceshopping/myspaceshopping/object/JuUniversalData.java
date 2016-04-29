package com.jhy.myspaceshopping.myspaceshopping.object;

/**
 * 设置周边商铺的数据
 * Created by Administrator on 2016/4/13.
 *   String name ;     //商户的名字              用户的名字             标题信息
 String score;         //用户的评分              发布时间               信息数量
 String location;      //商户的地点              微博来源                text（eg：给你留言，约你，关注了你）
 String distance;      //商户的距离              Null                    用户昵称
 String  photo;        //商户图片                用户头像                用户头像
 String  content;      //团购内容                发布微博的内容
 String  salebefore;   //打折前的价格            转发数
 String  salelater;    //折后价                  评论数
 String  salenum;      //销量                    点赞数
 */
public class JuUniversalData {
                          //Perphery                                 Near                    Comment
    String name ;         //商户的名字              用户的名字               附近人的名字             用户的名字
    String score;         //用户的评分              发布时间                 时间                    发布时间
    String location;      //商户的地点              微博来源                 Null                    Null
    String distance;      //商户的距离              Null                     附近人的距离            Null
    String  photo;        //商户图片                用户头像                 附近人的头像             用户头像
    String  content;      //团购内容                发布微博的内容           附近人的个人信息         评论内容
    String  salebefore;   //打折前的价格            转发数                   Null                    Null
    String  salelater;    //折后价                  评论数                   Null                    Null
    String  salenum;      //销量                    点赞数                   Null                    Null

    public JuUniversalData(String name, String score, String location, String distance, String photo, String content, String salebefore, String salelater, String salenum){
        this.name = name;
        this.score = score;
        this.location = location;
        this.distance = distance;
        this.photo = photo;
        this.content = content;
        this.salebefore = salebefore;
        this.salelater = salelater;
        this.salenum = salenum;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSalebefore() {
        return salebefore;
    }

    public void setSalebefore(String salebefore) {
        this.salebefore = salebefore;
    }


    public String getSalelater() {
        return salelater;
    }

    public void setSalelater(String salelater) {
        this.salelater = salelater;
    }


    public String getSalenum() {
        return salenum;
    }

    public void setSalenum(String salenum) {
        this.salenum = salenum;
    }

}
