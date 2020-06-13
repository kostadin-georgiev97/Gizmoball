package model.Bumpers;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.GizmoType;
import model.iPlaceable;
import model.physics.Circle;
import model.physics.LineSegment;

public class RoundBumper extends Bumper {

	public RoundBumper(String name, GizmoType type, int x, int y, Color colour, Set<iPlaceable> triggers,
					   Set<Integer> keyCodes) {
		super(name, type,x, y, 0, colour, triggers, keyCodes);
	}
	
	public RoundBumper(String name, GizmoType type,int x, int y, Color colour) {
		super(name, type,x, y, 0, colour);
	}

	@Override
	public Set<LineSegment> getLines() {
		Set<model.physics.LineSegment> lines = new HashSet<model.physics.LineSegment>();
		return lines;
	}

	@Override
	public Set<Circle> getCircles() {
		Set<model.physics.Circle> circles = new HashSet<model.physics.Circle>();
		circles.add(new model.physics.Circle(x+0.5, y+0.5, 0.5));
		return circles;
	}



	@Override
	public boolean rotate() {
		return false;
	}

}
