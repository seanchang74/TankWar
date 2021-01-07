package com.tankwar.map;

import com.tankwar.game.GameFrame;
import com.tankwar.game.LevelInfo;
import com.tankwar.tank.Tank;
import com.tankwar.utilis.Constant;
import com.tankwar.utilis.MapTilePool;
import com.tankwar.utilis.MyUtil;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 遊戲地圖類
 */
public class GameMap {

    public static final int MAP_X = Tank.RADIUS*2;
    public static final int MAP_Y = Tank.RADIUS*2 + GameFrame.titleBarH;
    public static final int MAP_WIDTH = Constant.RUN_FRAME_WIDTH - Tank.RADIUS*2;
    public static final int MAP_HEIGHT = Constant.FRAME_HEIGHT - Tank.RADIUS*6-GameFrame.titleBarH;

    //地圖元素塊的容器
    private List<MapTile> tiles = new ArrayList<>();

    //繪製主堡
    private TankHouse house;
    public GameMap() {

    }

    /**
     * 初始化地圖元素塊,level:第幾關
     */
    public void initMap(int level){
        tiles.clear();
        try{
            loadLevel(level);
        }catch (Exception e) {
            e.printStackTrace();
        }

        //初始化主堡
        house = new TankHouse();
        addHouse();
    }

    /**
     * 加載關卡訊息
     * @param level
     */
    private void loadLevel(int level) throws Exception{
        System.out.println("load level");
        //獲得關卡訊息類的唯一實例對象
        LevelInfo instance = LevelInfo.getInstance();
        instance.setLevel(level);

        Properties prop = new Properties();
        prop.load(new FileInputStream("level/lv_"+level));

        //將所有的地圖訊息都加載進來
        int enemyCount = Integer.parseInt(prop.getProperty("enemyCount"));
        instance.setEnemyCount(enemyCount);

        String methodName = prop.getProperty("method");
        int invokeCount = Integer.parseInt(prop.getProperty("invokeCount"));
        //把參數讀取到array中
        String[] params = new String[invokeCount];
        for (int i = 1; i <= invokeCount ; i++) {
            params[i-1] = prop.getProperty("param"+i);
        }
        //使用讀取到的參數，調用對應的方法
        //TODO
        invokeMethod(methodName,params);
    }

    //根據方法的名字和參數調用對應的方法
    private void invokeMethod(String methodName, String[] params) {
        for (String param : params) {
            //獲得每一行方法的參數，解析
            String[] split = param.split(",");
            int[] arr = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                arr[i] = Integer.parseInt(split[i]);
            }
            //塊之間的間隔是地圖塊的倍數
            final int DIS = MapTile.tileW;
            //TODO
            switch (methodName){
                case "addRow":
                    addRow(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,MAP_X+MAP_WIDTH-arr[2]*DIS,arr[3],arr[4]*DIS);
                    break;
                case "addCol":
                    addCol(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,MAP_Y+MAP_HEIGHT-arr[2]*DIS,arr[3],arr[4]*DIS);
                    break;
                case "addRect":
                    addRect(MAP_X+arr[0]*DIS,MAP_Y+arr[1]*DIS,MAP_X+MAP_WIDTH-arr[2]*DIS,MAP_Y+MAP_HEIGHT-arr[3]*DIS,arr[4],arr[5]*DIS);
                    break;
            }
        }
    }

    //將主堡周圍的地圖塊添加到地圖的容器中
    private void addHouse(){
        tiles.addAll(house.getTiles());
    }

    /**
     *某一個點確定的地圖塊。是否和tiles集合中的所有的塊 有重疊的部分
     * @param tiles
     * @param x
     * @param y
     * @return 有重疊返回 true，否則 false
     */
    private boolean isCollide(List<MapTile> tiles,int x,int y){
        for (MapTile tile : tiles) {
            int tileX = tile.getX();
            int tileY = tile.getY();
            if(Math.abs(tileX - x)<MapTile.tileW && Math.abs(tileY-y) < MapTile.tileW){
                return true;
            }
        }
        return false;
    }

    /**
     * 只對沒有遮擋效果的塊進行繪製
     * @param g
     */
    public void drawBk(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType() != MapTile.TYPE_GRASS)
                tile.draw(g);
        }
    }

    /**
     * 只繪製有遮擋效果的塊
     * @param g
     */
    public void drawCover(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType() == MapTile.TYPE_GRASS)
                tile.draw(g);
        }
    }

    public List<MapTile> getTiles() {
        return tiles;
    }

    /**
     * 將所有不可見的地圖塊從容器中移除
     */
    public void clearDestoryTile(){
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile = tiles.get(i);
            if(!tile.isVisible())
                tiles.remove(i);
        }
    }

    /**
     * 往地圖塊容器中添加一行指定類型的地圖塊
     * @param startx 添加地圖塊的起始x座標
     * @param starty 添加地圖塊的起始y座標
     * @param endx 添加地圖塊的結束x座標
     * @param type 地圖塊的類型
     * @param DIS 地圖塊之間中心點的間隔，如果是塊寬，代表是連續的，反之不連續
     */
    public void addRow(int startx,int starty,int endx,int type,final int DIS) {
        int count = (endx - startx) / (MapTile.tileW + DIS);
        for (int i = 0; i < count; i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startx + i * (MapTile.tileW+DIS));
            tile.setY(starty);
            tiles.add(tile);
        }
    }

    /**
     * 往地圖塊容器中添加一列指定類型的地圖塊
     * @param startx 該列的起始x座標
     * @param starty 該列的起始y座標
     * @param endy 該列的結束y座標
     * @param type 元素類型
     * @param DIS 相鄰元素中心點的間距
     */
    public void addCol(int startx,int starty,int endy,int type,final int DIS){
        int count = (endy - starty) / (MapTile.tileW + DIS);
        for (int i = 0; i < count; i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startx);
            tile.setY(starty+ i * (MapTile.tileW+DIS));
            tiles.add(tile);
        }
    }

    //對指定的矩形區域添加元素塊
    public void addRect(int startx,int starty,int endx,int endy,int type,final int DIS){
        int rows = (endy - starty) / (MapTile.tileW + DIS);
        for (int i = 0; i < rows; i++) {
            addRow(startx,starty+i*(MapTile.tileW + DIS),endx,type,DIS);
        }
    }
}
