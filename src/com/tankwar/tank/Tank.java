package com.tankwar.tank;


import com.tankwar.game.Bullet;
import com.tankwar.game.Explode;
import com.tankwar.game.GameFrame;
import com.tankwar.utilis.BulletsPool;
import com.tankwar.utilis.EnemyTanksPool;
import com.tankwar.utilis.ExplodesPool;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 坦克相關class
 */
public abstract  class Tank {
    //四個方向
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;
    //坦克半徑
    public static final int RADIUS = 30;
    //默認速度
    public static final int DEFAULT_SPEED = 6;
    //坦克狀態
    public static final int STATE_STAND = 0;
    public static final int STATE_MOVE = 1;
    public static final int STATE_DIE = 2;
    //坦克初始血量
    public static final int DEFAULT_HP = 5;
    //坦克名字
    private String name;
    //敵人坦克血量
    public static final int ENEMY_MIN_HP = 1;
    public static final int ENEMY_MAX_HP = 3;
    //坦克攻擊力
    public static final int ATK_MIN = 1;
    public static final int ATK_MAX = 1;

    //座標
    private int x,y;
    private int atk;
    private int hp = DEFAULT_HP;
    private int enemyhp = MyUtil.getRandomNumber(ENEMY_MIN_HP,ENEMY_MAX_HP);
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int status = STATE_STAND;
    private Color color;
    private boolean isEnemy = false;


    //砲彈容器
    private List<Bullet> bullets = new ArrayList();
    //爆炸效果容器
    private List<Explode> explodes = new ArrayList();

    //給物件池用
    public Tank(){
        initTank();
    };
    public Tank(int x,int y,int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
        initTank();
    }

    private void initTank(){
        color = MyUtil.getRandomColor();
        atk = MyUtil.getRandomNumber(ATK_MIN,ATK_MAX);
        name = MyUtil.getRandomName();
    }


    public void draw(Graphics g,int player){
        /**
         * 每楨都要執行
         */
        logic();
        drawTank(g,player);
        drawBullets(g,player);
        drawName(g);
    }

    private void drawName(Graphics g) {
        g.setColor(color);
        g.setFont(Constant.NAME_FONT);
        g.drawString(name,x - RADIUS,y-35);
    }

    /**
     * 繪製坦克
     * @param g
     */
    public abstract void drawTank(Graphics g,int player);

    //坦克邏輯處理
    private void logic(){
        switch (status){
            case STATE_STAND:
                break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;
        }
    }

    //坦克移動
    private void move(){
        switch (dir){
            case DIR_UP:
                y -= speed;
                if(y < RADIUS + GameFrame.titleBarH){
                    y = RADIUS + GameFrame.titleBarH;
                }
                break;
            case DIR_DOWN:
                y += speed;
                if(y > Constant.FRAME_HEIGHT - RADIUS){
                    y = Constant.FRAME_HEIGHT - RADIUS;
                }
                break;
            case DIR_LEFT:
                x -= speed;
                if(x < RADIUS){
                    x = RADIUS;
                }
                break;
            case DIR_RIGHT:
                x += speed;
                if(x > Constant.FRAME_WIDTH - RADIUS){
                    x = Constant.FRAME_WIDTH - RADIUS;
                }
                break;
        }
    }

    public static int getDirUp(){
        return DIR_UP;
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getHp(){
        return hp;
    }
    public void setHp(int hp){
        this.hp = hp;
    }
    public int getAtk(){
        return atk;
    }
    public void setAtk(int atk){

    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List getBullets() {
        return bullets;
    }

    public void setBullets(List bullets) {
        this.bullets = bullets;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public int getEnemyhp() {
        return enemyhp;
    }

    public void setEnemyhp(int enemyhp) {
        this.enemyhp = enemyhp;
    }

    /**
     * 坦克開火
     */
    public void fire(){
        //從子彈池拿子彈
        Bullet bullet = BulletsPool.get();
        //子彈屬性設定
        bullet.setX(x);
        bullet.setY(y);
        bullet.setDir(dir);
        bullet.setAtk(atk);
        bullet.setVisible(true);
        bullets.add(bullet);
    }

    /**
     * 繪製坦克的子彈
     * @param g
     */
    private void drawBullets(Graphics g,int player){
        for (Bullet bullet: bullets){
            bullet.draw(g,player);
        }
        for (int i=0;i<bullets.size();i++){
            Bullet bullet = bullets.get(i);
            if(!bullet.isVisible()){
                Bullet remove = bullets.remove(i);
                i--;
                BulletsPool.theReturn(remove);
            }
        }
    }
    //reset時子彈清空
    public void  bulletsReturn(){
        for (Bullet bullet : bullets) {
            BulletsPool.theReturn(bullet);
        }
    }
    //坦克和子彈碰撞判斷
    public void collideBullets(List<Bullet> bullets){
        //對所有子彈和坦克進行碰撞檢測
        for(Bullet bullet : bullets){
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            //碰撞發生
            if(MyUtil.isCollide(this.x, this.y,RADIUS,bulletX,bulletY)){
                //子彈消失
                bullet.setVisible(false);
                //坦克受到傷害
                hurt(bullet);
                //添加爆炸效果，以及當前被擊中坦克的座標
                Explode explode = ExplodesPool.get();
                explode.setX(x+RADIUS*2);
                explode.setY(y+RADIUS*2);
                explode.setVisible(true);
                explode.setIndex(0);
                explodes.add(explode);
            }
        }
    }
    //坦克受傷
    private void hurt(Bullet bullet){
        int atk = bullet.getAtk();
        hp-=atk;
        if(hp <= 0){
            hp = 0;
            die();
        }
        if(isEnemy){
            enemyhp-=atk;
            if(enemyhp <= 0){
                hp =0;
                die();
            }
        }
    }

    private void die(){
        //敵人死了
        if(isEnemy){
            //歸還物件池
            EnemyTanksPool.theReturn(this);
        }
        else{//TODO
            //gameover
            GameFrame.setGameState(Constant.STATE_OVER);

        }
    }

    public boolean isDie(){
        return enemyhp<=0;
    }

    /**
     * 繪製當前坦克上所有的爆炸效果
     * @param g
     */
    public void drawExplode(Graphics g){
        for (Explode explode : explodes) {
            explode.draw(g);
        }
//        將不可見的爆炸效果刪除
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            if(!explode.isVisible()){
                Explode remove = explodes.remove(i);
                ExplodesPool.back(remove);
                i--;
            }
        }
    }

    private class life{
    }
}
