package com.tankwar.utilis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;


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

    /**
     * 判斷點是否和方形相交
     * @param rectX  正方形中心點的X座標
     * @param rectY  正方形中心點的Y座標
     * @param radius  正方形的邊長的一半
     * @param pointX  點的X座標
     * @param pointY  點的Y座標
     * @return 如果在內部返回true else false
     */
    public static final boolean isCollide(int rectX,int rectY,int radius,int pointX,int pointY){
        //正方形中心點和 點的X，Y軸的距離
        int disX = Math.abs(rectX-pointX);
        int disY = Math.abs(rectY-pointY);
        if (disX < radius && disY < radius){
            return true;
        }
        else return false;
    }

    /**
     * 根據位置路徑加載圖片
     * @param path 圖片資源路徑
     * @return
     */
    public static final Image createImage(String path){
        return Toolkit.getDefaultToolkit().createImage(path);
    }

}
