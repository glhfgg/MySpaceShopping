package com.jhy.myspaceshopping.myspaceshopping.objectmode;

/**
 * PopupWindow下拉列表，右侧菜单==二级分类
 * Created by TOSHIBA on 2016/4/22.
 */
public class SecondClassItemModel {
    private int id;
    private String name;
    public SecondClassItemModel(){}
    public SecondClassItemModel(int id,String name){
        this.id=id;
        this.name=name;
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

    @Override
    public String toString() {
        return "SecondClassItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
