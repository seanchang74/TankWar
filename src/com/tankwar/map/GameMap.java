package com.tankwar.map;

import com.tankwar.game.GameFrame;
import com.tankwar.tank.Tank;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.MapTilePool;
import com.tankwar.utilis.MyUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 遊戲地圖類
 */
public class GameMap {

    public static final int MAP_X = Tank.RADIUS*3;
    public static final int MAP_Y = Tank.RADIUS*3 + GameFrame.titleBarH;
    public static final int MAP_WIDTH = Constant.FRAME_WIDTH - Tank.RADIUS*6;
    public static final int MAP_HEIGHT = Constant.FRAME_HEIGHT - Tank.RADIUS*8-GameFrame.titleBarH;

    //地圖元素塊的容器
    private List<MapTile> tiles = new ArrayList<>();

    public GameMap() {
        initMap();
    }

    /**
     * 初始化地圖元素塊
     */
    private void initMap(){
        //隨機的得到一個地圖元素塊，添加到容器中
        final int COUNT = 20;
        for (int i = 0; i < COUNT; i++) {
            MapTile tile = MapTilePool.get();
            int x = MyUtil.getRandomNumber(MAP_X,MAP_X+MAP_WIDTH-MapTile.tileW);
            int y = MyUtil.getRandomNumber(MAP_Y,MAP_Y+MAP_HEIGHT-MapTile.tileW);
            //新生成的隨機塊和已經存在的塊有重疊的部分，則重新生成
            if(isCollide(tiles,x,y)){
                i--;
                continue;
            }
            tile.setX(x);
            tile.setY(y);
            tiles.add(tile);
        }
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

    public void draw(Graphics g){
        for (MapTile tile : tiles) {
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
}
