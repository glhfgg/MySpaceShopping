package com.jhy.myspaceshopping.myspaceshopping.objectmode;

/**
 * Created by Administrator on 2016/4/21.
 */
public class Movie {
    private String image;
    private String title;
    private String score;

    public Movie(String image, String title, String score) {
        this.image = image;
        this.title = title;
        this.score = score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
