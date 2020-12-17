package com.tankwar.utilis;

public class PlayerHandling {


    //坦克初始血量
    public static final int DEFAULT_HP = 5;
    private static int hp1 = DEFAULT_HP;
    private static int hp2 = DEFAULT_HP;


    public PlayerHandling(){};

    //玩家扣血
    public static int player_get_hurt(int player,int atk){
        switch (player){
            case 1:
                hp1 -= atk;
                if (hp1 <= 0) {
                    hp1 = 0;
                }
                break;
            case 2:
                hp2 -= atk;
                if (hp2 <= 0) {
                    hp2 = 0;
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
