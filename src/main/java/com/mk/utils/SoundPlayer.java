package com.mk.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {

    // Guarda referencia al clip de audio que está sonando actualmente (si hay alguno)
    private static Clip currentClip = null;

    /**
     * Método para reproducir un archivo de audio en formato WAV desde la carpeta /audio/
     * Se ejecuta en un hilo separado para no bloquear la interfaz.
     * Controla errores si el archivo no existe o falla la reproducción.
     */
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

                currentClip = clip; // Guarda la referencia para controlar el audio

                clip.start();

                // Pausa el hilo hasta que termina la reproducción para liberar recursos
                Thread.sleep(clip.getMicrosecondLength() / 1000);

                currentClip = null; // Limpia la referencia cuando termina
            }catch(Exception e){
                System.err.println("Error al reproducir audio: " + e.getMessage());
                currentClip = null;
            }
        }).start();
    }

    /**
     * Método para detener la reproducción actual si hay un audio sonando.
     * Esto cierra el clip y limpia la referencia para liberar recursos.
     */
    public static void stop(){
        if(currentClip != null && currentClip.isRunning()){
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

    private SoundPlayer(){
        // Constructor privado para evitar instanciación (utilidad estática)
    }
}
