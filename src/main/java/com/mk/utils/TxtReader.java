package com.mk.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TxtReader {

    public static ArrayList<String> linesToList(String filename){
        ArrayList<String> lines = new ArrayList<>();
        try{
            InputStream is = TxtReader.class.getClassLoader().getResourceAsStream(filename); // Get txt as Stream object
            if(is == null){
                throw new IllegalStateException("No se encontro el archivo a leer");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = br.readLine()) != null){
                lines.add(line);
            }

            br.close();
            return lines;


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
