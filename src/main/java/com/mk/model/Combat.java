package com.mk.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa un combate por turnos hasta que uno de los jugadores alcanza 0 HP.
 * Registra cada acción como CombatEvent para luego narrar.
 */
public class Combat {
    public static class CombatEvent {
        private final String description;
        private final long timestamp;

        public CombatEvent(String description) {
            this.description = description;
            this.timestamp   = System.currentTimeMillis();
        }
        public String getDescription() { return description; }
        @Override public String toString() { return description; }
    }

    private final Player p1;
    private final Player p2;
    private int hp1 = 100;
    private int hp2 = 100;
    private final ArrayList<CombatEvent> events = new ArrayList<>();
    private final Random random = new Random();
    private final LocalDateTime datetime;

    // Coeficientes
    private static final double ALPHA = 0.25;
    private static final double BETA  = 1.0;

    public Combat(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.datetime = LocalDateTime.now();
        log("¡COMBATE INICIADO: " + p1.getUsername() + " vs " + p2.getUsername() + "!");
    }

    // Ejecuta el combate hasta que uno llegue a 0 HP
    public void execute() {
        boolean turnoP1 = true;
        while (hp1 > 0 && hp2 > 0) {
            if (turnoP1) {
                simulateTurn(p1, p2, true);
            } else {
                simulateTurn(p2, p1, false);
            }
            turnoP1 = !turnoP1;
        }
        // Registrar resultado final
        if (hp1 <= 0 && hp2 <= 0) {
            log("¡EMPATE! Ambos han caído.");
        } else if (hp2 <= 0) {
            log("¡VICTORIA de " + p1.getUsername() + "!");
            p1.setExp(p1.getExp() + 2);
            log(p1.getUsername() + " gana +2 XP (total: " + p1.getExp() + ")");
        } else {
            log("¡VICTORIA de " + p2.getUsername() + "!");
            p2.setExp(p2.getExp() + 2);
            log(p2.getUsername() + " gana +2 XP (total: " + p2.getExp() + ")");
        }
    }

    /**
     * Simula un turno de ataque:
     * @param atk  jugador atacante
     * @param def  jugador defensor
     * @param isFirstPlayer true si atk == p1, false si atk == p2
     */
    private void simulateTurn(Player atk, Player def, boolean isFirstPlayer) {
        // Recalcular fuerzas
        double Fj = random.nextInt(10) + 1
                + atk.getSelectedFighter().getBaseStrength()
                + ALPHA * atk.getExp();
        double Fo = random.nextInt(10) + 1
                + def.getSelectedFighter().getBaseStrength();

        // Decidir acción
        int roll = random.nextInt(100);
        if (roll < 20) {
            log(atk.getUsername() + " falló el ataque. (-0 HP)");
        }
        else if (roll < 30) {
            log(atk.getUsername()
                    + " atacó pero " + def.getUsername() + " esquivó (–0 HP)");
        }
        else if (roll < 80) {
            int dmg = (int)(Fj * 0.35);
            applyDamage(def, dmg, isFirstPlayer);
        }
        else {
            int dmg = (int)(Fj * 0.75);
            applyCritical(def, dmg, isFirstPlayer);
        }
    }

    private void applyDamage(Player def, int dmg, boolean attackerIsP1) {
        if (def == p1) {
            hp1 = Math.max(hp1 - dmg, 0);
        } else {
            hp2 = Math.max(hp2 - dmg, 0);
        }
        log(def.getUsername() + " recibió –" + dmg + " HP"
                + " (HP " + status(def) + ")");
    }

    private void applyCritical(Player def, int dmg, boolean attackerIsP1) {
        if (def == p1) {
            hp1 = Math.max(hp1 - dmg, 0);
        } else {
            hp2 = Math.max(hp2 - dmg, 0);
        }
        log("¡CRITICO! " + def.getUsername() + " recibió –" + dmg + " HP"
                + " (HP " + status(def) + ")");
    }

    private String status(Player def) {
        return (def == p1 ? hp1 : hp2) + "/100";
    }

    private void log(String desc) {
        events.add(new CombatEvent(desc));
    }

    public ArrayList<CombatEvent> getEvents() {
        return events;
    }

    public Player getWinnerPlayer() {
        if (hp2 <= 0 && hp1 > 0) return p1;
        if (hp1 <= 0 && hp2 > 0) return p2;
        return null;  // empate, aunque imposible
    }

    public Player getLoserPlayer(){
        if (hp1 <= 0 && hp2 > 0) return p1;
        if (hp2 <= 0 && hp1 > 0) return p2;
        return null;  // empate, aunque imposible
    }

    public LocalDateTime getDatetime(){
        return this.datetime;
    }

}
