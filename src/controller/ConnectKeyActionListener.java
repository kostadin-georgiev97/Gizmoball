package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class ConnectKeyActionListener implements ActionListener {

	private BuildGui gui;

	public ConnectKeyActionListener(BuildGui gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setAction(BuildModeAction.ConnectKey);
		gui.setBotLineText("Press a key from the keyboard.");
	}
	
}
