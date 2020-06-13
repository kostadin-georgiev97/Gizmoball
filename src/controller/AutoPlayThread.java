package controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Ball;
import view.RunGui;

public class AutoPlayThread extends Thread {

	RunGui rg;
	public AutoPlayThread(RunGui rg) {
		this.rg = rg;
	}
	
	@Override
	public void run() {
		boolean running = rg.getIsRunning();
		while (true) {
			try {
				rg.repainter();
				Thread.sleep(50);
				if(running) {
					Collection<Ball> balls = rg.getBalls();
					Map<Ball, double[]> ballsXY = new HashMap<>();
					for(Ball b : balls) {
						double bXY[] = {b.getX(), b.getY()};
						ballsXY.put(b, bXY);
					}
		            for(Ball b : balls) {
		            	double bXY[] = { b.getX(), b.getY()};
		            	if(Arrays.equals(ballsXY.get(b), bXY))
		            		rg.moveBall(b);
		            }
					
					
				}
				running = rg.getIsRunning();
			} catch (InterruptedException e1) {
				break;
			}
		}
		
	}
	
	

}
