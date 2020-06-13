package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Ball;
import view.RunGui;

public class RunStartListener implements ActionListener{
RunGui g;

public RunStartListener(RunGui g) {
	this.g= g;
	
}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!g.getIsRunning()) {
			for (Ball b : g.getBalls()) {
				g.moveBall(b);
			}

		} else {
			JOptionPane.showMessageDialog(null,"Game must be paused in order to use Tick");
		}
	}
}
