package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class DisconnectKeyListener implements ActionListener {
	
	private BuildGui gui;

	public DisconnectKeyListener(BuildGui gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setAction(BuildModeAction.DisconnectKey);
		gui.setBotLineText("Click on a gizmo to disconnect its keybind.");
	}

}
