package com.tankwar.tank;


import com.tankwar.game.Bullet;
import com.tankwar.game.GameFrame;
import com.tankwar.utilis.BulletsPool;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.MyUtil;

import javax.swing.*;
import java.awt.*;
import java.lang.invoke.SwitchPoint;
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
    public static final int DEFAULT_HP = 1000;


    //座標
    private int x,y;
    private int atk;
    private int hp = DEFAULT_HP;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int status = STATE_STAND;
    private Color color;
    private boolean isEnemy = false;


    //TODO 砲彈
    private List<Bullet> bullets = new ArrayList();

    public Tank(int x,int y,int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
        color = MyUtil.getRandomColor();
    }


    public void draw(Graphics g){
        /**
         * 每楨都要執行
         */
        logic();
        drawTank(g);
        drawBullets(g);
    }
    /**
     * 繪製坦克
     * @param g
     */
    public abstract void drawTank(Graphics g);

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

    /**
     * 坦克開火
     */
    public void fire(){
        int bulletX = x;
        int bulletY = y;
        switch (dir){
            case DIR_UP:
                bulletY -= 1.5*RADIUS;
                break;
            case DIR_DOWN:
                bulletY += 1.5*RADIUS;
                break;
            case DIR_LEFT:
                bulletX -= 1.5*RADIUS;
                break;
            case DIR_RIGHT:
                bulletX += 1.5*RADIUS;
                break;
        }
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
    private void drawBullets(Graphics g){
        for (Bullet bullet: bullets){
            bullet.draw(g);
        }
        for (int i=0;i<bullets.size();i++){
            Bullet bullet = bullets.get(i);
            if(!bullet.isVisible()){
                Bullet remove = bullets.remove(i);
                BulletsPool.theReturn(remove);
            }
        }
    }
}
