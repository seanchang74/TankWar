package com.tankwar.utilis;

import java.awt.*;
import java.net.PortUnreachableException;

/*
遊戲內常數管理
 */
public class Constant {
    /********************遊戲視窗相關*******************/
    public static final String GAME_TITLE = "坦克大戰1.0";
    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 576;

    //動態獲得系統的解析度
    public static final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static final int FRAME_X = SCREEN_W - FRAME_WIDTH >> 1;
    public static final int FRAME_Y = SCREEN_H - FRAME_HEIGHT >> 1;

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
    public  static final Font NAME_FONT = new Font("微軟正黑體",Font.BOLD,15);

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
