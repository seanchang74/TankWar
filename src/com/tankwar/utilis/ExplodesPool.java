package com.tankwar.utilis;

import com.tankwar.game.Explode;

import java.util.ArrayList;
import java.util.List;

public class ExplodesPool {

    public static final int DEFAULT_POOL_SIZE = 10;
    public static final int POOL_MAX_SIZE = 20;

    //用於保存所有爆炸效果的容器
    private static List<Explode> pool  = new ArrayList<>();
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Explode());
        }
    }

    /**
     * 從池塘中獲取一個爆炸對象
     * @return
     */
    public static Explode get(){
        Explode explode = null;
        if(pool.size() == 0){
            explode = new Explode();
        }
        else{
            explode = pool.remove(0);
        }
        return explode;
    }

    public static void back(Explode explode){
        if(pool.size() >= POOL_MAX_SIZE){
            return;
        }
        pool.add(explode);
    }
}
