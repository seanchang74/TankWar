package com.tankwar.utilis;

import com.tankwar.map.MapTile;
import com.tankwar.tank.EnemyTank;
import com.tankwar.tank.Tank;

import java.util.ArrayList;
import java.util.List;

public class MapTilePool {

    public static int DEFAULT_POOL_SIZE = 50;
    public static int DEFAULT_MAX_SIZE = 70;
    //保存所有子彈的容器
    private static List<MapTile> pool = new ArrayList<>();
    //加載對象時創建15坦克到物件池內
    static {
        for (int i=0;i<DEFAULT_POOL_SIZE;i++){
            pool.add(new MapTile());
        }
    }

    public static MapTile get(){
        MapTile tile = null;
        //當池內沒東西了
        if(pool.size()==0){
            tile = new MapTile();
        }
        else{//池內非空，拿走第一個對象
            tile = pool.remove(0);
        }
        return tile;
    }
    //歸還坦克
    public static void theReturn(MapTile tile){
        //坦克到達最大值，不再增加
        if(pool.size() >= DEFAULT_MAX_SIZE)return;
        pool.add(tile);
    }
}
