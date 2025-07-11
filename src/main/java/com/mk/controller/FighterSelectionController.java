package com.mk.controller;

import com.mk.dao.FighterDAO;
import com.mk.model.Player;
import com.mk.view.FighterSelectionView;
import com.mk.model.Fighter;

import javax.swing.*;

public class FighterSelectionController{
    private final FighterSelectionView view;
    private final Fighter[] fighters = FighterDAO.getAllObjects().toArray(new Fighter[0]);
    private Player plr;



    public FighterSelectionController(Player player){
        this.view = new FighterSelectionView(fighters);
        this.plr = player;
        initListeners();
    }


    private void initListeners(){
        view.getLeftButton().addActionListener(e -> {
            view.previousFighter();
        });

        view.getRightButton().addActionListener(e -> {
            view.nextFighter();
        });

        view.getConfirmButton().addActionListener(e ->{
            plr.setSelectedFighter(view.getCurrentFighter());
            JOptionPane.showMessageDialog(null, "¡Personaje seleccionado!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            new Timer(1000, evt ->{
                ((Timer) evt.getSource()).stop();

                closeWindow();
            }).start();

        });
    }


    private void closeWindow(){
        view.dispose();
    }

}
