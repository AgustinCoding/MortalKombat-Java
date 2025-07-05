package com.mk.controller;

import com.mk.dao.InitDB;
import com.mk.utils.PasswordHasher;
import com.mk.view.*;



/*
 GameController maneja el flujo del programa asi que contiene el metodo main
 Flujo:
    - El usuario selecciona

 */
public class GameController {

    public static void main(String[] args) {

        InitDB.initAll();

        new AuthView();

    }

    private static void gameLoop(){

    }


}
