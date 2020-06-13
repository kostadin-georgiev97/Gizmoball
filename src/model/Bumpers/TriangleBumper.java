package model.Bumpers;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.GizmoType;
import model.iPlaceable;
import model.physics.Circle;
import model.physics.LineSegment;

public class TriangleBumper extends Bumper {

	public TriangleBumper(String name, GizmoType type, int x, int y, int orientation, Color colour, Set<iPlaceable> triggers,
						  Set<Integer> keyCodes) {
		super(name, type,x, y, orientation, colour, triggers, keyCodes);
	}
	
	public TriangleBumper(String name, GizmoType type, int x, int y, int orientation, Color colour) {
		super(name, type, x, y, orientation, colour);
	}

	public TriangleBumper(String name, GizmoType type, int x, int y, Color colour) {
		super(name, type, x, y, 0,colour);
	}

	@Override
	public Set<LineSegment> getLines() {
		Set<model.physics.LineSegment> lines = new HashSet<model.physics.LineSegment>();
		
		switch(orientation%4) {
			case 0:
				lines.add(new  LineSegment(x, y, x+1, y));
				lines.add(new model.physics.LineSegment(x, y, x, y+1));
				lines.add(new model.physics.LineSegment(x+1, y, x, y+1));
				break;
			case 1:
				lines.add(new  LineSegment(x, y, x+1, y));
				lines.add(new model.physics.LineSegment(x+1, y, x+1, y+1));
				lines.add(new model.physics.LineSegment(x, y, x+1, y+1));
				break;
			case 2:
				lines.add(new  LineSegment(x, y+1, x+1, y+1));
				lines.add(new model.physics.LineSegment(x+1, y, x, y+1));
				lines.add(new model.physics.LineSegment(x+1, y, x+1, y+1));
				break;
			case 3:
				lines.add(new  LineSegment(x, y+1, x+1, y+1));
				lines.add(new model.physics.LineSegment(x, y, x, y+1));
				lines.add(new model.physics.LineSegment(x, y, x+1, y+1));
				break;
		}
		
		return lines;
	}

	@Override
	public Set<Circle> getCircles() {
		Set<model.physics.Circle> circles = new HashSet<model.physics.Circle>();
		switch(orientation%4) {
		case 0:
			circles.add(new model.physics.Circle(x, y, 0));
			circles.add(new model.physics.Circle(x+1, y, 0));
			circles.add(new model.physics.Circle(x, y+1, 0));
			break;
		case 1:
			circles.add(new model.physics.Circle(x, y, 0));
			circles.add(new model.physics.Circle(x+1, y, 0));
			circles.add(new model.physics.Circle(x+1, y+1, 0));
			break;
		case 2:
			circles.add(new model.physics.Circle(x+1, y, 0));
			circles.add(new model.physics.Circle(x, y+1, 0));
			circles.add(new model.physics.Circle(x+1, y+1, 0));
			break;
		case 3:
			circles.add(new model.physics.Circle(x, y, 0));
			circles.add(new model.physics.Circle(x, y+1, 0));
			circles.add(new model.physics.Circle(x+1, y+1, 0));
			break;
		}

		return circles;
	}

	/*public void carryOutTrigger() {
		super.carryOutTrigger();
		if(this.triggerType == TriggerType.TRIANGLE_ROTATE) {
			
		}
	}*/
	
	@Override
	public boolean rotate() {
		if(this.orientation == 3) {
			this.orientation = 0;
		} else {
			this.orientation++;
		}
		
		return true;
	}

}
