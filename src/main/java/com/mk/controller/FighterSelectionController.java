package com.mk.controller;

import com.mk.view.FighterSelectionView;
import com.mk.model.Fighter;

public class FighterSelectionController {
    private FighterSelectionView view;

    public FighterSelectionController(Fighter[] fighters){
        this.view = new FighterSelectionView(fighters);
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
            // to be implemented
        });
    }



}
