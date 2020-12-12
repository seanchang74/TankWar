package com.tankwar.utilis;

import com.tankwar.game.Bullet;

import java.util.ArrayList;
import java.util.List;

public class BulletsPool {
    public static int DEFAULT_POOL_SIZE = 200;
    public static int DEFAULT_MAX_SIZE = 300;
    //保存所有子彈的容器
    private static List<Bullet> pool = new ArrayList<>();
    //加載對象時創建200子彈到物件池內
    static {
        for (int i=0;i<DEFAULT_POOL_SIZE;i++){
            pool.add(new Bullet());
        }
    }

    public static Bullet get(){
        Bullet bullet = null;
        //當池內沒東西了
        if(pool.size()==0){
            bullet = new Bullet();
        }
        else{//池內非空，拿走第一個對象
            bullet = pool.remove(0);
        }
        System.out.println("剩餘:"+pool.size());
        return bullet;
    }
    //歸還子彈
    public static void theReturn(Bullet bullet){
        //子彈到達最大值，不再增加
        if(pool.size() >= DEFAULT_MAX_SIZE)return;
        pool.add(bullet);
        System.out.println("歸還:"+pool.size());
    }
}
