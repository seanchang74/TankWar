package com.tankwar.utilis;

import com.tankwar.game.GameFrame;
import com.tankwar.tank.Tank;

import java.awt.*;

public class SideBar {

    private static int score = 0;
    private static final int SIDEBAR_X = Constant.RUN_FRAME_WIDTH;


    public SideBar(){};
    //sidebar相關繪製
    public void draw(Graphics g){
        drawScore(g);
    }

    /**
     * 繪製分數(暫) TODO
     * @param g
     */
    private void drawScore(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString("Score "+score,SIDEBAR_X,Constant.FRAME_HEIGHT-20);
    }


    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        SideBar.score = score;
    }
}
