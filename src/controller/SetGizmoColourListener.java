package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.BuildGui;

public class SetGizmoColourListener implements ActionListener {

	private BuildGui gui;

	public SetGizmoColourListener(BuildGui gui) {
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton b = null;

		if (arg0.getSource() instanceof JButton) {
			b = (JButton) arg0.getSource();
		}
		if(b!=null) {
			
			String type = b.getName();
			if(type.equals("Gizmo") || type.equals("Ball"))
				gui.showColourFrame(type);
			
		
		if(type.equals("Gizmo") || type.equals("Current Gizmo")) {
			gui.setAction(BuildModeAction.SetGizmoColour);
			gui.setBotLineText("Click on a gizmo to change its colour.");
		}
			
		
		else if(type.equals("Ball") || type.equals("Current Ball")){
			gui.setAction(BuildModeAction.SetBallColour);
			gui.setBotLineText("Click on a ball to change its colour.");
			
		}
	}
	}
	
}
