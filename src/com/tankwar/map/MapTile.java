package com.tankwar.map;

import com.tankwar.utilis.MyUtil;

import java.awt.*;

/**
 * 地圖元素塊
 */
public class MapTile {
    private static Image tileImg;
    public static int tileW = 40;
    static {
        tileImg = MyUtil.createImage("res/image/map/walls.gif");
        if(tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
    }
    //圖片資源的左上角
    private int x,y;

    public MapTile() {
    }

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
        if(tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
    }

    public void draw(Graphics g){
        if(tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
        g.drawImage(tileImg,x,y,null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
