package com.tankwar.utilis;

import java.awt.*;
import java.net.ConnectException;

public class PlayerHandling {


    //坦克初始血量
    public static final int DEFAULT_HP = 5;
    public static final int DEFAULT_LIFE = 5;
    private static int hp1 = DEFAULT_HP;
    private static int hp2 = DEFAULT_HP;
    private static int life1 = DEFAULT_HP;
    private static int life2 = DEFAULT_HP;


    public PlayerHandling(){};

    //玩家血量設定
    public static int player_hp(int player){
        System.out.println("player"+hp1+" "+hp2);
        if(player == 1)return  hp1;
        else if(player == 2)return hp2;
        else{
            System.out.println("血量設定錯誤");
            return 1;
        }
    }
    //玩家扣血
    public static int player_get_hurt(int player,int atk){
        switch (player){
            case 1:
                hp1 -= atk;
                if (hp1 <= 0) {
                    hp1 = 0;
                    return Constant.PLAYER1_DIE;
                }
                break;
            case 2:
                hp2 -= atk;
                if (hp2 <= 0) {
                    hp2 = 0;
                    return Constant.PLAYER2_DIE;
                }
                break;
        }
        if(hp1 == 0 && hp2 == 0)return Constant.PLAYER_BOTH_DIE;
        return Constant.PLAYER_BOTH_ALIVE;
    }
    //初始化坦克狀態
    public static void initTank_hp(){
        hp1 = DEFAULT_HP;
        hp2 = DEFAULT_HP;
        life1 = DEFAULT_HP;
        life2 = DEFAULT_HP;
    }

    public static int getHp1() {
        return hp1;
    }

    public static void setHp1(int hp1) {
        PlayerHandling.hp1 = hp1;
    }

    public static int getHp2() {
        return hp2;
    }

    public static void setHp2(int hp2) {
        PlayerHandling.hp2 = hp2;
    }
}
