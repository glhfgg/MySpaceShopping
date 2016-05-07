package com.jhy.myspaceshopping.myspaceshopping.objectmode;

/**
 * Created by TOSHIBA on 2016/4/14.
 */
//全部商家--大致信息的列表
public class StoreBaseModel {
  //  String  imgThemePicture;//商家招牌图片
    String  textStoreName;//商家店名
    float  barStar;//星级
    String  textComment;//评价人数
    String  textAverage;//人均消费
    String  textLocation;//大致地点
    String  textDistance;//用户与商家之间的距离

    //构造方法
    public StoreBaseModel(String textStoreName, float barStar, String textComment, String textAverage, String textLocation, String textDistance) {
        //this.imgThemePicture = imgThemePicture;
        this.textStoreName= textStoreName;
        this.barStar = barStar;
        this.textComment = textComment;
        this.textAverage = textAverage;
        this.textLocation = textLocation;
        this.textDistance = textDistance;
    }
    //set+get
    /*public void setImgThemePicture(String imgThemePicture) {
        this.imgThemePicture = imgThemePicture;
    }*/

    public void setTextStoreName(String textStoreName) {
        this.textStoreName = textStoreName;
    }

    public void setBarStar(float barStar) {
        this.barStar = barStar;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public void setTextAverage(String textAverage) {
        this.textAverage = textAverage;
    }

    public void setTextLocation(String textLocation) {
        this.textLocation = textLocation;
    }

    public void setTextDistance(String textDistance) {
        this.textDistance = textDistance;
    }

  /*  public String getImgThemePicture() {
        return imgThemePicture;
    }*/

    public String getTextStoreName() {
        return textStoreName;
    }

    public float getBarStar() {
        return barStar;
    }

    public String getTextComment() {
        return textComment;
    }

    public String getTextAverage() {
        return textAverage;
    }

    public String getTextLocation() {
        return textLocation;
    }

    public String getTextDistance() {
        return textDistance;
    }
}
