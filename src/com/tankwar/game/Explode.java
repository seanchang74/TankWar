package com.tankwar.game;

import com.tankwar.utilis.MyUtil;

import java.awt.*;

/**
 * 控制爆炸效果
 */
public class Explode {
    public static final int EXPLODE_FRAME_COUNT = 16;
    //爆炸圖片
    private static Image[] img;
    private static int explodeWidth;
    private static int explodeHeight;
    static {
        img = new Image[EXPLODE_FRAME_COUNT];
        for (int i = 1;i < 9;i++){

            System.out.println("A");
            img[i] = MyUtil.createImage("res/image/blast"+i+".gif");
        }
    }

    //爆炸效果繪製
    private int x,y;
    //當前播放的禎數範圍
    private int index;
    //是否可見
    private boolean visible = true;


    public Explode(int x,int y){
        this.x = x;
        this.y = y;
        index = 1;

    }

    //todo 爆炸位置要再修正
    public void draw(Graphics g){
        if(explodeHeight <= 0){
//            explodeWidth = img[0].getWidth(null)/2;
//            explodeHeight = img[0].getHeight(null);
        }
        if(!visible)return;
        g.drawImage(img[index/2],x,y,null );
//        g.drawImage(img[index/2],x-explodeWidth,y-explodeHeight,null );
        index++;
        if(index >= EXPLODE_FRAME_COUNT){
            visible = false;
        }
    }
}
