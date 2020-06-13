package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class AddWallConnectionsListener implements ActionListener {
    BuildGui buildGui;
    public AddWallConnectionsListener(BuildGui bg){
        buildGui=bg;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buildGui.setBotLineText("Pick A Gizmo to attach a Wall Connection too");
        buildGui.setAction(BuildModeAction.ConnectWalls);
    }
}
