package com.mk.controller;

import com.mk.dao.FighterDAO;
import com.mk.dao.InitDB;
import com.mk.model.Fighter;
import com.mk.model.Player;
import com.mk.model.Bot;
import com.mk.utils.PasswordHasher;
import com.mk.view.*;
import com.mk.utils.SoundPlayer;

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

        while (!authViewController.isUserLogged()) {
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

        new FighterSelectionController(player1);

        while(player1.getSelectedFighter() == null){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        if(vsCpu){
            assignBot();
        }else {
            authenticateSecondPlayer();
        }

        new CombatDetailsView(player1, player2);
        SoundPlayer.play("fight.wav");




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
        JOptionPane.showMessageDialog(null, "Juagador 2 debe iniciar sesion para continuar");
        while(true){
            if(!authViewController.isUserLogged()){
                continue;
            }else{
                player2 = authViewController.getLoggedPlayerInstance();
                assert( !( player2.getUsername().equals( player1.getUsername() ) ) );
                new FighterSelectionController(player2);
                while(player2.getSelectedFighter() == null){
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }

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
