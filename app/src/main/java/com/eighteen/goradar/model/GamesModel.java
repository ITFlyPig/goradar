package com.eighteen.goradar.model;

import java.io.Serializable;

/**
 * Created by hyzx on 2016/10/25.
 */
public class GamesModel implements Serializable {
    private String Game_name;
    private String url;

    private int imageUrl;


    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getUrl() {
        return url;
    }

    public String getGame_name() {
        return Game_name;
    }

    public void setGame_name(String game_name) {
        Game_name = game_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GamesModel{" +
                "Game_name='" + Game_name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
