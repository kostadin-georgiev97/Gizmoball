package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import model.GameBoardModel;
import model.KeyFunc;
import view.BuildGui;

public class ConnectKeyKeyListener implements KeyListener {
	
	private BuildGui gui;
	private GameBoardModel model;

	public ConnectKeyKeyListener(BuildGui gui,GameBoardModel gameBoardModel) {
		this.model = gameBoardModel;
		this.gui = gui;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object[] options = {"Key Press", "Key Release","Both"};
		if(gui.getAction().equals(BuildModeAction.ConnectKey)) {

			if(gui.getPressedKey() == KeyEvent.VK_UNDEFINED) {
				gui.setPressedKey(e.getKeyCode());

				int choice =JOptionPane.showOptionDialog(null,"Would you like the connection to act on a press or a release?","Key Connection",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
				switch (choice){
					case JOptionPane.YES_OPTION:
						gui.setKeyFunc(KeyFunc.down);
						break;
					case JOptionPane.NO_OPTION:
						gui.setKeyFunc(KeyFunc.up);
						break;
					case JOptionPane.CANCEL_OPTION:
						gui.setKeyFunc(KeyFunc.dual);
						break;

						default:
							gui.setBotLineText("Aborted Key connection");
							gui.setAction(BuildModeAction.None);
							gui.setKeyFunc(KeyFunc.undefined);

							return;
				}

				gui.setBotLineText("You have pressed " + KeyEvent.getKeyText(gui.getPressedKey()) + " assigned it as a "+options[choice]+". Click on a gizmo to connect the key to.");

			} else {
				gui.setBotLineText("You've already chosen " + KeyEvent.getKeyText(gui.getPressedKey()) + " key to be connected. Click on a Gizmo to connect the key to.");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
