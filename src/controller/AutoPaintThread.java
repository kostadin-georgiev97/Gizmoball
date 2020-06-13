package controller;

import view.RunGui;

public class AutoPaintThread extends Thread{
	RunGui rg;
	public AutoPaintThread(RunGui rg) {
		this.rg = rg;
	}
	
	@Override
	public void run() {
		
		while(true) {
		
		rg.repainter();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		}
	}
	
}
