package com.mk.controller;

import com.mk.dao.FighterDAO;
import com.mk.dao.InitDB;
import com.mk.model.*;
import com.mk.controller.FighterSelectionController;
import com.mk.view.FighterSelectionView;
import com.mk.view.LoginView;
import com.mk.utils.PasswordHasher;



/*
 GameController maneja el flujo del programa asi que contiene el metodo main
 Flujo:
    - El usuario selecciona

 */
public class GameController {

    public static void main(String[] args) {

        InitDB.initAll();

        System.out.println(PasswordHasher.hash("xxbetagu123"));

    }

    private static void gameLoop(){

    }


}
