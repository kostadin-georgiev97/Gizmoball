package model.Flipper;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.GizmoType;
import model.physics.Circle;
import model.physics.LineSegment;

public class RightFlipper extends Flipper {

    public RightFlipper(String name, GizmoType type, int xPos, int yPos, int orientation, Color colour) {
        super(name, type, xPos, yPos, orientation, colour);
    }



    public void updateRotation() // call with every tick
    {
        Set<LineSegment> nLines = new HashSet<>();
        Set<Circle> nCircles = new HashSet<>();

        updateAngle();
        leftX = newX(initLeftX, initLeftY, initRightX, initRightY, -angle);
        leftY = newY(initLeftX, initLeftY, initRightX, initRightY, -angle);
        leftCircle = new Circle(leftX, leftY, 0.25);

        double xLeft, yLeft, xRight, yRight;

        xLeft = newX(topLeftX, topLeftY, initRightX, initRightY, -angle);
        yLeft = newY(topLeftX, topLeftY, initRightX, initRightY, -angle);
        xRight = newX(topRightX, topRightY, initRightX, initRightY, -angle);
        yRight = newY(topRightX, topRightY, initRightX, initRightY, -angle);
        topLine = new LineSegment(xLeft, yLeft, xRight, yRight);

        ctopLeftX = xLeft;
        ctopLeftY = yLeft;
        ctopRightX = xRight;
        ctopRightY = yRight;

        xLeft = newX(bottomLeftX, bottomLeftY, initRightX, initRightY, -angle);
        yLeft = newY(bottomLeftX, bottomLeftY, initRightX, initRightY, -angle);
        xRight = newX(bottomRightX, bottomRightY, initRightX, initRightY, -angle);
        yRight = newY(bottomRightX, bottomRightY, initRightX, initRightY, -angle);
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


