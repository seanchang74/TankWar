package com.tankwar.tank;

import com.tankwar.game.GameFrame;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.MyUtil;

import java.awt.*;

/**
 * 敵人坦克類
 */

public class EnemyTank extends Tank{
    //坦克圖片定義
    private static Image[] enemy_tankImg;

    static { //enemy
        enemy_tankImg = new Image[4];
        enemy_tankImg[0] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1U.gif");
        enemy_tankImg[1] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1D.gif");
        enemy_tankImg[2] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1L.gif");
        enemy_tankImg[3] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1R.gif");
    }

    public EnemyTank(int x, int y, int dir){
        super(x,y,dir);
    }

    public static Tank createEnemy(){
        int x = MyUtil.getRandomNumber(0,2) == 0?RADIUS : Constant.FRAME_WIDTH-RADIUS;
        int y = GameFrame.titleBarH +RADIUS;
        int dir = DIR_DOWN;
        Tank enemy = new EnemyTank(x, y, dir);
        enemy.setEnemy(true);
        //TODO
        enemy.setStatus(STATE_MOVE);
        return enemy;
    }

    public void drawTank(Graphics g){
        g.drawImage(enemy_tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null );
   }

}
