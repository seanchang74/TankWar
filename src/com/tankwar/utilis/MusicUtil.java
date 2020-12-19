package com.tankwar.utilis;
import javax.sound.sampled.*;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

public class MusicUtil {

    final private static class MyAudioClip implements AudioClip {
        final private Clip clip;

        private MyAudioClip(final Clip clip) {
            this.clip = clip;
        }

        //實現AudioClip接口
        @Override
        public void play() {
            clip.setFramePosition(0);
            clip.start();
        }

        @Override
        public void loop() {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        @Override
        public void stop() {
            clip.stop();
        }
    }

    /**
     * 透過文件獲取已加載音樂的AudioClip
     * @param audioFile 官方推薦wav和au格式
     * @return 已加載音樂的AudioClip
     */
    public static AudioClip createAudioClip(final File audioFile){
        try {
            final Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
            clip.open(ais);
            clip.setLoopPoints(0,-1);
            return new MyAudioClip(clip);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //關閉由上面生成的AudioClip釋放資源
    public static void closeAudioClip(final AudioClip audioClip) {
        if (audioClip != null && audioClip instanceof MyAudioClip) {
            try {
                ((MyAudioClip) audioClip).clip.close();
            } catch (Exception e) {
            }
        }
    }
}
