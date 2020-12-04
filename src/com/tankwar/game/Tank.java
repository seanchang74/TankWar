package com.tankwar.game;

import java.util.ArrayList;
import java.util.List;

/**
 * 坦克相關class
 */
public class Tank {
    //四個方向
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;
    //坦克半徑
    public static final int RADIUS = 16;
    //默認速度
    public static final int DEFAULT_SPEED = 4;
    //坦克狀態
    public static final int STATE_STAND = 0;
    public static final int STATE_MOVE = 0;
    public static final int STATE_DIE = 0;

    //座標
    private  int x,y;
    private int atk;
    private int hp;
    private int speed;
    private int dir;
    private int status;


    //TODO 砲彈
    private List bullets = new ArrayList();


}
