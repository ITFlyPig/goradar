package com.eighteen.goradar.model;

import java.io.Serializable;

/**
 * Created by hyzx on 2016/10/25.
 */
public class DataModel implements Serializable {
    private String listid;
    private String name;
    private String type_1;
    private String type_2;
    private String language;
    private int imageUrl;

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

    public String getType_2() {
        return type_2;
    }

    public void setType_2(String type_2) {
        this.type_2 = type_2;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String sortLetters;  //显示数据拼音的首字母


    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public String toString() {
        return "listid"+listid+"name"+name+"type_1"+type_1+"type_2"+type_2+"lanage"+language;
    }
}
