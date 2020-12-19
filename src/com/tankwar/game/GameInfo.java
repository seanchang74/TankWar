package com.tankwar.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 遊戲相關的訊息
 */
public class GameInfo {
    //從配置文件中讀取
    //關卡數量
    private static int levelCount;

    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("level/gameinfo"));
            levelCount = Integer.parseInt(prop.getProperty("levelCount"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLevelCount() {
        return levelCount;
    }
}
