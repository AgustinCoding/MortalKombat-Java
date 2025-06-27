package com.mk.model;

import java.util.ArrayList;

public class Fighter {
    private String name;
    private String image;
    private String description;
    private String gender;
    private int id;
    private int baseStrength;
    private ArrayList<String> dialogs = new ArrayList<>();


    public Fighter(String name, int baseStrength, String gender, String image, String description, int id){
        this.name = name;
        this.image = image;
        this.description = description;
        this.gender = gender;
        this.id = id;
        this.baseStrength = baseStrength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    public void setNewDialog(String dialog){
        this.dialogs.add(dialog);
    }
}
