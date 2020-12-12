package com.tankwar.game;
import com.tankwar.map.GameMap;
import com.tankwar.tank.EnemyTank;
import com.tankwar.tank.OurTank;
import com.tankwar.tank.Tank;
import com.tankwar.utilis.MyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.tankwar.utilis.Constant.*;


//遊戲主畫面
public class GameFrame extends Frame implements Runnable{
    //雙緩衝用圖片
    private BufferedImage bufImg = new BufferedImage(FRAME_WIDTH,FRAME_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
    //遊戲狀態
    public static int gameState;
    //菜單被選
    private int menuIndex;
    //最上方的高度
    public static int titleBarH;
    //宣告友方坦克
    private Tank Player_Tank_1;
    private Tank Player_Tank_2;
    //敵人坦克物件池
    private List<Tank> enemies = new ArrayList<>();
    //菜單指標
    private static Image select_image = MyUtil.createImage("res/image/selecttank.gif");

    //定義地圖相關的內容
    private GameMap gameMap;
    /**
     *  對視窗進行初始化
     */
    public  GameFrame(){
        initFrame();
        initEventListener();
        new Thread(this).start();
    }
    //icon
    ImageIcon icon = new ImageIcon("res/image/enemy3U.gif");
    /**
     * 遊戲狀態初始化
     */
    private void initGame(){
        gameState = STATE_MENU;
    }
    /**
     * 屬性初始化
     */
    private void initFrame(){
        //設定標題
        setTitle(GAME_TITLE);
        //設定圖示
        setIconImage(icon.getImage());
        //設定視窗大小
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        //不可縮放
        setResizable(false);
        //設定初始位置
        setLocationRelativeTo(null);
        //設定可見
        setVisible(true);
        //上方標題的高度
        titleBarH = getInsets().top;

    }

    /**
     * 負責繪製的內容，所有需要在畫面上顯示的內容，都透過此method調用
     * @param g1
     */
    public void update(Graphics g1){
        //先畫在一張圖上
        Graphics g = bufImg.getGraphics();
        g.setFont(FONT);
        switch (gameState){
            case STATE_MENU:
                drawMenu(g);
                break;
            case STATE_HELP:
                drawHelp(g);
                break;
            case STATE_RUN:
                drawRun(g);
                break;
            case STATE_OVER:
                drawOver(g);
                break;

        }
        //再畫到系統上
        g1.drawImage(bufImg,0,0,null);
    }

    /**
     * 繪製菜單的狀態內容
     * @param g
     */
    private void drawMenu(Graphics g){
        //繪製背景
        g.setColor(Color.BLACK);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

        final int STR_WIDTH = 120;
        int x = FRAME_WIDTH - STR_WIDTH >>1;
        int y = FRAME_HEIGHT /3*2;
        final int DIS = 35;
        g.setColor(Color.WHITE);
        for (int i = 0; i < MENUS.length; i++) {
            //紅色菜單
            if(i == menuIndex){
                g.setColor(Color.RED);
                //指標圖片
                g.drawImage(select_image,x-50,y-25+DIS * i,40,36,null);
            }
            else g.setColor(Color.WHITE);
            g.drawString(MENUS[i],x,y+DIS * i);
        }
    }
    private void drawHelp(Graphics g) {
    }
    private void drawRun(Graphics g) {
        //繪製背景
        g.setColor(Color.BLACK);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);

        //繪製地圖
        gameMap.draw(g);

        //繪製敵人坦克
        drawEnemies(g);
        //繪製坦克
        Player_Tank_1.draw(g,1);
        if(menuIndex ==1)
            Player_Tank_2.draw(g,2);
        //碰撞檢測
        bulletCollideTank();
        //繪製爆炸
        drawExplodes(g);
    }
    //繪製敵人坦克，若已經死亡從中移除
    private  void drawEnemies(Graphics g){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            if(enemy.isDie()){
                enemies.remove(i);
                i--;
                continue;
            }
            enemy.draw(g,0);
        }
        System.out.println("敵人數量"+enemies.size());
    }
    private void drawOver(Graphics g) {
    }


    /**
     * 初始化視窗監聽
     */
    private void initEventListener(){
        //監聽事件
        addWindowListener(new WindowAdapter() {
            //點擊關閉按鈕
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        //按鍵監聽
        addKeyListener(new KeyAdapter() {
            //按鍵被按
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (gameState){
                    case STATE_MENU:
                        keyPressedEventMenu(keyCode);
                        break;
                    case STATE_HELP:
                        keyPressedEventHelp(keyCode);
                        break;
                    case STATE_RUN:
                        keyPressedEventRun(keyCode);
                        break;
                    case STATE_OVER:
                        keyPressedEventOver(keyCode);
                        break;

                }
            }


            //按鍵鬆開
            @Override
            public void keyReleased(KeyEvent e) {
                //獲得按鍵的值
                int keyCode = e.getKeyCode();
                if ( gameState == STATE_RUN){
                    keyReleasedEventRun(keyCode);
                }
            }
        });
    }


    private void keyPressedEventMenu(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if(--menuIndex<0){
                    menuIndex = MENUS.length-1;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if(++menuIndex>MENUS.length-1){
                    menuIndex = 0;
                }
                break;
            case KeyEvent.VK_ENTER:{
                //TODO
                //開始新遊戲
                if(menuIndex == 0 || menuIndex == 1) {
                    newGame();
                    break;
                }
            }
        }
    }

    /**
     * 開始新遊戲
     */
    private void newGame() {
        gameState = STATE_RUN;
        //繪製坦克
        Player_Tank_1 = new OurTank(FRAME_WIDTH/3,FRAME_HEIGHT-Tank.RADIUS*2,Tank.DIR_UP);
        if(menuIndex==1)
        Player_Tank_2 = new OurTank(FRAME_WIDTH/3*2,FRAME_HEIGHT-Tank.RADIUS*2,Tank.DIR_UP);

        gameMap = new GameMap();

        //產生敵人
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if(enemies.size()< ENEMY_MAX_COUNT){
                        Tank enemy = EnemyTank.createEnemy();
                        enemies.add(enemy);
                    }
                    try {
                        Thread.sleep(ENEMY_BORN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void keyPressedEventHelp(int keyCode) {

    }

    private void keyPressedEventRun(int keyCode) {
        switch (keyCode){
            //玩家1
            case KeyEvent.VK_W:
                Player_Tank_1.setDir(Tank.DIR_UP);
                Player_Tank_1.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_S:
                Player_Tank_1.setDir(Tank.DIR_DOWN);
                Player_Tank_1.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_A:
                Player_Tank_1.setDir(Tank.DIR_LEFT);
                Player_Tank_1.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_D:
                Player_Tank_1.setDir(Tank.DIR_RIGHT);
                Player_Tank_1.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_SPACE:
                Player_Tank_1.fire();
                break;

            //玩家2
            case KeyEvent.VK_UP:
                Player_Tank_2.setDir(Tank.DIR_UP);
                Player_Tank_2.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_DOWN:
                Player_Tank_2.setDir(Tank.DIR_DOWN);
                Player_Tank_2.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_LEFT:
                Player_Tank_2.setDir(Tank.DIR_LEFT);
                Player_Tank_2.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_RIGHT:
                Player_Tank_2.setDir(Tank.DIR_RIGHT);
                Player_Tank_2.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_ENTER:
                Player_Tank_2.fire();
                break;
        }
    }
    //按鍵被鬆開的處理
    private void keyReleasedEventRun(int keyCode) {
        switch (keyCode){
            //玩家1
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                Player_Tank_1.setStatus(Tank.STATE_STAND);
                break;
            //玩家2
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                Player_Tank_2.setStatus(Tank.STATE_STAND);
                break;
        }
    }

    private void keyPressedEventOver(int keyCode) {

    }


    @Override
    public void run() {
        while (true){
            repaint();
            try {
                Thread.sleep(REPAINT_INT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //敵人子彈和玩家坦克碰撞
    //玩家坦克子彈和所有敵人碰撞
    private void bulletCollideTank(){
        //敵人坦克的子彈和玩家坦克的碰撞
        for(Tank enemy : enemies){
            enemy.collideBullets(Player_Tank_1.getBullets());
        }
        for (Tank enemy : enemies) {
            Player_Tank_1.collideBullets(enemy.getBullets());
        }
        if (menuIndex ==1){
            for (Tank enemy : enemies) {
                enemy.collideBullets(Player_Tank_2.getBullets());
            }
        }
        for (Tank enemy : enemies) {
            if(menuIndex == 1)
            Player_Tank_2.collideBullets(enemy.getBullets());
        }
    }

    private void drawExplodes(Graphics g){
        for(Tank enemy : enemies){
            enemy.drawExplode(g);
        }
        Player_Tank_1.drawExplode(g);
        if(menuIndex ==1)
        Player_Tank_2.drawExplode(g);
    }
}
