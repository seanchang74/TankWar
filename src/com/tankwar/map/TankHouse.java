package com.tankwar.map;

import com.tankwar.utilis.Constant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 玩家主堡
 */
public class TankHouse {
    //主堡的xy座標
    public static final int HOUSE_X = (Constant.RUN_FRAME_WIDTH-3*MapTile.tileW >> 1)+2;
    public static final int HOUSE_Y = Constant.FRAME_HEIGHT-2*MapTile.tileW;

    //一共六塊地圖塊
    private List<MapTile> tiles = new ArrayList<>();
    public TankHouse() {
        tiles.add(new MapTile(HOUSE_X,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X,HOUSE_Y+MapTile.tileW));
        tiles.add(new MapTile(HOUSE_X+MapTile.tileW,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X+MapTile.tileW*2,HOUSE_Y));
        tiles.add(new MapTile(HOUSE_X+MapTile.tileW*2,HOUSE_Y+MapTile.tileW));
        //設置主堡的地圖塊
        tiles.add(new MapTile(HOUSE_X+MapTile.tileW*5/4,HOUSE_Y+MapTile.tileW*5/4));
        tiles.get(tiles.size()-1).setType(MapTile.TYPE_HOUSE);
    }

    public List<MapTile> getTiles() {
        return tiles;
    }
}
