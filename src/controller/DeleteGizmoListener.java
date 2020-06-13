package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class DeleteGizmoListener implements ActionListener {
	
	private BuildGui gui;

	public DeleteGizmoListener(BuildGui gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setAction(BuildModeAction.DeleteGizmo);
		gui.setBotLineText("Click on a gizmo to delete it.");
	}

}
