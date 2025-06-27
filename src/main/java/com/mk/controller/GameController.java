package com.mk.controller;

import com.mk.model.*;
import com.mk.controller.FighterSelectionController;
import com.mk.view.FighterSelectionView;
import com.mk.view.LoginView;



/*
 GameController maneja el flujo del programa asi que contiene el metodo main
 Flujo:
    - El usuario selecciona

 */
public class GameController {

    public static void main(String[] args) {

        Game game = new Game();
        Fighter[] fighters = game.getFighters();

        new LoginView();
        new FighterSelectionController(fighters);

    }

    private static void gameLoop(){

    }


}
