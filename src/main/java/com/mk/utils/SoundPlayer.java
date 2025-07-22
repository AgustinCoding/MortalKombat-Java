package com.mk.utils;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

public class SoundPlayer {

    public static void play(String file){
        new Thread(() -> {
           try{
               InputStream audioSrc = SoundPlayer.class.getResourceAsStream("/audio/" + file);
               if ( audioSrc == null){
                   System.err.println("El archivo de audio no existe");
                   return;
               }
               InputStream bufferedIn = new BufferedInputStream(audioSrc);
               AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
               Clip clip = AudioSystem.getClip();
               clip.open(audioStream);
               clip.start();
               Thread.sleep(clip.getMicrosecondLength() / 1000);
           }catch(Exception e){
               System.err.println("Error al reproducir audio: " + e.getMessage());
           }
        }).start();
    }

    private SoundPlayer(){
        //evita instancias
    }
}
