package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameBoardModel;
import view.BuildGui;

public class ClearBoardListener implements ActionListener {
    private GameBoardModel gbm;
    private BuildGui bg;
    public ClearBoardListener(GameBoardModel gbm, BuildGui bg){
        this.bg =bg;
        this.gbm=gbm;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        gbm.resetGameBoard();
        bg.setBotLineText("Board cleared!!");
        bg.repainter();

    }
}
