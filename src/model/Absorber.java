package model;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.physics.Angle;
import model.physics.Circle;
import model.physics.LineSegment;
import model.physics.Vect;

public class Absorber extends Gizmo {


	int startX;
	int startY;
	int endX;
	int endY;
	Ball holdingBall;


	public Absorber(String name,GizmoType type,int startX, int startY, int endX, int endY, Color colour) {
		 this(name, type,startX, startY, endX, endY, colour, new HashSet<iPlaceable>(), new HashSet<Integer>());
	}
	
	public Absorber(String name, GizmoType type, int startX, int startY, int endX, int endY, Color colour, Set<iPlaceable> triggers, Set<Integer> keyCodes) {
		super(name,type,colour);
		this.startX = startX;
		 this.startY = startY;
		 this.endX = endX;
		 this.endY = endY;
	}

	@Override
	public void setTriggerType(TriggerType type) {
		//do nothing
	}

	@Override
	public int getXStartPos() {
		return startX;
	}
	@Override
	public int getYStartPos() {
		return startY;
	}
	
	public int getXEndPos() {
		return endX;
	}
	
	public int getYEndPos() {
		return endY;
	}


	@Override
	public int getOrientation() {
		return 0;
	}





	
	public void trigger() {
		carryOutTrigger();
		System.out.println("triggered");


		
	}
	
	
	public Set<LineSegment> getLines() {
		Set<model.physics.LineSegment> lines = new HashSet<model.physics.LineSegment>();
		lines.add(new model.physics.LineSegment(startX, startY, endX, startY));
		lines.add(new model.physics.LineSegment(startX, endY, endX, endY));
		lines.add(new model.physics.LineSegment(startX, startY, startX, endY));
		lines.add(new model.physics.LineSegment(endX, startY, endX, endY));
		return lines;
	}

	public Set<Circle> getCircles() {
		Set<model.physics.Circle> circles = new HashSet<model.physics.Circle>();
		circles.add(new model.physics.Circle(startX, startY, 0));
		circles.add(new model.physics.Circle(endX, startY, 0));
		circles.add(new model.physics.Circle(startX, endY, 0));
		circles.add(new model.physics.Circle(endX, endY, 0));
		return circles;
	}



	@Override
	public void setXStartPos(int xPos) {
		this.startX = xPos;
	}

	@Override
	public void setYStartPos(int yPos) {
		this.startY = yPos;
	}

	@Override
	public void setXEndPos(int xPos) {
		this.endX = xPos;
	}

	@Override
	public void setYEndPos(int yPos) {
		this.endY = yPos;
	}
	@Override
	public void setOrientation(int rotation) {

	}
	

	
	private void eatBall(Ball ball) {
		if(holdingBall == null) {
			ball.setXY(endX-ball.getRadius(), endY-ball.getRadius());
			ball.setVelo(new Vect(Angle.ZERO, 0));
			ball.setStationary(true);
    		this.holdingBall = ball;
    		System.out.println(this.getName() + ": NOMMED " + ball.getRadius() + holdingBall.getRadius());
    		
		}
	}
	

    public boolean hit(Ball ball) {
    	boolean success = false;
    	if(lastHit == 2) {
    		eatBall(ball);
    		success = true;
    	}
    	lastHit = 0;
    	return success;
    }
    
    public void resetHit() {
    	if(lastHit < 2 && holdingBall==null) {
    		lastHit++;
    	}
    }

	@Override
	public boolean rotate() {
		return false;
	}

	@Override
	public void triggerOnce() {
		carryOutTrigger();
	}

	private void carryOutTrigger() {
		if (holdingBall != null) {
			holdingBall.setXY(endX - holdingBall.getRadius(), startY - holdingBall.getRadius());
			holdingBall.setVelo(new Vect(Angle.DEG_270, 50));
			holdingBall.setStationary(false);
			holdingBall = null;
		}
	}

}
