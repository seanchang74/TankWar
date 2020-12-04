package com.tankwar.game;

import com.tankwar.utilis.Constant;

import java.awt.*;

/**
 * 子彈類
 */
public class Bullet {
    //子彈速度為坦克速度2倍
    public static final int DEFAULT_SPEED = Tank.DEFAULT_SPEED << 1;

    public static final int RADIUS = 4;

    private int x,y;
    private int speed = DEFAULT_SPEED;
    private int dir;
    private int atk;
    private Color color = Color.ORANGE;

    public Bullet(int x,int y,int dir,int atk){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.atk = atk;

    }

    /**
     * 繪製砲彈
     * @param g
     */
    public void draw(Graphics g){
        logic();
        g.setColor(color);
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);
    }

    /**
     * 子彈控制
     */
    private void logic(){
        move();
    }

    private void move(){
        switch (dir){
            case Tank.DIR_UP:
                y -= speed;
                break;
            case Tank.DIR_DOWN:
                y += speed;
                break;
            case Tank.DIR_LEFT:
                x -= speed;
                break;
            case Tank.DIR_RIGHT:
                x += speed;
                break;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }
}
