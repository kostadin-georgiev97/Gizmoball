package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class RemoveTriggerListener implements ActionListener {
    private BuildGui buildGui;
    public RemoveTriggerListener(BuildGui bg){
        buildGui=bg;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        buildGui.setAction(BuildModeAction.RemoveTrigger);
        buildGui.setBotLineText("Click a Gizmo to remove its trigger functionality");
    }
}

