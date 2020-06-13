package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.RunGui;

public class AutoRunListener implements ActionListener{
RunGui g;
boolean play = false;
Thread t;
public AutoRunListener(RunGui g) {
	this.g= g;
	//t = null;
	t= new Thread(new AutoPlayThread(g));
	t.start();
}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if (source instanceof JButton) {
			JButton b = (JButton) source;
			System.out.println(b.getText());
			if (b.getText().equals("Play")) {
				b.setText("Pause");
				g.setIsRunning(true);
				
				
			} else if (b.getText().equals("Pause")) {
				assert t!=null;
				b.setText("Play");
				g.setIsRunning(false);;
				//t.interrupt();
			}	
	}
		
	g.runStuff();
	
	}
}