package model.Flipper;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.Gizmo;
import model.GizmoType;
import model.physics.Circle;
import model.physics.LineSegment;

public abstract class Flipper extends Gizmo
{
    private int xPos;
    private int yPos;
    protected double angle;
    private double omega;
    protected int orientation;
    private boolean triggered;

    public Set<Circle> circles;
    public Set<LineSegment> lines;

    protected Circle leftCircle;
    protected Circle rightCircle;
    protected LineSegment topLine;
    protected LineSegment bottomLine;

    // centre points of circles
    protected double leftX;
    protected double leftY;
    protected double rightX;
    protected double rightY;

    // initial positions of circles
    protected double initLeftX;
    protected double initLeftY;
    protected double initRightX;
    protected double initRightY;

    // initial positions of lines
    protected double topLeftX;
    protected double topLeftY;
    protected double topRightX;
    protected double topRightY;
    protected double bottomLeftX;
    protected double bottomLeftY;
    protected double bottomRightX;
    protected double bottomRightY;

    protected double ctopLeftX;
    protected double ctopLeftY;
    protected double ctopRightX;
    protected double ctopRightY;
    protected double cbottomLeftX;
    protected double cbottomLeftY;
    protected double cbottomRightX;
    protected double cbottomRightY;

    public Flipper(String name, GizmoType type, int xPos, int yPos, int orientation, Color colour)
    {
	super(name, type,colour);
	this.xPos = xPos;
	this.yPos = yPos;

	circles = new HashSet<>();
	lines = new HashSet<>();
	this.setOrientation(orientation);

	angle = 0;
	omega = 5;
	triggered = false;

	build();
	addToSets();
    }

    public void build()
    {
	switch (orientation)
	{
	case 0:
	    initLeftX = xPos + 0.25;
	    initLeftY = yPos + 0.25;
	    initRightX = xPos + 1.75;
	    initRightY = yPos + 0.25;

	    topLeftX = xPos + 0.25;
	    topLeftY = yPos + 0;
	    topRightX = xPos + 1.75;
	    topRightY = yPos + 0;

	    bottomLeftX = xPos + 0.25;
	    bottomLeftY = yPos + 0.5;
	    bottomRightX = xPos + 1.75;
	    bottomRightY = yPos + 0.5;
	    break;
	case 1:
	    initLeftX = xPos + 0.25;
	    initLeftY = yPos + 1.75;
	    initRightX = xPos + 0.25;
	    initRightY = yPos + 0.25;

	    topLeftX = xPos + 0;
	    topLeftY = yPos + 1.75;
	    topRightX = xPos + 0;
	    topRightY = yPos + 0.25;

	    bottomLeftX = xPos + 0.5;
	    bottomLeftY = yPos + 1.75;
	    bottomRightX = xPos + 0.5;
	    bottomRightY = yPos + 0.25;
	    break;
	case 2:
	    initLeftX = xPos + 1.75;
	    initLeftY = yPos + 1.75;
	    initRightX = xPos + 0.25;
	    initRightY = yPos + 1.75;

	    topLeftX = xPos + 1.75;
	    topLeftY = yPos + 2;
	    topRightX = xPos + 0.25;
	    topRightY = yPos + 2;

	    bottomLeftX = xPos + 1.75;
	    bottomLeftY = yPos + 1.5;
	    bottomRightX = xPos + 0.25;
	    bottomRightY = yPos + 1.5;
	    break;
	case 3:
	    initLeftX = xPos + 1.75;
	    initLeftY = yPos + 0.25;
	    initRightX = xPos + 1.75;
	    initRightY = yPos + 1.75;

	    topLeftX = xPos + 2;
	    topLeftY = yPos + 0.25;
	    topRightX = xPos + 2;
	    topRightY = yPos + 1.75;

	    bottomLeftX = xPos + 1.5;
	    bottomLeftY = yPos + 0.25;
	    bottomRightX = xPos + 1.5;
	    bottomRightY = yPos + 1.75;
	    break;
	}

	leftX = initLeftX;
	leftY = initLeftY;
	rightX = initRightX;
	rightY = initRightY;

	ctopLeftX = topLeftX;
	ctopLeftY = topLeftY;
	ctopRightX = topRightX;
	ctopRightY = topRightY;

	cbottomLeftX = bottomLeftX;
	cbottomLeftY = bottomLeftY;
	cbottomRightX = bottomRightX;
	cbottomRightY = bottomRightY;

	leftCircle = new Circle(initLeftX, initLeftY, 0.25);
	rightCircle = new Circle(initRightX, initRightY, 0.25);

	topLine = new LineSegment(topLeftX, topLeftY, topRightX, topRightY);
	bottomLine = new LineSegment(bottomLeftX, bottomLeftY, bottomRightX, bottomRightY);
    }

