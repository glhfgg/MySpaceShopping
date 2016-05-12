package com.jhy.myspaceshopping.myspaceshopping.object;

/**
 * Created by TOSHIBA on 2016/5/11.
 */
public class Level {
    int cat_id;
    String cat_name;
    int  subcat_id;
    String subcat_name;

    public Level(int cat_id, String cat_name, String subcat_name, int subcat_id) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.subcat_name = subcat_name;
        this.subcat_id = subcat_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(int subcat_id) {
        this.subcat_id = subcat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getSubcat_name() {
        return subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }
}
