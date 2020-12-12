package com.tankwar.utilis;

import com.tankwar.tank.EnemyTank;
import com.tankwar.tank.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * 敵人坦克物件池
 */
public class EnemyTanksPool {
    public static int DEFAULT_POOL_SIZE = 15;
    public static int DEFAULT_MAX_SIZE = 20;
    //保存所有子彈的容器
    private static List<Tank> pool = new ArrayList<>();
    //加載對象時創建15坦克到物件池內
    static {
        for (int i=0;i<DEFAULT_POOL_SIZE;i++){
            pool.add(new EnemyTank() {
            });
        }
    }

    public static Tank get(){
        Tank tank = null;
        //當池內沒東西了
        if(pool.size()==0){
            tank = new EnemyTank();
        }
        else{//池內非空，拿走第一個對象
            tank = pool.remove(0);
        }
        return tank;
    }
    //歸還坦克
    public static void theReturn(Tank tank){
        //坦克到達最大值，不再增加
        if(pool.size() >= DEFAULT_MAX_SIZE)return;
        pool.add(tank);
    }
}