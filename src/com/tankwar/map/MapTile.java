package com.tankwar.map;

import com.tankwar.game.Bullet;
import com.tankwar.utilis.BulletsPool;
import com.tankwar.utilis.MyUtil;
import java.util.List;

import java.awt.*;

/**
 * 地圖元素塊
 */
public class MapTile {
    private static Image tileImg;
    public static int tileW = 60;
    public static int radius = tileW >>1;
    static {
        tileImg = MyUtil.createImage("res/image/walls.gif");
        if(tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
    }
    //圖片資源的左上角
    private int x,y;
    private boolean visible = true;

    public MapTile() {
    }

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
        if(tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
    }

    public void draw(Graphics g){
        if(!visible)
            return;

        if(tileW <= 0){
            tileW = tileImg.getWidth(null);
        }
        g.drawImage(tileImg,x,y,null);
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * 地圖塊和若干個子彈是否有碰撞
     * @param bullets
     * @return
     */
    public boolean isCollideBullet(List<Bullet> bullets){
        if(!visible)
            return false;
        for (Bullet bullet : bullets){
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            boolean collide = MyUtil.isCollide(this.x + radius, this.y + radius, radius, bulletX, bulletY);
            if(collide){
                //子彈的銷毀  TODO
                bullet.setVisible(false);
                BulletsPool.theReturn(bullet);
                return true;
            }
        }
        return false;
    }
}
