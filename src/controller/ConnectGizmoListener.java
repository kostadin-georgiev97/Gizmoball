package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class ConnectGizmoListener implements ActionListener {
    BuildGui buildGui;
    public ConnectGizmoListener(BuildGui bg){
        buildGui=bg;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buildGui.setAction(BuildModeAction.ConnectGizmo);
        buildGui.setBotLineText("Please a select a Gizmo to connect to another Gizmo");

    }
}
