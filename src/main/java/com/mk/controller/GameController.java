package com.mk.controller;

import com.mk.dao.FighterDAO;
import com.mk.dao.InitDB;
import com.mk.model.Fighter;
import com.mk.model.Player;
import com.mk.model.Bot;
import com.mk.utils.PasswordHasher;
import com.mk.view.*;

import javax.swing.*;
import java.text.MessageFormat;


/*
 GameController maneja el flujo del programa asi que contiene el metodo main
 Flujo:
    - El usuario selecciona

 */
public class GameController {

    private static Player player1 = null;
    private static Player player2 = null;
    private static Fighter[] fighters = FighterDAO.getAllObjects().toArray(new Fighter[0]);

    public static void main(String[] args) {


        InitDB.initAll();


        AuthViewController authViewController = new AuthViewController();

        while (!authViewController.isIsUserLogged()) {
            try {
                Thread.sleep(100); // esperar 100 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ya logueado
        player1 = authViewController.getLoggedPlayerInstance();
        assert(player1 != null);



        boolean vsCpu = vsCPU();
        authViewController = null;

        if(vsCpu){
            assignBot();
        }else {
            authenticateSecondPlayer();
        }



    }

    private static boolean vsCPU(){
        String[] options = {"CPU", "PLAYER VS PLAYER"};
        int selected = -1;

        while(selected == -1){
            selected = JOptionPane.showOptionDialog(
                    null,
                    "Desea jugar vs cpu o otro jugador?",
                    "Seleccion de modo de juego",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if(selected == -1){
                JOptionPane.showMessageDialog(null, "Debes elegir una opcion", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return selected == 0;
    }


    private static void assignBot(){
        player2 = Bot.getRandomBot(player1.getExp());

    }


    private static void authenticateSecondPlayer(){
        AuthViewController authViewController = new AuthViewController();
        while(true){
            if(!authViewController.isIsUserLogged()){
                continue;
            }else{
                player2 = authViewController.getLoggedPlayerInstance();
                assert( !( player2.getUsername().equals( player1.getUsername() ) ) );
                break;
            }
        }
    }


    public Player[] getPlayers(){
        return new Player[]{player1, player2};
    }

    public void startGame(){

    }



}
