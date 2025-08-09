package com.mk.model;

import com.mk.dao.FighterDAO;
import java.util.Random;
import java.util.ArrayList;

public class Bot extends Player {

    // Random para generar aleatoriedad en luchador y experiencia
    private static final Random rand = new Random();
    // Lista de todos los luchadores obtenidos de la base de datos
    private static final ArrayList<Fighter> fighters = FighterDAO.getAllObjects();

    /**
     * Constructor privado:
     * - Se llama solo desde getRandomBot().
     * - Inicializa un Bot con:
     *   - username vacío (se asigna después).
     *   - exp = 0 (también se ajusta después).
     *   - Un luchador aleatorio de la lista fighters.
     */
    private Bot(){
        super("", 0); // llama al constructor de Player
        this.setSelectedFighter(fighters.get(rand.nextInt(0, fighters.size())));
    }

    /**
     * Genera un Bot listo para combatir.
     * @param playerExp experiencia del jugador humano.
     * @return Bot con nombre gracioso y experiencia equilibrada.
     */
    public static Bot getRandomBot(int playerExp){
        Bot newBot = new Bot();
        // Nombre según el luchador que le tocó
        newBot.setUsername(getBotName(newBot.getSelectedFighter().getName()));
        // Si el jugador tiene exp 0, el bot también.
        // Si no, se genera exp equilibrada con variación.
        newBot.setExp(playerExp == 0 ? 0 : generateBalancedExp(playerExp));

        return newBot;
    }

    /**
     * Calcula una experiencia similar a la del jugador, con variación aleatoria.
     * @param playerExp exp del jugador
     * @return exp ajustada (nunca menor que 0)
     */
    private static int generateBalancedExp(int playerExp) {
        final int DELTA = 20; // margen de variación permitido
        int variation = rand.nextInt(DELTA * 2 + 1) - DELTA;
        int result = playerExp + variation;
        return Math.max(result, 0); // evita valores negativos
    }

    /**
     * Asigna un nombre al Bot según el luchador que use.
     * Si no hay coincidencia en el switch, añade "_Bot" al nombre original.
     */
    public static String getBotName(String characterName) {
        switch (characterName) {
            case "Scorpion": return "Escorpión-Pérez";
            case "Sub-Zero": return "Sub-Cero-Pesos";
            case "Liu Kang": return "Liu_Kangrejo";
            case "Raiden": return "Raiden-melaplata";
            case "Johnny Cage": return "JuanCarlosCage";
            case "Sonya Blade": return "Sonia_Bidet";
            case "Kitana": return "Kit-anastasia";
            case "Mileena": return "Milanesa";
            case "Jax": return "Jacinto";
            case "Kung Lao": return "Kung-Nabo";
            case "Baraka": return "Baraka-Obama";
            case "Reptile": return "Reptilianoide";
            case "Shang Tsung": return "Shang_tungtungtung";
            case "Kano": return "Caño";
            case "Noob Saibot": return "Pro_Saib0t_420";
            case "Nightwolf": return "Nikewolf";
            case "Sindel": return "SindelMoney";
            case "Frost": return "El_hielos";
            case "Jade": return "Jajajade";
            case "Shao Kahn": return "Chau-Kahn";
            default: return characterName + "_Bot";
        }
    }
}
