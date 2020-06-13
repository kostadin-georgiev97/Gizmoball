package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.BuildGui;

public class OptionsListener implements ActionListener{
	BuildGui bg;
	public OptionsListener(BuildGui bg) {
		this.bg = bg;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton pressed = null;
		if(arg0.getSource() instanceof JButton)
			pressed = (JButton) arg0.getSource();
		
		if(pressed != null) {
			if(pressed.getName() == "Gizmo") {

				bg.showGizmoOptions();
			}
			else if(pressed.getName() == "Ball") {
				bg.showBallOptions();
			}
			else if(pressed.getName() == "File") {
				bg.showFileOptions();
			}
		}
	}

}
