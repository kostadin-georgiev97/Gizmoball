package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class MoveGizmoListener implements ActionListener {
	
	private BuildGui gui;

	public MoveGizmoListener(BuildGui gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setAction(BuildModeAction.MoveGizmo);
		gui.setSourceX(-1);
		gui.setSourceY(-1);
		gui.setTargetX(-1);
		gui.setTargetY(-1);
		gui.setBotLineText("Click on an object on the board to move it.");
	}

}
