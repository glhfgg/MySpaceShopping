package com.jhy.myspaceshopping.myspaceshopping.objectmode;

import java.util.List;

/**
 * PopupWindow下拉列表，左侧菜单==一级分类
 * Created by TOSHIBA on 2016/4/22.
 */
public class FirstClassItemModel {
    private int id;
    private String name;
    private List<SecondClassItemModel> secondList;
    public FirstClassItemModel(int id,String name,List<SecondClassItemModel> secondList){

        this.id=id;
        this.name=name;
        this.secondList=secondList;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SecondClassItemModel> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<SecondClassItemModel> secondList) {
        this.secondList = secondList;
    }

    @Override
    public String toString() {
        return "FirstClassItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondList=" + secondList +
                '}';
    }
}
