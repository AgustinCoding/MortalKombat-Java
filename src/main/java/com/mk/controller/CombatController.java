package com.mk.controller;

import com.mk.model.Combat;
import com.mk.model.Combat.CombatEvent;
import com.mk.model.Player;
import com.mk.view.CombatLogView;
import com.mk.view.EndCombatView;
import com.mk.utils.NarrationGenerator;

import javax.swing.*;
import java.util.ArrayList;

public class CombatController {
    private final Combat combat;
    private final CombatLogView view;
    private volatile boolean inProgress = false;  // flag que indica si está en curso
    private final Player p1;
    private final Player p2;

    public CombatController(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.combat = new Combat(p1, p2);
        this.view   = new CombatLogView("Combate: " + p1.getUsername() + " vs " + p2.getUsername());
        startCombat();
    }

    /** Inicia la simulacion en background */
    private void startCombat() {
        inProgress = true;
        new SwingWorker<Void, CombatEvent>() {
            @Override
            protected Void doInBackground() {
                combat.execute();
                for (CombatEvent ev : combat.getEvents()) {
                    publish(ev);
                    try { Thread.sleep(700); }
                    catch (InterruptedException ignored) {}
                }
                return null;
            }

            @Override
            protected void process(java.util.List<CombatEvent> chunks) {
                for (CombatEvent ev : chunks) {
                    view.appendEvent(ev.getDescription());
                }
            }

            @Override
            protected void done() {
                view.appendEvent("--- Combate finalizado ---");
                Player winner = combat.getWinnerPlayer();
                Player loser = (winner == p1) ? p2 : p1;
                inProgress = false;
                new EndCombatView(winner, loser);

            }
        }.execute();
    }

    /** Permite saber si aún quedan eventos por procesar */
    public boolean isInProgress() {
        return inProgress;
    }

    public ArrayList<CombatEvent> getEvents(){
        return this.combat.getEvents();
    }
}
