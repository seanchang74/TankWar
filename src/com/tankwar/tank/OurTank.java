package com.tankwar.tank;

import com.tankwar.utilis.MyUtil;

import java.awt.*;

/**
 * 玩家坦克類
 */
public class OurTank extends Tank{
    //坦克圖片定義
    private static Image[] p1_tankImg_hp1;
    private static Image[] p1_tankImg_hp2;
    private static Image[] p1_tankImg_hp3;
    private static Image[] p2_tankImg_hp1;
    private static Image[] p2_tankImg_hp2;
    private static Image[] p2_tankImg_hp3;

    //圖片初始化
    static { //p1_hp1
        p1_tankImg_hp1 = new Image[4];
        p1_tankImg_hp1[0] = MyUtil.createImage("res/image/tank/player/p1tankU.gif");
        p1_tankImg_hp1[1] = MyUtil.createImage("res/image/tank/player/p1tankD.gif");
        p1_tankImg_hp1[2] = MyUtil.createImage("res/image/tank/player/p1tankL.gif");
        p1_tankImg_hp1[3] = MyUtil.createImage("res/image/tank/player/p1tankR.gif");
    }
    static { //p1_hp2
        p1_tankImg_hp2 = new Image[4];
        p1_tankImg_hp2[0] = MyUtil.createImage("res/image/tank/player/p1tank2U.png");
        p1_tankImg_hp2[1] = MyUtil.createImage("res/image/tank/player/p1tank2D.png");
        p1_tankImg_hp2[2] = MyUtil.createImage("res/image/tank/player/p1tank2L.png");
        p1_tankImg_hp2[3] = MyUtil.createImage("res/image/tank/player/p1tank2R.png");
    }
    static { //p1_hp3
        p1_tankImg_hp3 = new Image[4];
        p1_tankImg_hp3[0] = MyUtil.createImage("res/image/tank/player/p1tank3U.png");
        p1_tankImg_hp3[1] = MyUtil.createImage("res/image/tank/player/p1tank3D.png");
        p1_tankImg_hp3[2] = MyUtil.createImage("res/image/tank/player/p1tank3L.png");
        p1_tankImg_hp3[3] = MyUtil.createImage("res/image/tank/player/p1tank3R.png");
    }
    static { //p2_hp1
        p2_tankImg_hp1 = new Image[4];
        p2_tankImg_hp1[0] = MyUtil.createImage("res/image/tank/player/p2tankU.gif");
        p2_tankImg_hp1[1] = MyUtil.createImage("res/image/tank/player/p2tankD.gif");
        p2_tankImg_hp1[2] = MyUtil.createImage("res/image/tank/player/p2tankL.gif");
        p2_tankImg_hp1[3] = MyUtil.createImage("res/image/tank/player/p2tankR.gif");
    }
    static { //p2_hp2
        p2_tankImg_hp2 = new Image[4];
        p2_tankImg_hp2[0] = MyUtil.createImage("res/image/tank/player/p2tank2U.png");
        p2_tankImg_hp2[1] = MyUtil.createImage("res/image/tank/player/p2tank2D.png");
        p2_tankImg_hp2[2] = MyUtil.createImage("res/image/tank/player/p2tank2L.png");
        p2_tankImg_hp2[3] = MyUtil.createImage("res/image/tank/player/p2tank2R.png");
    }
    static { //p2_hp2
        p2_tankImg_hp3 = new Image[4];
        p2_tankImg_hp3[0] = MyUtil.createImage("res/image/tank/player/p2tank3U.png");
        p2_tankImg_hp3[1] = MyUtil.createImage("res/image/tank/player/p2tank3D.png");
        p2_tankImg_hp3[2] = MyUtil.createImage("res/image/tank/player/p2tank3L.png");
        p2_tankImg_hp3[3] = MyUtil.createImage("res/image/tank/player/p2tank3R.png");
    }

    public OurTank(int x, int y, int dir){
        super(x,y,dir);
    }

    public void drawTank(Graphics g,int player){
        if(player == 1)
        g.drawImage(p1_tankImg_hp1[getDir()],getX()-RADIUS,getY()-RADIUS,60,60,null );
        else if(player == 2)
        g.drawImage(p2_tankImg_hp1[getDir()],getX()-RADIUS,getY()-RADIUS,60,60,null );
    }
}
