package com.mk.model;

import com.mk.model.Fighter;
import com.mk.model.Player;

import java.time.LocalDateTime;
import java.time.Duration;

public class Combat {
    private int hpPlayer1 = 100;
    private int hpPlayer2 = 100;
    private LocalDateTime start;
    private LocalDateTime ending;
    private Duration duration = Duration.between(start, ending);

    Combat(Player p1, Player p2){
        start = LocalDateTime.now();
    }




}
