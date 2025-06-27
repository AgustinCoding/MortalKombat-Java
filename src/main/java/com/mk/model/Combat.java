package com.mk.model;

import com.mk.model.Fighter;
import com.mk.model.Player;

public class Combat {
    private final Player player1; // Usuario
    private final Player player2; //
    private final String[] COMENTARIOS_PELEA = {
            "Scorpion lanzó su kunai mientras Sub-Zero esquivaba con un slide!",
            "Liu Kang conectó un bicycle kick que hizo retroceder a su oponente!",
            "Raiden canalizó un rayo celestial desde los cielos!",
            "Johnny Cage ejecutó su shadow kick con una precisión mortal!",
            "Sonya Blade lanzó un kiss of death que impactó directamente!",
            "Kitana desplegó sus abanicos cortantes en un movimiento letal!",
            "Mileena ejecutó su sai fury con una velocidad impresionante!",
            "Jax conectó un ground pound que hizo temblar la arena!",
            "Kung Lao desató su hat spin con una precisión mortal!",
            "Baraka desgarró a su oponente con sus armas naturales!",
            "Reptile se volvió invisible antes de lanzar un ataque sorpresa!",
            "Shang Tsung robó la forma de su oponente para contraatacar!",
            "Kano ejecutó su knife throw desde una distancia peligrosa!",
            "Noob Saibot invocó su shadow clone para un ataque combinado!",
            "Nightwolf canalizó el espíritu del lobo para aumentar su fuerza!",
            "Sindel desató su banshee scream que rompió los tímpanos!",
            "Frost congeló el suelo creando una trampa mortal!",
            "Jade ejecutó un deadly boomerang que rebotó en los muros!",
            "Shao Kahn aplastó a su oponente con su martillo gigante!",
            "El combate alcanzó su clímax con una serie de ataques furiosos!"
    };


    public Combat(Fighter fighter1, Fighter fighter2){
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

}