    public void addToSets()
    {
	lines.add(topLine);
	lines.add(bottomLine);
	circles.add(leftCircle);
	circles.add(rightCircle);
    }

    @Override
    public Set<LineSegment> getLines()
    {
	return lines;
    }

    @Override
    public Set<Circle> getCircles()
    {
	return circles;
    }
    
    @Override
    public int getXStartPos() {
        return this.xPos;
    }

    @Override
    public int getYStartPos() {
        return this.yPos;
    }

    @Override
    public int getXEndPos() {
        return this.xPos + 2;
    }

    @Override
    public int getYEndPos() {
        return this.yPos + 2;
    }
    
    @Override
	public void setXStartPos(int xPos) {
		this.xPos = xPos;
	}

	@Override
	public void setYStartPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public void setXEndPos(int xPos) {
		
	}

	@Override
	public void setYEndPos(int yPos) {
		
	}

    @Override
    public void setOrientation(int rotation) {
    	orientation = rotation;
    	build();
    }

    @Override
    public int getOrientation() {
    	return orientation;
    }



    protected void updateAngle()
    {
	if (triggered) // while triggered increase angle until limit of 90 deg
		       // reached
	{
		if(angle==90){
			this.untrigger();
		}
	    if (angle + omega > 90)
	    {
		angle = 90;

	    }
	    else
	    {
		angle += omega;
	    }

	}
	else // while not triggered decrease angle until limit of 0 deg reached
	{
	    if (angle - omega < 0)
	    {
		angle = 0;
	    }
	    else
	    {
		angle -= omega;
	    }
	}
    }

    public void trigger() {
		triggered = true;
		super.setTriggered();
		//triggerConnectedGizmos
		super.triggerConnectedGizos();

	}
    public void untrigger()
    {
	triggered = false;
	super.resetTriggered();
	//updateAngle();
    }

	protected double newX(double rx, double ry, double cx, double cy, double deg)
	{
		double x = rx - cx;
		double y = ry - cy;
		double theta = Math.toRadians(deg);
		return x * Math.cos(theta) - y * Math.sin(theta) + cx;
	}


	protected double newY(double rx, double ry, double cx, double cy, double deg)
	{
		double x = rx - cx;
		double y = ry - cy;
		double theta = Math.toRadians(deg);
		return y * Math.cos(theta) + x * Math.sin(theta) + cy;
	}


	public double getLeftCircleX()
    {
	return leftX;
    }

    public double getLeftCircleY()
    {
	return leftY;
    }

    public double getRightCircleX()
    {
	return rightX;
    }

    public double getRightCircleY()
    {
	return rightY;
    }

    public double getTopLineX1()
    {
	return ctopLeftX;
    }

    public double getTopLineY1()
    {
	return ctopLeftY;
    }

    public double getTopLineX2()
    {
	return ctopRightX;
    }

    public double getTopLineY2()
    {
	return ctopRightY;
    }

    public double getBottomLineX1()
    {
	return cbottomLeftX;
    }

    public double getBottomLineY1()
    {
	return cbottomLeftY;
    }

    public double getBottomLineX2()
    {
	return cbottomRightX;
    }

    public double getBottomLineY2()
    {
	return cbottomRightY;
    }
    
    @Override
	public boolean rotate() {
		if(this.orientation == 3) {
			this.orientation = 0;
		} else {
			this.orientation++;
		}
		this.build();
		this.addToSets();
		
		return true;
	}
public boolean getDead(){
    	return  false;//FLippers cant die
}
 public 	void triggerOnce(){
	 triggered = true;

 }

}

