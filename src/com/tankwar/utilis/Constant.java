package com.tankwar.utilis;

import com.tankwar.tank.Tank;

import java.awt.*;
import java.net.PortUnreachableException;

/*
遊戲內常數管理
 */
public class Constant {
    /********************遊戲視窗相關*******************/
    public static final String GAME_TITLE = "坦克大戰1.0";
    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 629;
    public static final int RUN_FRAME_WIDTH = FRAME_WIDTH*9/10;
            ;

    //動態獲得系統的解析度
    public static final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;

    //這兩個沒用到
    public static final int FRAME_X = SCREEN_W - FRAME_WIDTH >> 1;
    public static final int FRAME_Y = SCREEN_H - FRAME_HEIGHT >> 1;

    /********************遊戲菜單相關*******************/
    public  static final int STATE_MENU = 0;
    public  static final int STATE_HELP = 1;
    public  static final int STATE_RUN = 2;
    public  static final int STATE_OVER = 3;

    public static final String[] MENUS = {
            "1 PLAYER",
            "2 PLAYERS",
            "關於",
            "退出遊戲",
    };

    public  static final String[] OVER_STR = {
            "退出遊戲",
            "返回標題",
    };
    // 字體設定
    public  static final Font FONT = new Font("微軟正黑體",Font.BOLD,25);
    public  static final Font NAME_FONT = new Font("微軟正黑體",Font.BOLD,15);

    //螢幕刷新
    public static final int REPAINT_INT = 30;
    /********************遊戲運行中相關*******************/
    //最大敵人數量
    public static final int ENEMY_MAX_COUNT = 8;
    public static final int ENEMY_BORN_INTERVAL = 5000;

    //AI動作間隔時間
    public static final int ENEMY_AI_INTERVAL = 1500;
    //AI開火機率
    public static final double ENEMY_FIRE_CHANGE = 0.05;

    //玩家坦克狀態
    public static final int PLAYER1_DIE = 1;
    public static final int PLAYER2_DIE = 2;
    public static final int PLAYER_BOTH_DIE = 3;
    public static final int PLAYER_BOTH_ALIVE = 4;

    //玩家出生位置
    public static final int PLAYER1_X = RUN_FRAME_WIDTH/3;
    public static final int PLAYER1_Y = FRAME_HEIGHT- Tank.RADIUS*2;
    public static final int PLAYER2_X = RUN_FRAME_WIDTH / 3 * 2;
    public static final int PLAYER2_Y = FRAME_HEIGHT - Tank.RADIUS * 2;
}
