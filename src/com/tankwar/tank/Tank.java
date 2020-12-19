package com.tankwar.tank;


import com.tankwar.game.Bullet;
import com.tankwar.game.Explode;
import com.tankwar.game.GameFrame;
import com.tankwar.map.MapTile;
import com.tankwar.utilis.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.tankwar.utilis.Constant.STATE_RUN;
import static com.tankwar.utilis.PlayerHandling.*;


/**
 * 坦克相關class
 */
public abstract  class Tank {
    //四個方向
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_LEFT = 2;
    public static final int DIR_RIGHT = 3;
    //坦克半徑
    public static final int RADIUS = 29;
    //默認速度
    public static final int DEFAULT_SPEED = 6;
    //坦克狀態
    public static final int STATE_STAND = 0;
    public static final int STATE_MOVE = 1;
    public static final int STATE_DIE = 2;
    //坦克名字
    private String name;
    //敵人坦克血量
    public static final int ENEMY_MIN_HP = 1;
    public static final int ENEMY_MAX_HP = 3;
    //坦克攻擊力
    public static final int ATK_MIN = 1;
    public static final int ATK_MAX = 1;

    //座標
    private int x,y;
    //血量
    private int hp;
    //攻擊力
    private int atk;
    private int enemy_hp = MyUtil.getRandomNumber(ENEMY_MIN_HP,ENEMY_MAX_HP+1);
    //速度
    private int speed = DEFAULT_SPEED;
    //方向
    private int dir;
    //現在狀態
    private int status = STATE_STAND;
    //是否無敵
    private boolean shield = false;
    //隨機顏色
    private Color color;
    //是否為敵人
    private boolean isEnemy = false;
    //是否可見
    private boolean visible = true;


    //砲彈容器
    private List<Bullet> bullets = new ArrayList();
    //爆炸效果容器
    private List<Explode> explodes = new ArrayList();

    //給物件池用
    public Tank(){
        initTank();
    };
    public Tank(int x,int y,int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
        initTank();
    }

    public void initTank(){
        color = MyUtil.getRandomColor();
        //坦克攻擊力
        atk = MyUtil.getRandomNumber(ATK_MIN,ATK_MAX+1);
        //初始化坦克血量
        if(!isEnemy)
        PlayerHandling.initTank_hp();
        //坦克名字
        name = MyUtil.getRandomName();
    }


    public void draw(Graphics g,int player){
        if(!visible)return;
        /**
         * 每楨都要執行
         */
        if(player == 1)hp = getHp1();
        else if(player == 2)hp = getHp2();
        logic();
        drawTank(g,player);
        drawBullets(g,player);
        if(!isEnemy)
        drawName(g);

    }


    private void drawName(Graphics g) {
        g.setColor(color);
        g.setFont(Constant.NAME_FONT);
        g.drawString(name,x - RADIUS,y-35);
    }

    /**
     * 繪製坦克
     * @param g
     */
    public abstract void drawTank(Graphics g,int player);

    //坦克邏輯處理
    private void logic(){
        switch (status){
            case STATE_STAND:
                break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;
        }
    }

    private int oldX = -1, oldY = -1;
    //坦克移動
    private void move(){
        oldX = x;
        oldY = y;
        switch (dir){
            case DIR_UP:
                y -= speed;
                if(y < RADIUS + GameFrame.titleBarH){
                    y = RADIUS + GameFrame.titleBarH;
                }
                break;
            case DIR_DOWN:
                y += speed;
                if(y > Constant.FRAME_HEIGHT - RADIUS){
                    y = Constant.FRAME_HEIGHT - RADIUS;
                }
                break;
            case DIR_LEFT:
                x -= speed;
                if(x < RADIUS){
                    x = RADIUS;
                }
                break;
            case DIR_RIGHT:
                x += speed;
                if(x > Constant.RUN_FRAME_WIDTH - RADIUS){
                    x = Constant.RUN_FRAME_WIDTH - RADIUS;
                }
                break;
        }
    }

