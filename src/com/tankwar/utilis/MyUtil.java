package com.tankwar.utilis;

import java.awt.*;

public class MyUtil {
    private MyUtil(){}

    /**
     * 隨機數
     * @param min 最小值
     * @param max 最大值
     * @return 隨機數
     */
    public static final int getRandomNumber(int min,int max){
        return (int)(Math.random()*(max-min)+min);
    }

    /**
     * 隨機顏色
     * @return color
     */
    public static final Color getRandomColor(){
        int red = getRandomNumber(0,256);
        int green = getRandomNumber(0,256);
        int blue = getRandomNumber(0,256);
        return new Color(red,green,blue);
    }
}
