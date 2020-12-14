package com.tankwar.map;

import com.tankwar.game.Bullet;
import com.tankwar.game.GameFrame;
import com.tankwar.utilis.BulletsPool;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.MyUtil;
import java.util.List;

import java.awt.*;

/**
 * 地圖元素塊
 */
public class MapTile {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HOUSE = 1;
    public static final int TYPE_HOUSEBREAK = 2;
    public static final int TYPE_STEEL = 3;
    public static final int TYPE_STEEL2 = 4;
    public static final int TYPE_GRASS = 5;
    public static final int TYPE_WATER = 6;

    public static int tileW = 60;
    public static int radius = tileW >> 1;
    private int type = TYPE_NORMAL;

    private static Image[] tileImg;

    static {
        tileImg = new Image[7];
        tileImg[TYPE_NORMAL] = MyUtil.createImage("res/image/map/walls.gif");
        tileImg[TYPE_HOUSE] = MyUtil.createImage("res/image/map/home.png");
        tileImg[TYPE_HOUSEBREAK] = MyUtil.createImage("res/image/map/home_break.png");
        tileImg[TYPE_STEEL] = MyUtil.createImage("res/image/map/steel.gif");
        tileImg[TYPE_STEEL2] = MyUtil.createImage("res/image/map/steel2.gif");
        tileImg[TYPE_GRASS] = MyUtil.createImage("res/image/map/grass.png");
        tileImg[TYPE_WATER] = MyUtil.createImage("res/image/map/water.gif");

        if (tileW <= 0) {
            tileW = tileImg[TYPE_NORMAL].getWidth(null);
        }
    }

    //圖片資源的左上角
    private int x, y;
    private boolean visible = true;
//    private boolean basevisible = true;

    public MapTile() {
    }

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
        if (tileW <= 0) {
            tileW = tileImg[TYPE_NORMAL].getWidth(null);
        }
    }

    public void draw(Graphics g) {
        if (!visible)
            return;
        if (tileW <= 0) {
            tileW = tileImg[TYPE_NORMAL].getWidth(null);
        }

        g.drawImage(tileImg[type], x, y, null);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 地圖塊和若干個子彈是否有碰撞
     *
     * @param bullets
     * @return
     */
    public boolean isCollideBullet(List<Bullet> bullets) {
        if (!visible)
            return false;
        for (Bullet bullet : bullets) {
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            boolean collide = MyUtil.isCollide(this.x + radius, this.y + radius, radius, bulletX, bulletY);
            if (collide) {
                //子彈的銷毀  TODO
                bullet.setVisible(false);
                BulletsPool.theReturn(bullet);
                return true;
            }
        }
        return false;
    }

    public boolean isHouse(){
        return type == TYPE_HOUSE;
    }
}

