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
    //紀錄時間
    private long aiTime;

    static { //enemy
        enemy_tankImg = new Image[4];
        enemy_tankImg[0] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1U.gif");
        enemy_tankImg[1] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1D.gif");
        enemy_tankImg[2] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1L.gif");
        enemy_tankImg[3] = Toolkit.getDefaultToolkit().createImage("res/image/enemy1R.gif");
    }

    public EnemyTank(int x, int y, int dir){
        super(x,y,dir);
        aiTime = System.currentTimeMillis();
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
        ai();
        g.drawImage(enemy_tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null );
   }

   //敵人AI
   private void ai(){
        if(System.currentTimeMillis()-aiTime > Constant.ENEMY_AI_INTERVAL){
            //隨機方向
            setDir(MyUtil.getRandomNumber(DIR_UP,DIR_RIGHT+1));
            //間隔三秒隨機賦予狀態
            setStatus(MyUtil.getRandomNumber(0,2) == 0? STATE_STAND:STATE_MOVE);
            aiTime = System.currentTimeMillis();
        }
        //開火機率
        if(Math.random() < Constant.ENEMY_FIRE_CHANGE){
            fire();
        }
   }
}
