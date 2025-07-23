package com.mk.controller;

import com.mk.dao.FighterDAO;
import com.mk.dao.InitDB;
import com.mk.dao.PlayerDAO;
import com.mk.model.Fighter;
import com.mk.model.Player;
import com.mk.model.Bot;
import com.mk.utils.NarrationGenerator;
import com.mk.utils.PasswordHasher;
import com.mk.view.*;
import com.mk.utils.SoundPlayer;

import javax.swing.*;
import java.text.MessageFormat;

public class GameController {

    private static Player player1 = null;
    private static Player player2 = null;
    private static Fighter[] fighters = FighterDAO.getAllObjects().toArray(new Fighter[0]);

    public static void main(String[] args) {

        InitDB.initAll();

        AuthViewController authViewController = new AuthViewController();

        while (!authViewController.isUserLogged()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

        CombatDetailsView combatDetailsView = new CombatDetailsView(player1, player2);
        SoundPlayer.play("fight.wav");

        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        combatDetailsView.dispose();

        CombatController combatController = new CombatController(player1, player2);
        SoundPlayer.play("combatmusic.wav");
        while(combatController.isInProgress()){
            continue;
        }
        SoundPlayer.stop();

        PlayerDAO.updatePlayer(player1);
        if(!vsCpu){
            PlayerDAO.updatePlayer(player2);
        }

        // Mostrar narración
        showNarration(combatController, player1, player2);
    }

    private static void showNarration(CombatController combatController, Player player1, Player player2) {
        // Crear la vista de narracion
        NarrationView narrationView = new NarrationView(
                player1.getUsername(),
                player2.getUsername(),
                player1.getSelectedFighter().getName(),
                player2.getSelectedFighter().getName()
        );

        // Generar narracion en hilo separado para optimizar la request
        new Thread(() -> {
            try {
                // Generar la narracion
                String narration = NarrationGenerator.generateNarration(
                        combatController.getEvents(),
                        player1.getUsername(),
                        player2.getUsername(),
                        player1.getSelectedFighter().getName(),
                        player2.getSelectedFighter().getName()
                );

                // Mostrar en la vista
                narrationView.showNarration(narration);

                System.out.println("Narracion generada exitosamente");

            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(narrationView,
                            "Error generando narracion: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    narrationView.dispose();
                });
            }
        }).start();
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
        JOptionPane.showMessageDialog(null, "Jugador 2 debe iniciar sesion para continuar");
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