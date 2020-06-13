package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.BuildGui;

public class ColourSelectionListener implements ActionListener {
	BuildGui bg;
	public ColourSelectionListener(BuildGui bg) {
		this.bg = bg;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = null;
		if(e.getSource() instanceof JButton) {
			b = (JButton) e.getSource();
		}
		
		if(b!=null) {
			String type = b.getName();
		Color lastColour = bg.getLastColour(type);
		Color chosenColour = bg.getChoice(type);
		if(lastColour!=chosenColour) {
			bg.setColourChoiceLbl(chosenColour,type);
		}
		
		
		bg.hideColourFrame(type);
		
		if(type.equals("Gizmo")) {
		bg.setAction(BuildModeAction.SetGizmoColour);
		bg.setBotLineText("Click on a gizmo to change its colour.");
		}
		else if(type.equals("Ball")) {
			bg.setAction(BuildModeAction.SetBallColour);
			bg.setBotLineText("Click on a ball to change its colour.");
		}
		
			}
		
	}
	
	
	
}
