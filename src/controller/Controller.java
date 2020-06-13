package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.GameBoardModel;
import model.KeyFunc;
import model.iPlaceable;

public class Controller implements KeyListener{
	
	GameBoardModel gbm;
	
	public Controller(GameBoardModel gbm) {
		this.gbm = gbm;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		for(iPlaceable tile : gbm.getTiles()) {
			if(tile.getKeyCode() == e.getKeyCode()&&(tile.getKeyFunc()== KeyFunc.down||tile.getKeyFunc()==KeyFunc.dual)) {//down is pressed
				tile.trigger();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		for(iPlaceable tile : gbm.getTiles()) {
			if(tile.getKeyCode() == e.getKeyCode()&&(tile.getKeyFunc()== KeyFunc.up||tile.getKeyFunc()==KeyFunc.dual)) { //up is released
				tile.trigger();
			}
		}
	}

}
