package com.tankwar.game;
import com.tankwar.tank.Tank;
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
    private Color colorOrange = Color.ORANGE;
    private Color colorGreen = Color.decode("#7fe78b");
    //子彈是否可見
    private boolean visible = true;

    public Bullet(int x,int y,int dir,int atk){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.atk = atk;

    }
    //給物件池使用，所有屬性皆為默認值
    public Bullet(){}

    /**
     * 繪製砲彈
     * @param g
     */
    public void draw(Graphics g ,int player){
        if(!visible)return;
        logic();
        if(player == 1){
        g.setColor(colorOrange);}
        else if(player == 2)g.setColor(colorGreen);
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
                if(y<0)visible = false;
                break;
            case Tank.DIR_DOWN:
                y += speed;
                if(y>Constant.FRAME_HEIGHT)visible = false;
                break;
            case Tank.DIR_LEFT:
                x -= speed;
                if(x<0)visible = false;
                break;
            case Tank.DIR_RIGHT:
                x += speed;
                if(x>Constant.FRAME_WIDTH)visible = false;
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
