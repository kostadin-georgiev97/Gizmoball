package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class AddGizmoListener implements ActionListener {
	
	private BuildGui gui;

	public AddGizmoListener(BuildGui gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setAction(BuildModeAction.AddGizmo);
		if(String.valueOf(gui.getGizmoDrop().getSelectedItem()).equals("Absorber")) {
			gui.setBotLineText("Click on an empty grid location to choose one of the absorber's corners.");
		} else {
			gui.setBotLineText("Click on an empty grid location to add Gizmo.");
		}
		gui.setSourceX(-1);
		gui.setSourceY(-1);
		gui.setTargetX(-1);
		gui.setTargetY(-1);
	}

}
