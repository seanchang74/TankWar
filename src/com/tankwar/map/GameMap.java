package com.tankwar.map;

import com.tankwar.game.GameFrame;
import com.tankwar.tank.Tank;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.MapTilePool;
import com.tankwar.utilis.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 遊戲地圖類
 */
public class GameMap {

    public static final int MAP_X = Tank.RADIUS*3;
    public static final int MAP_Y = Tank.RADIUS*3 + GameFrame.titleBarH;
    public static final int MAP_WIDTH = Constant.RUN_FRAME_WIDTH - Tank.RADIUS*6;
    public static final int MAP_HEIGHT = Constant.FRAME_HEIGHT - Tank.RADIUS*8-GameFrame.titleBarH;

    //地圖元素塊的容器
    private List<MapTile> tiles = new ArrayList<>();

    //繪製主堡
    private TankHouse house;
    public GameMap() {
        initMap();
    }

    /**
     * 初始化地圖元素塊
     */
    private void initMap(){
        //隨機的得到一個地圖元素塊，添加到容器中
        final int COUNT = 20;

        //三行的地圖
        addRow(MAP_X,MAP_Y,MAP_X+MAP_WIDTH,MapTile.TYPE_NORMAL,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*2,MAP_X+MAP_WIDTH,MapTile.TYPE_GRASS,0);
        addRow(MAP_X,MAP_Y+MapTile.tileW*4,MAP_X+MAP_WIDTH,MapTile.TYPE_WATER,MapTile.tileW+20);
        //初始化主堡
        house = new TankHouse();
        addHouse();
    }

    //將主堡周圍的地圖塊添加到地圖的容器中
    private void addHouse(){
        tiles.addAll(house.getTiles());
    }

    /**
     *某一個點確定的地圖塊。是否和tiles集合中的所有的塊 有重疊的部分
     * @param tiles
     * @param x
     * @param y
     * @return 有重疊返回 true，否則 false
     */
    private boolean isCollide(List<MapTile> tiles,int x,int y){
        for (MapTile tile : tiles) {
            int tileX = tile.getX();
            int tileY = tile.getY();
            if(Math.abs(tileX - x)<MapTile.tileW && Math.abs(tileY-y) < MapTile.tileW){
                return true;
            }
        }
        return false;
    }

    /**
     * 只對沒有遮擋效果的塊進行繪製
     * @param g
     */
    public void drawBk(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType() != MapTile.TYPE_GRASS)
                tile.draw(g);
        }
    }

    /**
     * 只繪製有遮擋效果的塊
     * @param g
     */
    public void drawCover(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType() == MapTile.TYPE_GRASS)
                tile.draw(g);
        }
    }

    public List<MapTile> getTiles() {
        return tiles;
    }

    /**
     * 將所有不可見的地圖塊從容器中移除
     */
    public void clearDestoryTile(){
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile = tiles.get(i);
            if(!tile.isVisible())
                tiles.remove(i);
        }
    }

    /**
     * 往地圖塊容器中添加一行指定類型的地圖塊
     * @param startx 添加地圖塊的起始x座標
     * @param starty 添加地圖塊的起始y座標
     * @param endx 添加地圖塊的結束x座標
     * @param type 地圖塊的類型
     * @param DIS 地圖塊之間中心點的間隔，如果是塊寬，代表是連續的，反之不連續
     */
    public void addRow(int startx,int starty,int endx,int type,final int DIS) {
        int count = (endx - startx) / (MapTile.tileW + DIS);
        for (int i = 0; i < count; i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startx + i * (MapTile.tileW+DIS));
            tile.setY(starty);
            tiles.add(tile);
        }
    }
}
