package com.android.nurriture.entity;

/**
 * Created by Administrator on 2015/12/10.
 */
public class RecipeInfo {
    private String name;
    private String type;
    private String imgpath;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
