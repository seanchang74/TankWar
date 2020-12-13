package com.tankwar.game;

import com.tankwar.utilis.MyUtil;

import java.awt.*;

/**
 * 用來控制爆炸效果的類
 */
public class Explode {
    public static final int EXPLODE_FRAME_COUNT = 8;
    private static Image[] img;
    //爆炸效果的圖片的寬度和高度
    private static int explodewidth;
    private static int explodeheight;
    static {
        img = new Image[EXPLODE_FRAME_COUNT];
        for (int i = 0; i < img.length; i++) {
            img[i] = MyUtil.createImage("res/image/material/blast"+(i+1)+".gif");
        }
    }
    //爆炸效果的屬性
    private int x,y;
    //當前播放的偵的索引
    private int index;
    //
    private boolean visible = true;

    public Explode() {
        index = 0;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        index = 0;
    }

    /**
     *
     * @param g
     */
    public void draw(Graphics g){
        //對爆炸效果圖片的寬高的確定
        if(explodeheight <= 0){
            explodeheight = img[0].getHeight(null);
            explodewidth = img[0].getWidth(null);
        }
        if(!visible) return;
        g.drawImage(img[index],x-explodewidth,y-explodeheight,null);
        index ++;
        if(index >= EXPLODE_FRAME_COUNT){
            visible = false;
        }
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
