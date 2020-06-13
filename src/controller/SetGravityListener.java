package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.GameBoardModel;
import view.BuildGui;

public class SetGravityListener implements ActionListener{

	BuildGui bg;
	JTextField inField;
	GameBoardModel m;
	
	public SetGravityListener(BuildGui buildGui,GameBoardModel m) {
		bg = buildGui;
		this.m = m;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		m.setGravityValue(bg.getGravity());
		bg.setBotLineText("Gravity set to "+bg.getGravity());
	}

}
