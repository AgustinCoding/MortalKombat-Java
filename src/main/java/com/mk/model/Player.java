package com.mk.model;

public class Player {
    // Nombre del jugador
    private String username;
    // Luchador seleccionado por el jugador inicializado en null
    private Fighter selectedFighter = null;
    // Experiencia del jugador (afecta al daño potencial en combate)
    private int exp = 1;

    // Constructor: inicializa un jugador con su nombre y experiencia inicial
    public Player(String username, int exp){
        this.username = username;
        this.exp = exp;
    }

    // Setters: permiten modificar atributos del jugador
    public void setExp(int exp){
        this.exp = exp;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setSelectedFighter(Fighter fighter){
        this.selectedFighter = fighter;
    }

    // Getters: permiten consultar atributos del jugador
    public Fighter getSelectedFighter() {
        return this.selectedFighter;
    }

    public int getExp(){
        return this.exp;
    }

    public String getUsername() {
        return this.username;
    }
}
