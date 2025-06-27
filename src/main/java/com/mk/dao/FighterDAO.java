package com.mk.dao;

import com.mk.dao.DBConnection;
import com.mk.model.Fighter;

import java.util.ArrayList;



public class FighterDAO {
    // CLASE CON METODOS ESTATICOS
    // getByName
    // getByID
    // getAllString -> String[][]
    // getAllObjects -> ArrayList<Fighter>
    // setNewFighter
    // updateFighter
    // deleteFighter

    public static ArrayList<String[]> getAllString(){
        ArrayList<String[]> stringArray = new ArrayList<>();
        // Acceder a la base de datos
        // Por cada fila armar una String[] con los atributos de x personaje
        // Almacenar la String[] en createdList
        // retornar lista

        return stringArray;
    }


    public static ArrayList<Fighter> getAllObjects(){
        ArrayList<Fighter> objectArray = new ArrayList<>();
        // Acceder a la base de datos
        // Por cada fila armar un objeto Fighter con los atributos de x personaje
        // Almacenarlo en objectArray
        // retornar lista
        return objectArray;

    }


}
