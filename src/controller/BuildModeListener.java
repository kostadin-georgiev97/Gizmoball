package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JOptionPane;

import model.GameBoardModel;
import model.Gizmo;
import model.iPlaceable;
import view.RunGui;

public class BuildModeListener implements ActionListener {

    private RunGui runGui;
    private GameBoardModel gbm;

    public BuildModeListener(RunGui run_View, GameBoardModel gbm) {
        runGui = run_View;
        this.gbm = gbm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Set<iPlaceable> placeableList = gbm.getTiles();

        if (!runGui.getIsRunning() && !checkTriggers(placeableList)) {
            runGui.setVisible(false);
        } else {
            if(runGui.getIsRunning()) {
                System.out.println("Game currently running, cant change to build mode");
                JOptionPane.showMessageDialog(null, "Cant switch to build mode, pause game first");
            }else {
                JOptionPane.showMessageDialog(null,"Cant Switch till all actions have stopped");
            }
        }

    }

    private boolean checkTriggers(Set<iPlaceable> placeables) {
        for (iPlaceable placeable : placeables) {
            if (((Gizmo) placeable).isTriggered()) {
                return true;
            }

        }
        return false;//nothing triggered safe to go back
    }
}