    public static int getDirUp(){
        return DIR_UP;
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getAtk(){
        return atk;
    }
    public void setAtk(int atk){

    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List getBullets() {
        return bullets;
    }

    public void setBullets(List bullets) {
        this.bullets = bullets;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public int getEnemy_hp() {
        return enemy_hp;
    }

    public void setEnemy_hp(int enemy_hp) {
        this.enemy_hp = enemy_hp;
    }

    //上一次開火的時間
    private long fireTime;
    //子彈發射的最小的間隔
    public static final int FIRE_INTERVAL = 500;
    /**
     * 坦克開火
     */
    public void fire(){
        if(System.currentTimeMillis() - fireTime > FIRE_INTERVAL){
            //從子彈池拿子彈
            Bullet bullet = BulletsPool.get();
            //子彈屬性設定
            bullet.setX(x);
            bullet.setY(y);
            bullet.setDir(dir);
            bullet.setAtk(atk);
            bullet.setVisible(true);
            bullets.add(bullet);

            //發射子彈之後，紀錄本次發射的時間
            fireTime = System.currentTimeMillis();
        }
   }

    /**
     * 繪製坦克的子彈
     * @param g
     */
    private void drawBullets(Graphics g,int player){
        for (Bullet bullet: bullets){
            bullet.draw(g,player);
        }
        for (int i=0;i<bullets.size();i++){
            Bullet bullet = bullets.get(i);
            if(!bullet.isVisible()){
                Bullet remove = bullets.remove(i);
                i--;
                BulletsPool.theReturn(remove);
            }
        }
    }
    //reset時子彈清空
    public void  bulletsReturn(){
        for (Bullet bullet : bullets) {
            BulletsPool.theReturn(bullet);
        }
    }
    //坦克和子彈碰撞判斷
    public void collideBullets(List<Bullet> bullets ,int player,Graphics g){
        //對所有子彈和坦克進行碰撞檢測
        for(Bullet bullet : bullets){
            int bulletX = bullet.getX();
            int bulletY = bullet.getY();
            //碰撞發生
            if(MyUtil.isCollide(this.x, this.y,RADIUS,bulletX,bulletY)){
                //子彈消失
                bullet.setVisible(false);
                if(!shield){
                    //坦克受到傷害
                    hurt(bullet,player);
                    //添加爆炸效果
                    addExplode(x+RADIUS*2,y+RADIUS*2);
                    //坦克重生
                    if(!isEnemy && hp!=1)
                        reborn(g,player);
                }
            }
        }
    }

    private void addExplode(int x,int y){//添加爆炸效果，以及當前被擊中坦克的座標
            Explode explode = ExplodesPool.get();
            explode.setX(x);
            explode.setY(y);
            explode.setVisible(true);
            explode.setIndex(0);
            explodes.add(explode);
    }

    //坦克受傷
    private void hurt(Bullet bullet,int player){
        int atk = bullet.getAtk();

        if(!isEnemy){
            if(player_get_hurt(player,atk) == Constant.PLAYER_BOTH_DIE)
                gameover();
        }
        if(isEnemy){
            enemy_hp -=atk;
            if(enemy_hp <= 0){
                enemy_hp =0;
                gameover();
                SideBar.setScore(SideBar.getScore()+200);
            }
        }
    }
    //坦克重生
    private void reborn(Graphics g,int player){
        born(g,player);
        if(player == 1){
            this.setX(Constant.PLAYER1_X);
            this.setY(Constant.PLAYER1_Y);
        }
        else if(player == 2){
            this.setX(Constant.PLAYER2_X);
            this.setY(Constant.PLAYER2_Y);
        }
    }
    //出生圖片繪製
    private static Image[] born_Image;
    static {
        born_Image = new Image[4];
        born_Image[0] = MyUtil.createImage("res/image/material/born1.gif");
        born_Image[1] = MyUtil.createImage("res/image/material/born2.gif");
        born_Image[2] = MyUtil.createImage("res/image/material/born3.gif");
        born_Image[3] = MyUtil.createImage("res/image/material/born4.gif");
    }
    //TODO
    public void born(Graphics g,int player){
        int sleep = 10000;
        this.setVisible(false);
        Thread b =  new Thread(){
            public void run() {
                try {
                    for (int i = 0; i < 80000; i++) {
                        System.out.println("I"+i);
                        if(i%sleep<(sleep/4))
                            g.drawImage(born_Image[0],x-RADIUS,y-RADIUS,RADIUS*2,RADIUS*2,null);
                        else if(i%sleep<(sleep/2))
                            g.drawImage(born_Image[1], x-RADIUS,y-RADIUS,RADIUS*2,RADIUS*2,null);
                        else if(i%sleep<(sleep/4*3))
                            g.drawImage(born_Image[2], x-RADIUS,y-RADIUS,RADIUS*2,RADIUS*2,null);
                        else
                            g.drawImage(born_Image[3], x-RADIUS,y-RADIUS,RADIUS*2,RADIUS*2,null);
                        if(i == 79999){
                            setVisible(true);
                            if(!isEnemy)
                            drawshield(g,player);
                        }
                        //只在遊戲進行中執行
                        if(GameFrame.getGameState() != STATE_RUN){
                            break;
                        }
                    }
                } catch (Exception e) { }
            }
        };
        b.start();
    }
    private static Image shield1 = MyUtil.createImage("res/image/material/shield1.png");
    private static Image shield2 = MyUtil.createImage("res/image/material/shield2.png");
    //護盾繪製
    private void drawshield(Graphics g, int player){
        int sleep = 3000;
        new Thread(){
            public void run() {
                try {
                    //呈現閃爍效果
                    for (int i = 0; i < 80000; i++) {
                        shield = true;
                        if(i%sleep<(sleep/2))
                            g.drawImage(shield1,x-RADIUS,y-RADIUS,65,65,null);
                        else
                            g.drawImage(shield2,x-RADIUS,y-RADIUS,65,65,null);
                        //護盾消失
                        if(i==79999)shield = false;
                        //只在遊戲進行中執行
                        if(GameFrame.getGameState() != STATE_RUN){
                            break;
                        }
                    }
                } catch (Exception e) { }
            }
        }.start();
    }

    public void gameover(){
        //敵人死了
        if(isEnemy){
            //歸還物件池
            EnemyTanksPool.theReturn(this);
        }
        else{
            //gameover
            delaySecondsToOver(3000);
            
        }
    }
    //敵人坦克死亡判斷
    public boolean isDie(){
        return enemy_hp <=0;
    }


    /**
     * 繪製當前坦克上所有的爆炸效果
     * @param g
     */
    public void drawExplode(Graphics g){
            for (Explode explode : explodes) {
                explode.draw(g);
            }
            //將不可見的爆炸效果刪除
            for (int i = 0; i < explodes.size(); i++) {
                Explode explode = explodes.get(i);
                if (!explode.isVisible()) {
                    Explode remove = explodes.remove(i);
                    ExplodesPool.back(remove);
                    i--;
                }
            }
    }

    //坦克的子彈和地圖所有的塊的碰撞
    public void bulletsCollideMapTiles(List<MapTile> tiles){
        for (MapTile tile : tiles) {
            if(tile.isCollideBullet(bullets)){
                //添加爆炸效果
//                addExplode(tile.getX()+MapTile.radius*3,tile.getY()+MapTile.tileW*2);
                //鐵塊無法被消除
                if(tile.getType() == MapTile.TYPE_STEELS)
                    continue;
                //設置地圖塊銷毀
                tile.setVisible(false);
                //歸還對象池
                MapTilePool.theReturn(tile);
                //當主堡被擊毀後，1.5秒鐘之後切換到遊戲結束的畫面
                if(tile.isHouse()){

                    delaySecondsToOver(1500);
                }
            }
        }
    }


    /**
     * 延遲若干毫秒切換到遊戲結束
     * @param millisSecond
     */
    private void delaySecondsToOver(int millisSecond){
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(millisSecond);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                GameFrame.setGameState(Constant.STATE_OVER);
            }
        }.start();
    }

    /**
     * 一個地圖塊和當前的坦克碰撞的方法
     * 從tile中提取8個點，來判斷8個點是否有任何一個點和當前坦克發生碰撞
     * 點的順序從左上角的點開始，順時針偵測
     */
     public boolean isCollideTile(List<MapTile> tiles){
        for (MapTile tile : tiles) {
            //如果塊不可見，或是為草地塊時就不進行碰撞檢測
            if(!tile.isVisible() || tile.getType() == MapTile.TYPE_GRASS)
                continue;
            //點-1 左上角
            int tileX = tile.getX();
            int tileY = tile.getY();
            boolean collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
            //點-2 中上點
            tileX += MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
            //點-3 右上角
            tileX += MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
            //點-4 中右點
            tileY += MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
            //點-5 右下角
            tileY += MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
            //點-6 中下點
            tileX -= MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
            //點-7 左下角
            tileX -= MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
            //點-8 中左點
            tileY -= MapTile.radius;
            collide = MyUtil.isCollide(x, y, RADIUS, tileX, tileY);
            //如果碰上了就直接返回，否則繼續判斷下一個點
            if(collide){
                return true;
            }
        }
        return false;
    }

    /**
     * 坦克回退的方法
     */
    public void back() {
        x = oldX;
        y = oldY;
    }

}
