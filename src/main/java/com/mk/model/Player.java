package com.mk.model;

public class Player {
    private String username;
    private Fighter selectedFighter;
    private int exp;


    public Player(String username, Fighter figther){
        this.username = username;
        this.selectedFighter = figther;
    }

    public void setExp(int exp){
        this.exp = exp;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
