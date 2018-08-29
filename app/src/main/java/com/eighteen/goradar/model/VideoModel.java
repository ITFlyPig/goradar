package com.eighteen.goradar.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by a on 2017/5/12.
 */

public class VideoModel implements Serializable {
    /*
         * 是否第一次进入
         */
    private static final String IS_CLICK="isclick";
    private String id;
    private String url;
    private String language;
    private String title;
    private String Logo_URL;
    private String imageurl;
    private Bitmap bitmap;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo_URL() {
        return Logo_URL;
    }

    public void setLogo_URL(String logo_URL) {
        Logo_URL = logo_URL;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "imageurl='" + imageurl + '\'' +
                '}';
    }
}
