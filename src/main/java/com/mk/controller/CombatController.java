package com.mk.controller;

import com.mk.model.Combat;
import com.mk.model.Combat.CombatEvent;
import com.mk.model.Player;
import com.mk.view.CombatLogView;
import com.mk.view.EndCombatView;
import com.mk.utils.SoundPlayer;
import com.mk.utils.NarrationGenerator;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Controlador que gestiona la logica de un combate entre dos jugadores.
 * Su funcion es:
 *  - Crear y configurar la simulacion del combate.
 *  - Mostrar en tiempo real los eventos generados.
 *  - Determinar el ganador y mostrar la pantalla de finalizacion.
 *  - Mantener un estado que indique si el combate sigue en curso.
 */
public class CombatController {

    // Objeto que contiene toda la logica interna del combate
    private final Combat combat;

    // Vista que muestra los eventos del combate en pantalla
    private final CombatLogView view;

    // Bandera que indica si el combate esta en curso
    // volatile garantiza que cambios en este valor sean visibles entre hilos
    private volatile boolean inProgress = false;

    // Referencias a los jugadores que participan
    private final Player p1;
    private final Player p2;

    /**
     * Constructor del controlador.
     * Recibe a los dos jugadores y prepara la simulacion y la vista.
     */
    public CombatController(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        // Crea el objeto combate con los dos jugadores
        this.combat = new Combat(p1, p2);

        // Crea la vista del log de combate con un titulo descriptivo
        this.view = new CombatLogView("Combate: " + p1.getUsername() + " vs " + p2.getUsername());

        // Inicia la simulacion inmediatamente
        startCombat();
    }

    /**
     * Metodo que lanza la simulacion del combate en segundo plano.
     * Usa SwingWorker para no bloquear el hilo de la interfaz.
     */
    private void startCombat() {
        inProgress = true; // Marca que el combate ha comenzado

        // SwingWorker ejecuta en segundo plano y permite actualizar la UI de forma segura
        new SwingWorker<Void, CombatEvent>() {

            @Override
            protected Void doInBackground() {
                // Ejecuta toda la logica del combate (internamente genera los eventos)
                combat.execute();

                // Recorre los eventos generados y los publica uno por uno
                for (CombatEvent ev : combat.getEvents()) {
                    publish(ev); // envia el evento al metodo process()

                    // Pausa breve para simular ritmo en la narracion
                    try { Thread.sleep(700); }
                    catch (InterruptedException ignored) {}
                }
                return null;
            }

            @Override
            protected void process(java.util.List<CombatEvent> chunks) {
                // Este metodo se ejecuta en el hilo de la UI
                // Recibe los eventos publicados y los muestra en pantalla
                for (CombatEvent ev : chunks) {
                    view.appendEvent(ev.getDescription());
                }
            }

            @Override
            protected void done() {
                // Cuando termina doInBackground, se llama a este metodo en la UI
                view.appendEvent("--- Combate finalizado ---");

                // Determina el ganador y el perdedor
                Player winner = combat.getWinnerPlayer();
                Player loser = (winner == p1) ? p2 : p1;

                // Marca que ya no esta en curso
                inProgress = false;

                // Muestra la vista de fin de combate
                new EndCombatView(winner, loser);
            }
        }.execute(); // Inicia la tarea en segundo plano
    }

    /**
     * Indica si el combate sigue en curso.
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * Devuelve la lista de eventos generados en el combate.
     */
    public ArrayList<CombatEvent> getEvents(){
        return this.combat.getEvents();
    }

    /**
     * Devuelve el objeto Combat asociado a este controlador.
     * Esto puede ser usado para obtener mas datos del resultado.
     */
    public Combat getCombat(){
        return this.combat;
    }
}
