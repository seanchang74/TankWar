package com.tankwar.utilis;

import com.tankwar.game.GameFrame;
import com.tankwar.tank.Tank;

import java.awt.*;

import static com.tankwar.utilis.Constant.*;

public class SideBar {

    private static int score = 0;
    private static final int SIDEBAR_X = Constant.RUN_FRAME_WIDTH;


    public SideBar(){};
    //sidebar相關繪製
    public void draw(Graphics g,int player){
        drawScore(g);//血條繪製
        drawLife(g,player);
    }

    public void drawBackground(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(RUN_FRAME_WIDTH,0,FRAME_WIDTH*1/5,FRAME_HEIGHT);
    }
    /**
     * 繪製分數(暫) TODO
     * @param g
     */
    private void drawScore(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString("Score "+score,SIDEBAR_X,Constant.FRAME_HEIGHT-20);
    }

    //玩家圖片p1
    private static Image player1icon = MyUtil.createImage("res/image/material/player1.png");
    //玩家圖片p2
    private static Image player2icon = MyUtil.createImage("res/image/material/player2.png");
    //玩家生命圖片
    private static Image life = MyUtil.createImage("res/image/material/life.png");
    /**
     * 畫生命
     * @param g
     */
    public static void drawLife(Graphics g,int player){
        int hp1 = PlayerHandling.getHp1();
        int hp2 = PlayerHandling.getHp2();
        final int DIS = 55;
        final int BORDER = 5;
        final int THEY = Constant.FRAME_HEIGHT * 5/7;
        g.setColor(Color.BLACK);
        g.setFont(Constant.FONT);
        //繪製1P
        if(player == 1) {
            g.drawImage(player1icon, SIDEBAR_X+BORDER, 0 + THEY, player1icon.getWidth(null) * 3 / 2, player1icon.getHeight(null) * 3 / 2, null);
            g.drawImage(life, SIDEBAR_X+BORDER, 0 + THEY + 10 + player1icon.getHeight(null), life.getWidth(null) * 3 / 2, life.getHeight(null) * 3 / 2, null);
            g.drawString(String.valueOf(hp1),SIDEBAR_X+5+life.getWidth(null)*3/2+BORDER,0+THEY+23+player1icon.getHeight(null)*3/2);
        }//繪製2P
        else if(player == 2){
            g.drawImage(player2icon, SIDEBAR_X+DIS, 0 + THEY, player1icon.getWidth(null) * 3 / 2, player1icon.getHeight(null) * 3 / 2, null);
            g.drawImage(life, SIDEBAR_X+DIS, 0 + THEY + 10 + player1icon.getHeight(null), life.getWidth(null) * 3 / 2, life.getHeight(null) * 3 / 2, null);
            g.drawString(String.valueOf(hp2),SIDEBAR_X+5+life.getWidth(null)*3/2+DIS,0+THEY+23+player1icon.getHeight(null)*3/2);
        }
    }


    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        SideBar.score = score;
    }
}
