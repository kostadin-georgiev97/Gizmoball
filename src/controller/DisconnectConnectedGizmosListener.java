package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class DisconnectConnectedGizmosListener implements ActionListener{
    BuildGui buildGui;
    public DisconnectConnectedGizmosListener(BuildGui bg){
        buildGui = bg;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buildGui.setAction(BuildModeAction.DisconnectConnectedGizmo);
        buildGui.setBotLineText("Select a Gizmo to remove its connected Gizmos!");
    }
}
