package com.eighteen.goradar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lloyd on 7/30/2016.
 */
public class MoveStats {

    ArrayList<AttackMove> moveInfo;

    MoveStats(){
        moveInfo = new ArrayList<>();
    }

    public List<AttackMove> getMoveList(){
        moveInfo.add(new AttackMove("Acid","	Poison	10	9.52	1050ms	200ms"));
        moveInfo.add(new AttackMove("Bite","	Dark	6	12	500ms	200ms"));
        moveInfo.add(new AttackMove("Bubble","	Water	25	10.87	2300ms	200ms"));
        moveInfo.add(new AttackMove("Bug Bite","	Bug	5	11.11	450ms	200ms"));
        moveInfo.add(new AttackMove("Bullet Punch","	Steel	10	8.33	1200ms	200ms"));
        moveInfo.add(new AttackMove("Confusion","	Psychic	15	9.93	1510ms	200ms"));
        moveInfo.add(new AttackMove("Cut","	Normal	12	10.62	1130ms	200ms"));
        moveInfo.add(new AttackMove("Dragon Breath","	Dragon	6	12	500ms	200ms"));
        moveInfo.add(new AttackMove("Ember","	Fire	10	9.52	1050ms	200ms"));
        moveInfo.add(new AttackMove("Feint Attack","	Dark	12	11.54	1040ms	200ms"));
        moveInfo.add(new AttackMove("Fire Fang","	Fire	10	11.9	840ms	200ms"));
        moveInfo.add(new AttackMove("Frost Breath","	Ice	9	11.11	810ms	200ms"));
        moveInfo.add(new AttackMove("Fury Cutter","	Bug	3	7.5	400ms	200ms"));
        moveInfo.add(new AttackMove("Ice Shard","	Ice	15	10.71	1400ms	200ms"));
        moveInfo.add(new AttackMove("Karate Chop","	Fighting	6	7.5	800ms	200ms"));
        moveInfo.add(new AttackMove("Lick","	Ghost	5	10	500ms	200ms"));
        moveInfo.add(new AttackMove("Low Kick","	Fighting	5	8.33	600ms	200ms"));
        moveInfo.add(new AttackMove("Metal Claw","	Steel	8	12.7	630ms	200ms"));
        moveInfo.add(new AttackMove("Mud Shot","	Ground	6	10.91	550ms	200ms"));
        moveInfo.add(new AttackMove("Mud Slap","	Ground	15	11.11	1350ms	200ms"));
        moveInfo.add(new AttackMove("Peck","	Flying	10	8.7	1150ms	200ms"));
        moveInfo.add(new AttackMove("Poison Jab","	Poison	12	11.43	1050ms	200ms"));
        moveInfo.add(new AttackMove("Poison Sting","	Poison	6	10.43	575ms	200ms"));
        moveInfo.add(new AttackMove("Pound","	Normal	7	12.96	540ms	200ms"));
        moveInfo.add(new AttackMove("Psycho Cut","	Psychic	7	12.28	570ms	200ms"));
        moveInfo.add(new AttackMove("Quick Attack","	Normal	10	7.52	1330ms	200ms"));
        moveInfo.add(new AttackMove("Razor Leaf","	Grass	15	10.34	1450ms	200ms"));
        moveInfo.add(new AttackMove("Rock Smash","	Fighting	15	10.64	1410ms	200ms"));
        moveInfo.add(new AttackMove("Rock Throw","	Rock	12	8.82	1360ms	200ms"));
        moveInfo.add(new AttackMove("Scratch","	Normal	6	12	500ms	200ms"));
        moveInfo.add(new AttackMove("Shadow Claw","	Ghost	11	11.58	950ms	200ms"));
        moveInfo.add(new AttackMove("Spark","	Electric	7	10	700ms	200ms"));
        moveInfo.add(new AttackMove("Splash","	Water	0	0	1230ms	200ms"));
        moveInfo.add(new AttackMove("Steel Wing","	Steel	15	11.28	1330ms	200ms"));
        moveInfo.add(new AttackMove("Sucker Punch","	Dark	7	10	700ms	200ms"));
        moveInfo.add(new AttackMove("Tackle","	Normal	12	10.91	1100ms	200ms"));
        moveInfo.add(new AttackMove("Thunder Shock","	Electric	5	8.33	600ms	200ms"));
        moveInfo.add(new AttackMove("Vine Whip","	Grass	7	10.77	650ms	200ms"));
        moveInfo.add(new AttackMove("Water Gun","	Water	6	12	500ms	200ms"));
        moveInfo.add(new AttackMove("Wing Attack","	Flying	9	12	750ms	200ms"));
        moveInfo.add(new AttackMove("Zen Headbutt","	Psychic	12	11.43	1050ms	200ms"));

        //start special moves
        moveInfo.add(new AttackMove("Aerial Ace","	Flying	30	10.34	2900ms	600ms"));
        moveInfo.add(new AttackMove("Air Cutter","	Flying	30	9.09	3300ms	900ms"));
        moveInfo.add(new AttackMove("Ancient Power","	Rock	35	9.72	3600ms	350ms"));
        moveInfo.add(new AttackMove("Aqua Jet","	Water	25	10.64	2350ms	400ms"));
        moveInfo.add(new AttackMove("Aqua Tail","	Water	45	19.15	2350ms	200ms"));
        moveInfo.add(new AttackMove("Blizzard","Ice	100	25.64	3900ms	0ms"));
        moveInfo.add(new AttackMove("Body Slam","	Normal	40	25.64	1560ms	200ms"));
        moveInfo.add(new AttackMove("Bone Club","	Ground	25	15.63	1600ms	250ms"));
        moveInfo.add(new AttackMove("Brick Break","	Fighting	30	18.75	1600ms	400ms"));
        moveInfo.add(new AttackMove("Brine	Water","	25	10.42	2400ms	350ms"));
        moveInfo.add(new AttackMove("Bubble Beam","	Water	30	10.34	2900ms	200ms"));
        moveInfo.add(new AttackMove("Bug Buzz","Bug	75	17.65	4250ms	1500ms"));
        moveInfo.add(new AttackMove("Bulldoze","	Ground	35	10.29	3400ms	1100ms"));
        moveInfo.add(new AttackMove("Cross Chop","	Fighting	60	30	2000ms	300ms"));
        moveInfo.add(new AttackMove("Cross Poison","	Poison	25	16.67	1500ms	300ms"));
        moveInfo.add(new AttackMove("Dark Pulse","Dark	45	12.86	3500ms	1100ms"));
        moveInfo.add(new AttackMove("Dazzling Gleam","Fairy	55	13.1	4200ms	800ms"));
        moveInfo.add(new AttackMove("Dig","	Ground	70	12.07	5800ms	400ms"));
        moveInfo.add(new AttackMove("Disarming Voice	","Fairy	25	6.41	3900ms	1800ms"));
        moveInfo.add(new AttackMove("Discharge","	Electric	35	14	2500ms	700ms"));
        moveInfo.add(new AttackMove("Dragon Claw","	Dragon	35	23.33	1500ms	200ms"));
        moveInfo.add(new AttackMove("Dragon Pulse","Dragon	65	18.06	3600ms	1200ms"));
        moveInfo.add(new AttackMove("Draining Kiss","Fairy	25	8.93	2800ms	100ms"));
        moveInfo.add(new AttackMove("Drill Peck","	Flying	40	14.81	2700ms	900ms"));
        moveInfo.add(new AttackMove("Drill Run","	Ground	50	14.71	3400ms	700ms"));
        moveInfo.add(new AttackMove("Earthquake","	Ground	100	23.81	4200ms	1950ms"));
        moveInfo.add(new AttackMove("Fire Blast","	Fire	100	24.39	4100ms	400ms"));
        moveInfo.add(new AttackMove("Flame Charge","	Fire	25	8.06	3100ms	200ms"));
        moveInfo.add(new AttackMove("Fire Punch","	Fire	40	14.29	2800ms	510ms"));
        moveInfo.add(new AttackMove("Flame Burst","	Fire	30	14.29	2100ms	400ms"));
        moveInfo.add(new AttackMove("Flame Wheel","Fire	40	8.7	4600ms	500ms"));
        moveInfo.add(new AttackMove("Flamethrower","Fire	55	18.97	2900ms	900ms"));
        moveInfo.add(new AttackMove("Flash Cannon","	Steel	60	15.38	3900ms	1100ms"));
        moveInfo.add(new AttackMove("Giga Drain","	Grass	50	13.89	3600ms	1150ms"));
        moveInfo.add(new AttackMove("Gunk Shot","	Poison	65	21.67	3000ms	400ms"));
        moveInfo.add(new AttackMove("Heat Wave","	Fire	80	21.05	3800ms	400ms"));
        moveInfo.add(new AttackMove("Heart Stamp","	Psychic	20	7.84	2550ms	300ms"));
        moveInfo.add(new AttackMove("Horn Attack","	Normal	25	11.36	2200ms	300ms"));
        moveInfo.add(new AttackMove("Hurricane","	Flying	80	25	3200ms	1770ms"));
        moveInfo.add(new AttackMove("Hydro Pump","	Water	90	23.68	3800ms	2100ms"));
        moveInfo.add(new AttackMove("Hyper Beam","	Normal	120	24	5000ms	800ms"));
        moveInfo.add(new AttackMove("Hyper Fang","	Normal	35	16.67	2100ms	300ms"));
        moveInfo.add(new AttackMove("Ice Beam","	Ice	65	17.81	3650ms	1350ms"));
        moveInfo.add(new AttackMove("Ice Punch","	Ice	45	12.86	3500ms	1100ms"));
        moveInfo.add(new AttackMove("Icy Wind","	Ice	25	6.58	3800ms	700ms"));
        moveInfo.add(new AttackMove("Iron Head","	Steel	30	15	2000ms	250ms"));
        moveInfo.add(new AttackMove("Leaf Blade","	Grass	55	19.64	2800ms	1000ms"));
        moveInfo.add(new AttackMove("Low Sweep","	Fighting	30	13.33	2250ms	150ms"));
        moveInfo.add(new AttackMove("Magnet Bomb","	Steel	30	10.71	2800ms	550ms"));
        moveInfo.add(new AttackMove("Megahorn","	Bug	80	25	3200ms	300ms"));
        moveInfo.add(new AttackMove("Mega Drain","	Grass	25	7.81	3200ms	1200ms"));
        moveInfo.add(new AttackMove("Moonblast","	Fairy	85	20.73	4100ms	600ms"));
        moveInfo.add(new AttackMove("Mud Bomb","	Ground	30	11.54	2600ms	450ms"));
        moveInfo.add(new AttackMove("Night Slash","	Dark	30	11.11	2700ms	200ms"));
        moveInfo.add(new AttackMove("Ominous Wind","	Ghost	30	9.68	3100ms	250ms"));
        moveInfo.add(new AttackMove("Parabolic Charge","	Electric	25	11.9	2100ms	400ms"));
        moveInfo.add(new AttackMove("Petal Blizzard","	Grass	65	20.31	3200ms	1000ms"));
        moveInfo.add(new AttackMove("Play Rough","	Fairy	55	18.97	2900ms	1300ms"));
        moveInfo.add(new AttackMove("Poison Fang","	Poison	25	10.42	2400ms	200ms"));
        moveInfo.add(new AttackMove("Power Gem","	Rock	40	13.79	2900ms	800ms"));
        moveInfo.add(new AttackMove("Power Whip","	Grass	70	25	2800ms	1300ms"));
        moveInfo.add(new AttackMove("Psybeam","	Psychic	40	11.11	3600ms	1300ms"));
        moveInfo.add(new AttackMove("Psychic","	Psychic	55	19.64	2800ms	1200ms"));
        moveInfo.add(new AttackMove("Psyshock","	Psychic	40	14.81	2700ms	500ms"));
        moveInfo.add(new AttackMove("Psystrike","	Psychic	100	19.61	5100ms	900ms"));
        moveInfo.add(new AttackMove("Rest","Normal	50	16.13	3100ms	1296ms"));
        moveInfo.add(new AttackMove("Rock Slide","	Rock	50	15.63	3200ms	1400ms"));
        moveInfo.add(new AttackMove("Rock Tomb","	Rock	30	8.82	3400ms	900ms"));
        moveInfo.add(new AttackMove("Scald","	Water	55	13.75	4000ms	2100ms"));
        moveInfo.add(new AttackMove("Seed Bomb","	Grass	40	16.67	2400ms	500ms"));
        moveInfo.add(new AttackMove("Shadow Ball","	Ghost	45	14.61	3080ms	300ms"));
        moveInfo.add(new AttackMove("Shadow Sneak","	Ghost	25	8.06	3100ms	600ms"));
        moveInfo.add(new AttackMove("Shadow Punch","	Ghost	25	11.9	2100ms	300ms"));
        moveInfo.add(new AttackMove("Signal Beam","	Bug	45	14.52	3100ms	1000ms"));
        moveInfo.add(new AttackMove("Sludge	","Poison	30	11.54	2600ms	500ms"));
        moveInfo.add(new AttackMove("Sludge Bomb","	Poison	55	21.15	2600ms	500ms"));
        moveInfo.add(new AttackMove("Sludge Wave","	Poison	70	20.59	3400ms	900ms"));
        moveInfo.add(new AttackMove("Solar Beam","	Grass	120	24.49	4900ms	1700ms"));
        moveInfo.add(new AttackMove("Stomp","	Normal	30	14.29	2100ms	700ms"));
        moveInfo.add(new AttackMove("Stone Edge","	Rock	80	25.81	3100ms	400ms"));
        moveInfo.add(new AttackMove("Struggle","	Normal	15	8.85	1695ms	700ms"));
        moveInfo.add(new AttackMove("Submission","	Fighting	30	14.29	2100ms	150ms"));
        moveInfo.add(new AttackMove("Swift","Normal	30	10	3000ms	500ms"));
        moveInfo.add(new AttackMove("Thunder","Electric	100	23.26	4300ms	1550ms"));
        moveInfo.add(new AttackMove("Thunder Punch","	Electric	40	16.67	2400ms	250ms"));
        moveInfo.add(new AttackMove("Thunderbolt","Electric	55	20.37	2700ms	800ms"));
        moveInfo.add(new AttackMove("Twister","Dragon	25	9.26	2700ms	1750ms"));
        moveInfo.add(new AttackMove("Vice Grip","Normal	25	11.9	2100ms	250ms"));
        moveInfo.add(new AttackMove("Water Pulse","Water	35	10.61	3300ms	1000ms"));
        moveInfo.add(new AttackMove("Wrap","	Normal	25	6.25	4000ms	600ms"));
        moveInfo.add(new AttackMove("X Scissor","	Bug	35	16.67	2100ms	250ms"));

        return moveInfo;
    }
}
