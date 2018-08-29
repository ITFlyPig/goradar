package com.eighteen.goradar;

/**
 * Created by lloyd on 7/30/2016.
 */
public class AttackMove {

    String moveName;
    String type;
    String damage;
    String dps;


    AttackMove(String name, String info){
        String[] myinfo = info.trim().split("\\s+");
        moveName = name.trim();
        type = myinfo[0];
        damage = myinfo[1];
        dps = myinfo[2];
    }

    public String getMoveName() {

        return moveName;
    }

    public String getDps() {

        return dps;
    }

    public String getDamage() {

        return damage;
    }

    public String getType() {
        return type;
    }
}
