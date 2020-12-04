package com.tankwar.game;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

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
    //宣告坦克
    private Tank my_tank;
    /**
     *  對視窗進行初始化
     */
    public  GameFrame(){
        initFrame();
        initEventListener();
        new Thread(this).start();
    }
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
        final int DIS = 30;
        g.setColor(Color.WHITE);
        for (int i = 0; i < MENUS.length; i++) {
            if(i == menuIndex)g.setColor(Color.RED);
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
        //繪製坦克
        my_tank.draw(g);
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
                newGame();
                break;
            }
        }
    }

    /**
     * 開始新遊戲
     */
    private void newGame() {
        gameState = STATE_RUN;
        //繪製坦克
        my_tank = new Tank(300,400,Tank.DIR_DOWN);
    }

    private void keyPressedEventHelp(int keyCode) {

    }

    private void keyPressedEventRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_W:
                my_tank.setDir(Tank.DIR_UP);
                my_tank.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_S:
                my_tank.setDir(Tank.DIR_DOWN);
                my_tank.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_A:
                my_tank.setDir(Tank.DIR_LEFT);
                my_tank.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_D:
                my_tank.setDir(Tank.DIR_RIGHT);
                my_tank.setStatus(Tank.STATE_MOVE);
                break;
            case KeyEvent.VK_SPACE:
                my_tank.fire();
                break;
        }
    }
    //按鍵被鬆開的處理
    private void keyReleasedEventRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                my_tank.setStatus(Tank.STATE_STAND);
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
}
