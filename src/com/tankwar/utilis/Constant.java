package com.tankwar.utilis;

import java.awt.*;

/*
遊戲內常數管理
 */
public class Constant {
    /********************遊戲視窗相關*******************/
    public static final String GAME_TITLE = "坦克大戰1.0";
    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 576;

    public static final int FRAME_X = 600;
    public static final int FRAME_Y = 600;

    /********************遊戲菜單相關*******************/
    public  static final int STATE_MENU = 0;
    public  static final int STATE_HELP = 1;
    public  static final int STATE_RUN = 2;
    public  static final int STATE_OVER = 3;

    public static final String[] MENUS = {
            "1 PLAYER(未實裝)",
            "2 PLAYERS",
            "CONTINUE(未實裝)",
            "關於",
            "退出遊戲",
    };
    // 字體設定
    public  static final Font FONT = new Font("微軟正黑體",Font.BOLD,20);

    //螢幕刷新
    public static final int REPAINT_INT = 30;

    //最大敵人數量
    public static final int ENEMY_MAX_COUNT = 8;
    public static final int ENEMY_BORN_INTERVAL = 5000;

    //AI動作間隔時間
    public static final int ENEMY_AI_INTERVAL = 1500;
    //AI開火機率
    public static final double ENEMY_FIRE_CHANGE = 0.05;
}
