package model.Flipper;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.GizmoType;
import model.physics.Circle;
import model.physics.LineSegment;

public class LeftFlipper extends Flipper
{
    public LeftFlipper(String name, GizmoType type, int xPos, int yPos, int orientation, Color colour) {
    	super(name, type, xPos, yPos, orientation, colour);
    }



	public void updateRotation() // call with every tick
	{
		Set<LineSegment> nLines = new HashSet<>();
		
		Set<Circle> nCircles = new HashSet<>();

		updateAngle();
		rightX = newX(initRightX, initRightY, initLeftX, initLeftY, angle);
		rightY = newY(initRightX, initRightY, initLeftX, initLeftY, angle);
		rightCircle = new Circle(rightX, rightY, 0.25);

		double xLeft, yLeft, xRight, yRight;

		xLeft = newX(topLeftX, topLeftY, initLeftX, initLeftY, angle);
		yLeft = newY(topLeftX, topLeftY, initLeftX, initLeftY, angle);
		xRight = newX(topRightX, topRightY, initLeftX, initLeftY, angle);
		yRight = newY(topRightX, topRightY, initLeftX, initLeftY, angle);
		topLine = new LineSegment(xLeft, yLeft, xRight, yRight);

		ctopLeftX = xLeft;
		ctopRightX = xRight;
		ctopRightY = yRight;

		xLeft = newX(bottomLeftX, bottomLeftY, initLeftX, initLeftY, angle);
		yLeft = newY(bottomLeftX, bottomLeftY, initLeftX, initLeftY, angle);
		xRight = newX(bottomRightX, bottomRightY, initLeftX, initLeftY, angle);
		yRight = newY(bottomRightX, bottomRightY, initLeftX, initLeftY, angle);
		bottomLine = new LineSegment(xLeft, yLeft, xRight, yRight);

		cbottomLeftX = xLeft;
		cbottomLeftY = yLeft;
		cbottomRightX = xRight;
		cbottomRightY = yRight;

		nLines.add(topLine);
		nLines.add(bottomLine);
		nCircles.add(leftCircle);
		nCircles.add(rightCircle);
		lines = nLines;
		circles = nCircles;
	}


}
