package com.tankwar.game;
import com.tankwar.map.GameMap;
import com.tankwar.tank.EnemyTank;
import com.tankwar.tank.OurTank;
import com.tankwar.utilis.MusicUtil;
import com.tankwar.tank.Tank;
import com.tankwar.utilis.MyUtil;
import com.tankwar.utilis.PlayerHandling;
import com.tankwar.utilis.SideBar;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.tankwar.utilis.Constant.*;


//遊戲主畫面
public class GameFrame extends Frame implements Runnable{
    //雙緩衝用圖片
    private BufferedImage bufImg = new BufferedImage(FRAME_WIDTH,FRAME_HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
    //遊戲狀態
    private static int gameState;
    //菜單被選
    private static int menuIndex;
    //結束菜單
    private int overIndex;
    //最上方的高度
    public static int titleBarH;
    //設定遊戲音樂
    static AudioClip startmusic = MusicUtil.createAudioClip(new File("res/audio/start.wav"));
    //宣告友方坦克
    private static Tank Player_Tank_1;
    private static Tank Player_Tank_2;
    //敵人坦克物件池
    private static List<Tank> enemies = new ArrayList<>();
    //用來記錄本關卡產生了多少個敵人
    private static int bornEnemyCount;
    //用來記錄消滅多少敵人
    public static int killEnemyCount;
    //菜單指標
    private static Image select_image = MyUtil.createImage("res/image/material/selecttank.gif");
    //icon
    private static ImageIcon icon = new ImageIcon("res/image/tank/enemies/enemy3U.gif");
    //結束遊戲圖片
    private static Image overImg = MyUtil.createImage("res/image/material/over.png");
    //側邊攔
    private static SideBar sideBar;
    //定義地圖相關的內容
    private static GameMap gameMap;
    /**
     *  對視窗進行初始化
     */
    public  GameFrame(){
        initFrame();
        initGame();
        initEventListener();
        new Thread(this).start();
    }
    /**
     * 進入下一關的方法
     */
    public static void nextLevel() {
        startGame(LevelInfo.getInstance().getLevel()+1);
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
        System.out.println(titleBarH);

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
            case STATE_WIN:
                drawWin(g);
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
        controllerPressedEventMenu();
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
        g.fillRect(0,0,RUN_FRAME_WIDTH,FRAME_HEIGHT);

        //繪製側邊攔
        sideBar.drawBackground(g);
        sideBar.draw(g,1);
        if (menuIndex == 1)
            sideBar.draw(g,2);
        //繪製地圖的碰撞層
        gameMap.drawBk(g);
        //繪製敵人坦克
        drawEnemies(g);
        //繪製坦克
        if (Player_Tank_1!=null)
            Player_Tank_1.draw(g,1);
        if(menuIndex ==1)
            if(Player_Tank_2!=null)
            Player_Tank_2.draw(g,2);
        //搖桿控制
        controllerPressedEventRun();
//        controllerReleasedEventRun();
        //繪製地圖的遮擋層
        gameMap.drawCover(g);
        //碰撞檢測
        bulletCollideTank(g);
        //繪製爆炸
        drawExplodes(g);
        //子彈和所有地圖塊的碰撞
        bulletAndTankCollideMapTile();
        //檢測玩家死亡
        deletePlayer();
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

    /**
     * 繪製遊戲勝利的介面
     * @param g
     */
    private void drawWin(Graphics g){
        drawOver(g);
        //通關文字
        g.setColor(Color.white);
        g.setFont(FONT);
        g.drawString("遊戲通關 !",FRAME_WIDTH/2-60,50+titleBarH);
    }
    /**
     * 繪製遊戲結束
     * @param g
     */
    private void drawOver(Graphics g) {
        controllerPressedEventOver();
        int imgW = overImg.getWidth(null);
        int imgH = overImg.getHeight(null);
        int imgX = RUN_FRAME_WIDTH - imgW >>1;
        int imgY = FRAME_HEIGHT - imgH >>1;
        final int DIS = 250;
        g.setColor(Color.BLACK);
        g.fillRect(0,0,RUN_FRAME_WIDTH,FRAME_HEIGHT);
        g.drawImage(overImg, imgX, imgY, null);
        sideBar.drawBackground(g);
        sideBar.draw(g,1);
        if(menuIndex == 1)sideBar.draw(g,2);
        //提供選單
        for (int i = 0; i < OVER_STR.length; i++) {
            //紅色菜單
            if(i == overIndex){
                g.setColor(Color.RED);
            }
            else g.setColor(Color.WHITE);
            g.drawString(OVER_STR[i], imgX+40+DIS*i,imgY+imgH+20 );
        }
        System.out.println(imgX+" "+imgY);
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
                    case STATE_WIN:
                        keyPressedEventWin(keyCode);
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
        //搖桿監聽
        ReadAllEvents();
    }
    //TODO
    private int controller = 0;
    private String button = "";
    private boolean buttonState = false;
    private void ReadAllEvents() {
        //find controller
        Controller[] all_controllers =  ControllerEnvironment
                .getDefaultEnvironment().getControllers();
        /* Get the available controllers */
        List<Controller> controllers = new ArrayList<Controller>();
        if (all_controllers.length == 0) {
            System.out.println("找不到控制器");
            System.exit(0);
        }

        for (int i = 0; i < all_controllers.length; i++) {
            /* Remember to poll each one */
            all_controllers[i].poll();
            if(all_controllers[i].toString().equals("Controller (XBOX 360 For Windows)")||all_controllers[i].toString().equals("Wireless Gamepad")){
                controllers.add(all_controllers[i]);
                System.out.println("找到了");
            }
        }
        for (int i = 0; i < controllers.toArray().length; i++) {
            System.out.println(controllers.get(i));
        }
        new Thread() {
            @Override
            public void run() {
                while (true){


                    for (int i = 0; i < controllers.toArray().length; i++) {
                        /* Remember to poll each one */
                        controllers.get(i).poll();
                        /* Get the controllers event queue */
                        EventQueue queue = controllers.get(i).getEventQueue();


                        /* Create an event object for the underlying plugin to populate */
                        Event event = new Event();

                        /* For each object in the queue */
                        while (queue.getNextEvent(event)) {

                            StringBuffer buffer = new StringBuffer(controllers.get(i)
                                    .getName());
                            buffer.append(", ");
                            Component comp = event.getComponent();
                            Component.Identifier identifier = comp.getIdentifier();
                            buffer.append(identifier.toString()).append(" changed to ");
                            float value = event.getValue();

                            /*
                             * Check the type of the component and display an
                             * appropriate value
                             */
                            if (comp.isAnalog()) {
                                buffer.append(value);
                            } else {
                                System.out.println(value);
                                //被壓下
                                if (value == 1.0f) {
                                    buffer.append("On");
                                    buttonState = true;
                                }
                                //被放開
                                else {
                                    buffer.append("Off");
                                    buttonState = false;
                                }
                            }
                            System.out.println(buffer.toString());
                            if(i == 0){
                                setController(1);
                            }
                            else if(i == 1){
                                setController(2);
                            }
                            setButton(identifier.toString());
                            if(button == "pov"){
                                System.out.println("pov");
                                setButton(String.valueOf(event.getValue()));
                            }
                            System.out.println("controller: "+controller);
                            System.out.println("button: "+button);
                        }
                    }

                    /*
                     * Sleep for 20 milliseconds, in here only so the example doesn't
                     * thrash the system.
                     */
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        }.start();

    }
    private void controllerPressedEventMenu() {
        button = getButton();
        switch (button){
            case PAD_UP:
                if(--menuIndex<0){
                    menuIndex = MENUS.length-1;
                }
                break;
            case PAD_DOWN:
                if(++menuIndex>MENUS.length-1){
                    menuIndex = 0;
                }
                break;
            case BUTTON_A:{
                //開始新遊戲
                if(menuIndex == 0 || menuIndex == 1) {
                    startGame(1);
                    break;
                }
                else if(menuIndex == MENUS.length-1){
                    System.exit(0);
                }
            }
        }try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //遊戲通關的按鍵處理
    private void keyPressedEventWin(int keyCode) {
        keyPressedEventOver(keyCode);
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
                //開始新遊戲
                if(menuIndex == 0 || menuIndex == 1) {
                    startGame(1);
                    break;
                }
                else if(menuIndex == MENUS.length-1){
                    System.exit(0);
                }
            }
        }
    }

    /**
     * 開始新遊戲
     * 並加載level關卡訊息
     */
    private static void startGame(int level) {
        startmusic.play();
        enemies.clear();
        if(gameMap == null)
            gameMap = new GameMap();
        gameMap.initMap(level);
        bornEnemyCount = 0;
        killEnemyCount = 0;
        gameState = STATE_RUN;
        //繪製坦克
        Player_Tank_1 = new OurTank(PLAYER1_X,PLAYER1_Y,Tank.DIR_UP);
        Tank.tanks.add(Player_Tank_1);
        if(menuIndex==1) {
            System.out.println("new 2");
            Player_Tank_2 = new OurTank(PLAYER2_X, PLAYER2_Y, Tank.DIR_UP);
            Tank.tanks.add(Player_Tank_2);
        }
        sideBar = new SideBar();
        //產生敵人
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if(LevelInfo.getInstance().getEnemyCount() > bornEnemyCount&&
                            enemies.size() < ENEMY_MAX_COUNT){
                        Tank enemy = EnemyTank.createEnemy();
                        enemies.add(enemy);
                        bornEnemyCount ++;
                    }
                    try {
                        Thread.sleep(ENEMY_BORN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //只在遊戲進行中創建敵人
                    if(gameState != STATE_RUN){
                        break;
                    }
                }
            }
        }.start();
    }

    private void keyPressedEventHelp(int keyCode) {

    }

    private void controllerPressedEventRun() {
        if(Player_Tank_1!=null && controller == 1){
            switch (button) {
                //玩家1
                case PAD_UP:
                    Player_Tank_1.setDir(Tank.DIR_UP);
                    Player_Tank_1.setStatus(Tank.STATE_MOVE);
                    break;
                case PAD_DOWN:
                    Player_Tank_1.setDir(Tank.DIR_DOWN);
                    Player_Tank_1.setStatus(Tank.STATE_MOVE);
                    break;
                case PAD_RIGHT:
                    Player_Tank_1.setDir(Tank.DIR_LEFT);
                    Player_Tank_1.setStatus(Tank.STATE_MOVE);
                    break;
                case PAD_LEFT:
                    Player_Tank_1.setDir(Tank.DIR_RIGHT);
                    Player_Tank_1.setStatus(Tank.STATE_MOVE);
                    break;
                case BUTTON_A:
                    Player_Tank_1.fire();
                    break;
            }
        }
        if(Player_Tank_2!=null && controller==2){
            switch (button) {
                //玩家2
                case PAD_UP:
                    Player_Tank_2.setDir(Tank.DIR_UP);
                    Player_Tank_2.setStatus(Tank.STATE_MOVE);
                    break;
                case PAD_DOWN:
                    Player_Tank_2.setDir(Tank.DIR_DOWN);
                    Player_Tank_2.setStatus(Tank.STATE_MOVE);
                    break;
                case PAD_RIGHT:
                    Player_Tank_2.setDir(Tank.DIR_LEFT);
                    Player_Tank_2.setStatus(Tank.STATE_MOVE);
                    break;
                case PAD_LEFT:
                    Player_Tank_2.setDir(Tank.DIR_RIGHT);
                    Player_Tank_2.setStatus(Tank.STATE_MOVE);
                    break;
                case BUTTON_A:
                    Player_Tank_2.fire();
                    break;
            }
        }
    }
    private void keyPressedEventRun(int keyCode) {
        if(Player_Tank_1!=null){
            switch (keyCode) {
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
            }
        }
        if(Player_Tank_2!=null){
            switch (keyCode) {
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
    }
    //手把被鬆開
    private void controllerReleasedEventRun() {
        if (!buttonState)
        {
            switch (button){
                //玩家1
                case PAD_UP:
                case PAD_DOWN:
                case PAD_LEFT:
                case  PAD_RIGHT:
                    if(Player_Tank_1!=null && controller == 1)
                        Player_Tank_1.setStatus(Tank.STATE_STAND);
                    if(Player_Tank_2!=null && controller == 2)
                        Player_Tank_2.setStatus(Tank.STATE_STAND);
                    break;
            }
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
                if(Player_Tank_1!=null)
                Player_Tank_1.setStatus(Tank.STATE_STAND);
                if(Player_Tank_2!=null)
                Player_Tank_2.setStatus(Tank.STATE_STAND);
                break;
        }
    }
    private void controllerPressedEventOver() {
        switch (button){
            case PAD_RIGHT:
                if(--overIndex<0){
                    overIndex=0;
                }
                break;
            case PAD_LEFT:
                if(++overIndex>1){
                    overIndex=1;
                }
                break;
            case BUTTON_A:{
                //結束遊戲
                if(overIndex == 0) {
                    System.exit(0);
                }
                //回到標題
                else if(overIndex == 1){
                    gameState =  STATE_MENU;
                    //重製遊戲
                    resetGame();
                }
                break;
            }
        }
    }
    /**
     * 遊戲結束按鍵處理
     * @param keyCode
     */
    private void keyPressedEventOver(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if(--overIndex<0){
                    overIndex=0;
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if(++overIndex>1){
                    overIndex=1;
                }
                break;
            case KeyEvent.VK_ENTER:{
                //結束遊戲
                if(overIndex == 0) {
                    System.exit(0);
                }
                //回到標題
                else if(overIndex == 1){
                    gameState =  STATE_MENU;
                    //重製遊戲
                    resetGame();
                }
                break;
            }
        }
    }
    //玩家死亡刪除玩家
    public void deletePlayer(){
        if(Player_Tank_1 != null) {
            if (PlayerHandling.getHp1() == 0) {
                if (Player_Tank_2 == null) Player_Tank_1.die();
                Player_Tank_1 = null;
                System.out.println("p1 die");
            }
        }
        if(Player_Tank_2 != null) {
            if (PlayerHandling.getHp2() == 0) {
                if (Player_Tank_1 == null) Player_Tank_2.die();
                Player_Tank_2 = null;
                System.out.println("p2 die ");
            }
        }
    }
    //重置遊戲狀態
    private void resetGame(){
        killEnemyCount = 0;
        menuIndex = 0;
        //將子彈還回對象池
        if(Player_Tank_1!=null)
        Player_Tank_1.bulletsReturn();
        if(Player_Tank_2!=null)
        Player_Tank_2.bulletsReturn();
        //刪除自己坦克
        if(Player_Tank_1!=null)
        Player_Tank_1 = null;
        if(Player_Tank_2!=null)
        Player_Tank_2 = null;
        //清空敵人物件池和子彈
        for (Tank enemy : enemies) {
            enemy.bulletsReturn();
        }
        enemies.clear();
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
    private void bulletCollideTank(Graphics g){
        //敵人坦克的子彈和玩家坦克的碰撞
        if (Player_Tank_1!=null){
            for (Tank enemy : enemies) {
                enemy.collideBullets(Player_Tank_1.getBullets(), 0,g);
            }
        }
        if (Player_Tank_1!=null){
            for (Tank enemy : enemies) {
                Player_Tank_1.collideBullets(enemy.getBullets(), 1,g);
            }
        }
        if (Player_Tank_2!=null){
            for (Tank enemy : enemies) {
                enemy.collideBullets(Player_Tank_2.getBullets(),0,g);
            }
        }
        if(Player_Tank_2!=null){
            for (Tank enemy : enemies) {
                Player_Tank_2.collideBullets(enemy.getBullets(), 2,g);
            }
            //坦克和坦克的碰撞
//        if (Player_Tank_1.isCollideTank(Tank.getTanks())) {
//            Player_Tank_1.back();
//        }
//        if (menuIndex == 1)
//            if (Player_Tank_2.isCollideTank(Tank.getTanks())) {
//                Player_Tank_2.back();
//            }
//        for (Tank enemy : enemies) {
//            if (enemy.isCollideTank(Tank.getTanks())) {
//                enemy.back();
//            }
//        }
        }
    }

    //所有的子彈和地圖塊的碰撞
    private void bulletAndTankCollideMapTile(){
        //自己的坦克的子彈和地圖塊的碰撞
        if(Player_Tank_1!=null)
            Player_Tank_1.bulletsCollideMapTiles(gameMap.getTiles());
        if(Player_Tank_2!=null)
            Player_Tank_2.bulletsCollideMapTiles(gameMap.getTiles());
        for (Tank enemy : enemies) {
            enemy.bulletsCollideMapTiles(gameMap.getTiles());
        }
        //坦克和地圖的碰撞
        if(Player_Tank_1!=null)
            if(Player_Tank_1.isCollideTile(gameMap.getTiles())){
                Player_Tank_1.back();
            }
        if(Player_Tank_2!=null)
            if(Player_Tank_2.isCollideTile(gameMap.getTiles())){
                Player_Tank_2.back();
            }
        for (Tank enemy : enemies) {
            if(enemy.isCollideTile(gameMap.getTiles())){
                enemy.back();
            }
        }
        //清理所有的被銷毀的地圖塊
        gameMap.clearDestoryTile();
    }

    private void drawExplodes(Graphics g){
        for(Tank enemy : enemies){
            enemy.drawExplode(g);
        }
        if(Player_Tank_1!=null)
        Player_Tank_1.drawExplode(g);
        if(Player_Tank_2!=null)
        Player_Tank_2.drawExplode(g);
    }

    public int getController() {
        return controller;
    }

    public void setController(int controller) {
        this.controller = controller;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public static int getGameState() {
        return gameState;
    }

    public static void setGameState(int gameState) {
        GameFrame.gameState = gameState;
    }
    /**
     * 是否為最後一關
     * @return
     */
    public static boolean isLastLevel(){
        //當前關卡和總關卡一致
        int currlevel = LevelInfo.getInstance().getLevel();
        int levelCount = GameInfo.getLevelCount();
        return currlevel == levelCount;
    }

    /**
     * 判斷是否過關了
     * @return
     */
    public static boolean isCrossLevel(){
        //消滅敵人的數量和關卡的敵人數量一致
        return killEnemyCount == LevelInfo.getInstance().getEnemyCount();
    }
}
