package com.eighteen.goradar.model;

import java.io.Serializable;

/**
 * Created by hyzx on 2016/11/8.
 */
public class SearchModel implements Serializable {

    public  SearchModel(){

    }

    public String name;

    private String list_id;
    private String pokemon_name;
    private String type1;
    private String type2;
    private String total;
    private String hp;
    private String attack;
    private String defense;
    private String sp_attack;
    private String sp_defense;
    private String speed;
    private String language;
 
    private String sortLetters;  //显示数据拼音的首字母
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

private int imageUrl;
    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getList_id() {
        return list_id;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }

    public String getPokemon_name() {
        return pokemon_name;
    }

    public void setPokemon_name(String pokemon_name) {
        this.pokemon_name = pokemon_name;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getSp_attack() {
        return sp_attack;
    }

    public void setSp_attack(String sp_attack) {
        this.sp_attack = sp_attack;
    }

    public String getSp_defense() {
        return sp_defense;
    }

    public void setSp_defense(String sp_defense) {
        this.sp_defense = sp_defense;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return list_id+"list_id"+pokemon_name+"pokemon_name"+language+"pokemon_name";
    }


    public  String type;
    public  String subtype;
    public  String weight;
    public  String height;
    public  String maxCP;

    public String mainAttack1;
    public String mainAttack2;

    public String subAttack1;
    public String subAttack2;
    public String subAttack3;

    double evoCPMultiplier;
    double evoSD;

    public String candyToEvolve;
    String imagePath;



    public SearchModel(String n, String t, String sub, String maxC, String w, String m1, String m2,
                       String s1, String s2, String s3, String evo, double evoMultiplier, double evoSD){
        name = n;  //pokemon name
        type = t;  //pokemon type
        subtype = sub;
        weight = w;
        maxCP = maxC;
        mainAttack1 = m1;
        mainAttack2 = m2;
        subAttack1 = s1;
        subAttack2 = s2;
        subAttack3 = s3;
        candyToEvolve = evo;
        evoCPMultiplier = evoMultiplier;
        this.evoSD = evoSD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
