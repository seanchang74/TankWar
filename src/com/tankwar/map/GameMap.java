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
    public static final int MAP_WIDTH = Constant.RUN_FRAME_WIDTH - Tank.RADIUS*6;
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
            tile.setX(x);
            tile.setY(y);
            tiles.add(tile);
        }

    }

    public void draw(Graphics g){
        for (MapTile tile : tiles) {
            tile.draw(g);
        }
    }
}
