package com.mk.model;

import com.mk.utils.TxtReader;

import java.util.ArrayList;

public class Game {

    String[] players;

    private final String[][] stringCharacters = {
            {"Scorpion", "7", "M", "images/scorpion.png", "Ninja espectral con kunai ardiente"},
            {"Sub-Zero", "7", "M", "images/subzero.png", "Maestro del hielo de Lin Kuei"},
            {"Liu Kang", "8", "M", "images/liukang.png", "Monje shaolin campeón de Mortal Kombat"},
            {"Raiden", "9", "M", "images/raiden.png", "Dios del trueno protector de la Tierra"},
            {"Johnny Cage", "6", "M", "images/johnnycage.png", "Actor famoso con poderes ancestrales"},
            {"Sonya Blade", "6", "F", "images/sonyablade.png", "Líder de las fuerzas especiales"},
            {"Kitana", "7", "F", "images/kitana.png", "Princesa de Edenia con abanicos mortales"},
            {"Mileena", "7", "F", "images/mileena.png", "Clon híbrido de Kitana con hambre de sangre"},
            {"Jax", "8", "M", "images/jax.png", "Soldado con implantes biónicos en sus brazos"},
            {"Kung Lao", "7", "M", "images/kunglao.png", "Maestro shaolin con sombrero cortante"},
            {"Baraka", "8", "M", "images/baraka.png", "Guerrero tarkatan con cuchillas naturales"},
            {"Reptile", "6", "M", "images/reptile.png", "Ninja reptiliano con habilidades de camuflaje"},
            {"Shang Tsung", "9", "M", "images/shangtsung.png", "Hechicero cambiaformas de Outworld"},
            {"Kano", "6", "M", "images/kano.png", "Mercenario líder del Dragón Negro"},
            {"Noob Saibot", "8", "M", "images/noobsaibot.png", "Espectro oscuro maestro de las sombras"},
            {"Nightwolf", "7", "M", "images/nightwolf.png", "Chamán nativo americano con poderes espirituales"},
            {"Sindel", "8", "F", "images/sindel.png", "Reina de Edenia con grito mortal"},
            {"Frost", "6", "F", "images/frost.png", "Aprendiz de Sub-Zero con poderes criogénicos"},
            {"Jade", "7", "F", "images/jade.png", "Guerrera leal a Kitana con bastón de combate"},
            {"Shao Kahn", "10", "M", "images/shaokahn.png", "Emperador tiránico de Outworld"}
    }; // Esta lista sera eliminada y reemplazada por fightersDAO.getAll(). Los datos deben de ser almacenados y controlados por sqlite


    private final Fighter[] fighters = new Fighter[stringCharacters.length];


    public Game(){
        loadCharacters();
        assignDialogToFighters();
    }

    private void loadCharacters(){ // Metodo debe ser modificado para que cree objetos Fighter con los datos de la bd

        for(int i = 0 ; i < stringCharacters.length; i++){
            Fighter fighter = new Fighter(
                    stringCharacters[i][0],
                    Integer.parseInt(stringCharacters[i][1]),
                    stringCharacters[i][2],
                    stringCharacters[i][3],
                    stringCharacters[i][4],
                    i
            );
            fighters[i] = fighter;
        }

    }

    private void assignDialogToFighters(){ // Futura modificacion: Almacenar dialogos desde bd
       ArrayList<String> dialogs = TxtReader.linesToList("dialogs.txt");

       for(Fighter f : fighters){
           System.out.println("Dialogos para: " + f.getName());
           for(String dialog : dialogs){
               if(dialog.contains(f.getName())){
                   f.setNewDialog(dialog.split(":", 2)[1].trim());
                   System.out.println("Dialogo agregado: " + dialog);
               }
           }
       }



    }

    public Fighter[] getFighters(){
        return this.fighters;
    }

    public String[][] getStringFighters(){
        return this.stringCharacters;
    }
}
