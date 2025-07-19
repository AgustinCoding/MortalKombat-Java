package com.mk.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GeminiService {
    private static final String CONFIG_FILE = "config.properties";
    private static final String URL;
    private static final String API_KEY;


    static { // Este bloque se ejecuta solo una vez al llamar cualquier metodo de la clase por primera vez.
        // EL archivo config.properties debe existir localmente en carpeta "utils" ya que esta excluido en repositorio git
        // Claves: GEMINI_URL, GEMINI_API_KEY

        Properties prop = new Properties();
        try(FileInputStream fis = new FileInputStream(CONFIG_FILE)){
            prop.load(fis);
            URL = prop.getProperty("GEMINI_URL", "");
            API_KEY = prop.getProperty("GEMINI_API_KEY", "");

        }catch(IOException e){
            throw new RuntimeException("Error cargando configuracion: ", e);
        }
    }

    private GeminiService(){} // Evitar instancias

    public String getResponseTo(String message){
        String finalResponse = "";

        return finalResponse;
    }

    private static String jsonToMessage(String json){
        String message = "";

        return message;
    }

}
