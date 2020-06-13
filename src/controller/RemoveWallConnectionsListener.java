package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class RemoveWallConnectionsListener implements ActionListener {
    BuildGui buildGui;
    public RemoveWallConnectionsListener(BuildGui bg){
        buildGui=bg;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buildGui.setAction(BuildModeAction.DisconnectWalls);
        buildGui.setBotLineText("Choose a Gizmo to disconnect from the walls");

    }
}
