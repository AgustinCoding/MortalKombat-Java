package com.mk.utils;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

public class SoundPlayer {

    private static Clip currentClip = null;

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

                // Guarda referencia al clip actual
                currentClip = clip;

                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);

                // Limpia la referencia cuando termina
                currentClip = null;
            }catch(Exception e){
                System.err.println("Error al reproducir audio: " + e.getMessage());
                currentClip = null;
            }
        }).start();
    }

    public static void stop(){
        if(currentClip != null && currentClip.isRunning()){
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

    private SoundPlayer(){
        //evita instancias
    }
}