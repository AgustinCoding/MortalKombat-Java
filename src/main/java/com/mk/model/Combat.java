package com.mk.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Combat {

    /**
     * Clase interna que representa un evento ocurrido durante el combate.
     * Guarda una descripción y el momento en que ocurrió.
     */
    public static class CombatEvent {
        private final String description;
        private final long timestamp;

        public CombatEvent(String description) {
            this.description = description;
            this.timestamp   = System.currentTimeMillis();
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    // Jugadores que participan en el combate
    private final Player p1;
    private final Player p2;

    // Vida de cada jugador (inicialmente 100)
    private int hp1 = 100;
    private int hp2 = 100;

    // Lista de eventos que narran todo lo sucedido en el combate
    private final ArrayList<CombatEvent> events = new ArrayList<>();

    // Generador de números aleatorios para decidir acciones y daño
    private final Random random = new Random();

    // Fecha y hora en que se inició el combate
    private final LocalDateTime datetime;

    // Constantes que influyen en el cálculo de fuerza
    private static final double ALPHA = 0.25; // peso de la experiencia
    private static final double BETA  = 1.0;  // (no se usa en el código actual)

    // Constructor: inicializa el combate entre dos jugadores
    public Combat(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.datetime = LocalDateTime.now();
        log("¡COMBATE INICIADO: " + p1.getUsername() + " vs " + p2.getUsername() + "!");
    }

    /**
     * Ejecuta el combate completo hasta que alguno de los dos jugadores (o ambos) llegue a 0 HP.
     * El turno se alterna entre p1 y p2.
     */
    public void execute() {
        boolean turnoP1 = true; // true → le toca a p1, false → le toca a p2

        while (hp1 > 0 && hp2 > 0) {
            if (turnoP1) {
                simulateTurn(p1, p2); // p1 actúa y p2 recibe la acción
            } else {
                simulateTurn(p2, p1); // p2 actúa y p1 recibe la acción
            }
            turnoP1 = !turnoP1; // cambiar el turno
        }

        // Resultado final
        if (hp1 <= 0 && hp2 <= 0) {
            log("¡EMPATE! Ambos han caído.");
        } else if (hp2 <= 0) {
            log("¡VICTORIA de " + p1.getUsername() + "!");
            p1.setExp(p1.getExp() + 2); // gana experiencia
            log(p1.getUsername() + " gana +2 XP (total: " + p1.getExp() + ")");
        } else {
            log("¡VICTORIA de " + p2.getUsername() + "!");
            p2.setExp(p2.getExp() + 2);
            log(p2.getUsername() + " gana +2 XP (total: " + p2.getExp() + ")");
        }
    }

    /**
     * Simula un turno de juego donde un jugador actúa y el otro recibe la acción.
     * @param actor jugador que actúa en este turno
     * @param receptor jugador que recibe la acción
     */
    private void simulateTurn(Player actor, Player receptor) {
        // Fuerza del jugador que actúa
        double fuerzaActor = random.nextInt(10) + 1
                + actor.getSelectedFighter().getBaseStrength()
                + ALPHA * actor.getExp();

        // Fuerza base del jugador que recibe (no se usa en el cálculo actual del daño, pero podría servir para defensas)
        double fuerzaReceptor = random.nextInt(10) + 1
                + receptor.getSelectedFighter().getBaseStrength();

        // Tirada para decidir qué ocurre en el turno
        int roll = random.nextInt(100);

        if (roll < 20) {
            log(actor.getUsername() + " falló la acción. (-0 HP)");
        }
        else if (roll < 30) {
            log(actor.getUsername() + " actuó pero " + receptor.getUsername() + " evitó el impacto (–0 HP)");
        }
        else if (roll < 80) {
            // Acción normal
            int dmg = (int)(fuerzaActor * 0.35);
            applyDamage(receptor, dmg);
        }
        else {
            // Golpe crítico
            int dmg = (int)(fuerzaActor * 0.75);
            applyCritical(receptor, dmg);
        }
    }

    // Aplica daño normal
    private void applyDamage(Player receptor, int dmg) {
        if (receptor == p1) {
            hp1 = Math.max(hp1 - dmg, 0);
        } else {
            hp2 = Math.max(hp2 - dmg, 0);
        }
        log(receptor.getUsername() + " recibió –" + dmg + " HP (HP " + status(receptor) + ")");
    }

    // Aplica daño crítico
    private void applyCritical(Player receptor, int dmg) {
        if (receptor == p1) {
            hp1 = Math.max(hp1 - dmg, 0);
        } else {
            hp2 = Math.max(hp2 - dmg, 0);
        }
        log("¡CRÍTICO! " + receptor.getUsername() + " recibió –" + dmg + " HP (HP " + status(receptor) + ")");
    }

    // Devuelve el estado de vida actual de un jugador
    private String status(Player p) {
        return (p == p1 ? hp1 : hp2) + "/100";
    }

    // Registra un evento en la lista de narración
    private void log(String desc) {
        events.add(new CombatEvent(desc));
    }

    // Getters para acceder a información del combate
    public ArrayList<CombatEvent> getEvents() {
        return events;
    }

    public Player getWinnerPlayer() {
        if (hp2 <= 0 && hp1 > 0) return p1;
        if (hp1 <= 0 && hp2 > 0) return p2;
        return null;  // empate
    }

    public Player getLoserPlayer(){
        if (hp1 <= 0 && hp2 > 0) return p1;
        if (hp2 <= 0 && hp1 > 0) return p2;
        return null;  // empate
    }

    public LocalDateTime getDatetime(){
        return this.datetime;
    }
}
