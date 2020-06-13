package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class RotateGizmoListener implements ActionListener {
	
	private BuildGui gui;

	public RotateGizmoListener(BuildGui gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setAction(BuildModeAction.RotateGizmo);
		gui.setBotLineText("Click on a gizmo to rotate it.");
	}
	
}
