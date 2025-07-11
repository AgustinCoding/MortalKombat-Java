package com.mk.model;

public class Player {
    private String username;
    private Fighter selectedFighter = null;
    private int exp;


    public Player(String username, int exp){
        this.username = username;
        this.exp = exp;
    }

    public void setExp(int exp){
        this.exp = exp;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setSelectedFighter(Fighter fighter){ this.selectedFighter = fighter; }

    public Fighter getSelectedFighter() {
        return this.selectedFighter;
    }

    public int getExp(){
        return this.exp;
    }

    public String getUsername() {return this.username;}
}

