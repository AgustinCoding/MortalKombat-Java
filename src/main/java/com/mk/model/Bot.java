package com.mk.model;

import com.mk.dao.FighterDAO;
import com.mk.model.Player;

import java.util.Random;
import java.util.ArrayList;

public class Bot extends Player {

    private static final Random rand = new Random();
    private static final ArrayList<Fighter> fighters = FighterDAO.getAllObjects();


    private Bot(){

        super("", 0);
        this.setSelectedFighter(fighters.get(rand.nextInt(0, fighters.size())));

    }


    public static Bot getRandomBot(int playerExp){
        Bot newBot = new Bot();
        newBot.setUsername(getBotName(newBot.getSelectedFighter().getName()));
        newBot.setExp(playerExp == 0 ? 0 : generateBalancedExp(playerExp));

        return newBot;
    }


    private static int generateBalancedExp(int playerExp) {
        final int DELTA = 20; // cuanto puede variar para arriba o abajo

        Random rand = new Random();

        // Genera un número entre -DELTA y +DELTA
        int variation = rand.nextInt(DELTA * 2 + 1) - DELTA;

        int result = playerExp + variation;

        return Math.max(result, 0); // nunca menos que cero
    }


    public static String getBotName(String characterName) {
        switch (characterName) {
            case "Scorpion":
                return "Escorpión-Pérez";

            case "Sub-Zero":
                return "Sub-Cero-Pesos";

            case "Liu Kang":
                return "Liu_Kangrejo";

            case "Raiden":
                return "Raiden-melaplata";

            case "Johnny Cage":
                return "JuanCarlosCage";

            case "Sonya Blade":
                return "Sonia_Bidet";

            case "Kitana":
                return "Kit-anastasia";

            case "Mileena":
                return "Milanesa";

            case "Jax":
                return "Jacinto";

            case "Kung Lao":
                return "Kung-Nabo";

            case "Baraka":
                return "Baraka-Obama";

            case "Reptile":
                return "Reptilianoide";

            case "Shang Tsung":
                return "Shang_tungtungtung";

            case "Kano":
                return "Caño";

            case "Noob Saibot":
                return "Pro_Saib0t_420";

            case "Nightwolf":
                return "Nikewolf";

            case "Sindel":
                return "SindelMoney";

            case "Frost":
                return "El_hielos";

            case "Jade":
                return "Jajajade";

            case "Shao Kahn":
                return "Chau-Kahn";

            default:
                return characterName + "_Bot";
        }
    }

}
