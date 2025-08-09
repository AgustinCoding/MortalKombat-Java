package com.mk.controller;

import com.mk.dao.EventDAO;
import com.mk.dao.FighterDAO;
import com.mk.dao.InitDB;
import com.mk.dao.PlayerDAO;
import com.mk.model.Fighter;
import com.mk.model.Player;
import com.mk.model.Bot;
import com.mk.utils.NarrationGenerator;
import com.mk.view.*;
import com.mk.utils.SoundPlayer;

import javax.swing.*;

/**
 * GameController es el punto de entrada principal de la aplicacion.
 * Orquesta todo el flujo del juego: autenticacion, seleccion de luchadores,
 * configuracion del modo de juego, ejecucion del combate y muestra de narracion final.
 */
public class GameController {

    private static Player player1 = null;
    private static Player player2 = null;
    // Se cargan todos los luchadores disponibles desde la base de datos
    private static Fighter[] fighters;

    public static void main(String[] args) {

        // Inicializa base de datos, DAOs y datos iniciales
        InitDB.initAll();

        // Controla el login del primer jugador
        AuthViewController authViewController = new AuthViewController();
        fighters = FighterDAO.getAllObjects().toArray(new Fighter[0]);
        while (!authViewController.isUserLogged()) {
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        player1 = authViewController.getLoggedPlayerInstance();
        assert(player1 != null);

        // Pregunta si se juega contra CPU o contra otro jugador
        boolean vsCpu = vsCPU();
        authViewController = null;

        // Permite al jugador 1 elegir luchador
        new FighterSelectionController(player1);
        while(player1.getSelectedFighter() == null){
            try{ Thread.sleep(1000); } catch(InterruptedException e){ e.printStackTrace(); }
        }

        // Segun el modo de juego, asigna CPU o autentica jugador 2
        if(vsCpu){
            assignBot();
        } else {
            authenticateSecondPlayer();
        }

        // Muestra detalles previos al combate y reproduce sonido
        CombatDetailsView combatDetailsView = new CombatDetailsView(player1, player2);
        SoundPlayer.play("fight.wav");
        try{ Thread.sleep(3000); } catch(InterruptedException e){ e.printStackTrace(); }
        combatDetailsView.dispose();

        // Inicia el combate
        CombatController combatController = new CombatController(player1, player2);
        SoundPlayer.play("combatmusic.wav");
        while(combatController.isInProgress()){ continue; }
        SoundPlayer.stop();

        // Actualiza datos de jugadores y guarda eventos del combate
        PlayerDAO.updatePlayer(player1);
        if(!vsCpu){
            PlayerDAO.updatePlayer(player2);
        }
        EventDAO.addEvent(combatController.getCombat());

        // Muestra narracion final del combate
        showNarration(combatController, player1, player2);
    }

    /**
     * Genera y muestra la narracion del combate en una ventana independiente.
     * Se ejecuta en un hilo separado para no bloquear la interfaz.
     */
    private static void showNarration(CombatController combatController, Player player1, Player player2) {
        NarrationView narrationView = new NarrationView(
                player1.getUsername(),
                player2.getUsername(),
                player1.getSelectedFighter().getName(),
                player2.getSelectedFighter().getName()
        );

        new Thread(() -> {
            try {
                String narration = NarrationGenerator.generateNarration(
                        combatController.getEvents(),
                        player1.getUsername(),
                        player2.getUsername(),
                        player1.getSelectedFighter().getName(),
                        player2.getSelectedFighter().getName()
                );
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

    /**
     * Pregunta al usuario si desea jugar contra CPU o contra otro jugador.
     * Obliga a elegir una opcion para continuar.
     */
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

    /**
     * Asigna un bot aleatorio como oponente segun la experiencia del jugador 1.
     */
    private static void assignBot(){
        player2 = Bot.getRandomBot(player1.getExp());
    }

    /**
     * Autentica al segundo jugador y permite que seleccione su luchador.
     * Garantiza que no sea el mismo usuario que el jugador 1.
     */
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
                    try{ Thread.sleep(1000); } catch(InterruptedException e){ e.printStackTrace(); }
                }
                break;
            }
        }
    }

    public Player[] getPlayers(){
        return new Player[]{player1, player2};
    }

}
