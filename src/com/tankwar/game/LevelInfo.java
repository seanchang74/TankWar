package com.tankwar.game;

/**
 * 用來管理當前關卡的訊息
 * 單例模式 : 如果一個類只有唯一的實例存在，那麼即可使用單例模式
 */
public class LevelInfo {
    //構造方法私有化
    private LevelInfo(){

    }
    //定義靜態本類類型的變量，來指向唯一的實例
    private static LevelInfo instance;
    //懶漢模式的單例，第一次使用該實例的時候創建唯一的實例
    //所有的訪問該類的唯一實例，都是通過該方法
    //該方法具有安全隱患，多線程的情況下可能會創建多個實例
    public static LevelInfo getInstance(){
        if(instance == null){
            //創建了唯一的實例
            instance = new LevelInfo();
        }
        return instance;
    }
    //關卡編號
    private int level;
    //關卡的敵人的數量
    private int enemyCount;
    //通關的要求的時長,-1意味著 不限時
    private int crossTime = -1;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public int getCrossTime() {
        return crossTime;
    }

    public void setCrossTime(int crossTime) {
        this.crossTime = crossTime;
    }
}
