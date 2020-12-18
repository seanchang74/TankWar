package com.tankwar.tank;

import com.tankwar.game.GameFrame;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.EnemyTanksPool;
import com.tankwar.utilis.MyUtil;

import java.awt.*;

/**
 * 敵人坦克類
 */

public class EnemyTank extends Tank{
    //坦克圖片定義
    private static Image[] enemy_tankImg_hp1;
    private static Image[] enemy_tankImg_hp2;
    private static Image[] enemy_tankImg_hp3;
    //紀錄時間
    private long aiTime;

    static { //enemy
        enemy_tankImg_hp1 = new Image[4];
        enemy_tankImg_hp1[0] = MyUtil.createImage("res/image/tank/enemies/enemy1U.gif");
        enemy_tankImg_hp1[1] = MyUtil.createImage("res/image/tank/enemies/enemy1D.gif");
        enemy_tankImg_hp1[2] = MyUtil.createImage("res/image/tank/enemies/enemy1L.gif");
        enemy_tankImg_hp1[3] = MyUtil.createImage("res/image/tank/enemies/enemy1R.gif");
    }
    static { //enemy
        enemy_tankImg_hp2 = new Image[4];
        enemy_tankImg_hp2[0] = MyUtil.createImage("res/image/tank/enemies/enemy2U.gif");
        enemy_tankImg_hp2[1] = MyUtil.createImage("res/image/tank/enemies/enemy2D.gif");
        enemy_tankImg_hp2[2] = MyUtil.createImage("res/image/tank/enemies/enemy2L.gif");
        enemy_tankImg_hp2[3] = MyUtil.createImage("res/image/tank/enemies/enemy2R.gif");
    }
    static { //enemy
        enemy_tankImg_hp3 = new Image[4];
        enemy_tankImg_hp3[0] = MyUtil.createImage("res/image/tank/enemies/enemy3U.gif");
        enemy_tankImg_hp3[1] = MyUtil.createImage("res/image/tank/enemies/enemy3D.gif");
        enemy_tankImg_hp3[2] = MyUtil.createImage("res/image/tank/enemies/enemy3L.gif");
        enemy_tankImg_hp3[3] = MyUtil.createImage("res/image/tank/enemies/enemy3R.gif");
    }
    public EnemyTank(){
        aiTime = System.currentTimeMillis();
    }
    private EnemyTank(int x, int y, int dir){
        super(x,y,dir);
        aiTime = System.currentTimeMillis();
    }

    public static Tank createEnemy(){
        int x = MyUtil.getRandomNumber(0,2) == 0?RADIUS : Constant.RUN_FRAME_WIDTH-RADIUS;
        int y = GameFrame.titleBarH +RADIUS;
        int dir = DIR_DOWN;
        //使用敵人坦克物件池
        Tank enemy = EnemyTanksPool.get();
        enemy.setX(x);
        enemy.setY(y);
        enemy.setDir(dir);
        enemy.setEnemy(true);
        enemy.setStatus(STATE_MOVE);
        return enemy;
    }

    public void drawTank(Graphics g,int player){
        ai();
        switch (getEnemy_hp()){
            case 1:
                g.drawImage(enemy_tankImg_hp1[getDir()],getX()-RADIUS,getY()-RADIUS,59,59,null );
                break;
            case 2:
                g.drawImage(enemy_tankImg_hp2[getDir()],getX()-RADIUS,getY()-RADIUS,59,59,null );
                break;
            case 3:
                g.drawImage(enemy_tankImg_hp3[getDir()],getX()-RADIUS,getY()-RADIUS,59,59,null );
                break;
//            case 4:
//                g.drawImage(enemy_tankImg_hp2[getDir()],getX()-RADIUS,getY()-RADIUS,null );
//                break;
        }
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
