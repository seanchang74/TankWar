package com.tankwar.game;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.tankwar.utilis.Lang.*;


//遊戲主畫面
public class GameFrame extends Frame implements Runnable{
    //遊戲狀態
    public static int gameState;
    //菜單被選
    private int menuIndex;
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

    }

    /**
     * 負責繪製的內容，所有需要在畫面上顯示的內容，都透過此method調用
     * @param g
     */
    public void update(Graphics g){
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
                        keyEventMenu(keyCode);
                        break;
                    case STATE_HELP:
                        keyEventHelp(keyCode);
                        break;
                    case STATE_RUN:
                        keyEventRun(keyCode);
                        break;
                    case STATE_OVER:
                        keyEventOver(keyCode);
                        break;

                }
            }


            //按鍵鬆開
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
    }
    private void keyEventMenu(int keyCode) {
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
        }
    }

    private void keyEventHelp(int keyCode) {

    }

    private void keyEventRun(int keyCode) {

    }

    private void keyEventOver(int keyCode) {

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
