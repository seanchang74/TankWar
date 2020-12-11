package com.tankwar.tank;

import com.tankwar.utilis.MyUtil;

import java.awt.*;

/**
 * 玩家坦克類
 */
public class OurTank extends Tank{
    //坦克圖片定義
    private static Image[] p1_tankImg;
    private static Image[] p2_tankImg;

    //圖片初始化
    static { //p1
        p1_tankImg = new Image[4];
        p1_tankImg[0] = MyUtil.createImage("res/image/p1tankU.gif");
        p1_tankImg[1] = MyUtil.createImage("res/image/p1tankD.gif");
        p1_tankImg[2] = MyUtil.createImage("res/image/p1tankL.gif");
        p1_tankImg[3] = MyUtil.createImage("res/image/p1tankR.gif");
    }
    static { //p2
        p2_tankImg = new Image[4];
        p2_tankImg[0] = MyUtil.createImage("res/image/p2tankU.gif");
        p2_tankImg[1] = MyUtil.createImage("res/image/p2tankD.gif");
        p2_tankImg[2] = MyUtil.createImage("res/image/p2tankL.gif");
        p2_tankImg[3] = MyUtil.createImage("res/image/p2tankR.gif");
    }

    public OurTank(int x, int y, int dir){
        super(x,y,dir);
    }

    public void drawTank(Graphics g,int player){
        if(player == 1)
        g.drawImage(p1_tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null );
        else if(player == 2)
        g.drawImage(p2_tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null );
    }
}
