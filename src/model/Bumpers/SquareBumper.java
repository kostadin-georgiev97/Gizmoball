package model.Bumpers;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.GizmoType;
import model.iPlaceable;
import model.physics.Circle;
import model.physics.LineSegment;

public class SquareBumper extends Bumper {

	public SquareBumper(String name, GizmoType type, int x, int y, Color colour, Set<iPlaceable> triggers,
						Set<Integer> keyCodes) {
		super(name, type,x, y, 0, colour, triggers, keyCodes);
	}
	
	public SquareBumper(String name, GizmoType type,int x, int y, Color colour) {
		super(name, type,x, y, 0, colour);
	}


	@Override
	public Set<LineSegment> getLines() {
		Set<model.physics.LineSegment> lines = new HashSet<model.physics.LineSegment>();
		lines.add(new  LineSegment(x, y, x+1, y));
		lines.add(new model.physics.LineSegment(x, y+1, x+1, y+1));
		lines.add(new model.physics.LineSegment(x, y, x, y+1));
		lines.add(new model.physics.LineSegment(x+1, y, x+1, y+1));
		return lines;
	}

	@Override
	public Set<Circle> getCircles() {
		Set<model.physics.Circle> circles = new HashSet<model.physics.Circle>();
		circles.add(new model.physics.Circle(x, y, 0));
		circles.add(new model.physics.Circle(x+1, y, 0));
		circles.add(new model.physics.Circle(x, y+1, 0));
		circles.add(new model.physics.Circle(x+1, y+1, 0));
		return circles;
	}



	@Override
	public boolean rotate() {
		return false;
	}

}
