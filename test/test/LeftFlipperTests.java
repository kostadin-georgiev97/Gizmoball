package test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import model.Ball;
import model.GameBoardModel;
import model.GizmoType;
import model.Flipper.LeftFlipper;
import model.physics.Circle;
import model.physics.Vect;

public class LeftFlipperTests {
	
	GameBoardModel gmb = new GameBoardModel();
	LeftFlipper flipper; //Using concrete because this is a test class

	@BeforeEach
	public void beforeEach() {
		gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<>());
		Ball ball = new Ball("b1",0.1,0.1,0.2,0.2);
		Set<Ball> balls = new HashSet<Ball>();
		balls.add(ball);
		gmb.setBalls(balls);
		gmb.placeGizmo(GizmoType.LeftFlipper, 5, 5);
		flipper = (LeftFlipper)gmb.getTileAt(5, 5);
		System.out.println(flipper.hashCode());
		System.out.println("Setup done");
	}
	
	@Test
	public void testFlipperStructure() {
		beforeEach();
		assertEquals(6, flipper.getCircles().size());
		assertEquals(2, flipper.getLines().size());
	}
	
	@Test
	public void testFlipperPositions() {
		beforeEach();
		Vect initialCentre = getLeftmostCircle(flipper.getCircles()).getCenter();
		flipper.trigger();
		for(int i = 0; i < 10; i++) {
			//Check that the leftmost circle never moves, as it's the pivot
			assertEquals(initialCentre, getLeftmostCircle(flipper.getCircles()).getCenter());
		}
	}
	
	public Circle getLeftmostCircle(Set<Circle> circles) {
		Circle leftMost = null;
		for(Circle circle : circles) {
			if(leftMost == null || circle.getCenter().x() < leftMost.getCenter().x()) {
				leftMost = circle;
			}
		}
		return leftMost;
	}
	
}
