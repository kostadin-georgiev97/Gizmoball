package test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Ball;
import model.GameBoardModel;
import model.physics.Angle;
import model.physics.Vect;

class BallTests {
	
	GameBoardModel gmb;
	
	@BeforeEach
	public void beforeEach() {
		gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<>());
		Ball ball = new Ball("b1",0.1,0.1,0.2,0.2);
		Set<Ball> balls = new HashSet<Ball>();
		balls.add(ball);
		gmb.setBalls(balls);
		System.out.println("Setup done");
	}

	@Test
	void testGravityFromStatic() {
		Vect correctAnswer = new Vect(Angle.DEG_90, gmb.getGravityValue());
		assertEquals(correctAnswer, gmb.getGravityVector(1));
	}
		 
	
	@Test
	void testFriction() {
		beforeEach();
		Vect initial = new Vect(1,0);
		//Vect expected = new Vect(0.95,0);
		Double expected = 0.99875;
		assertEquals(expected, (Double)gmb.getFrictionLength(initial, 0.05));
	}
	
	@Test
	void testAbsorber() {
		Ball ball = gmb.getBalls().iterator().next();
		ball.setXY(5, 5);
		ball.setVelo(new Vect(0,0));
		gmb.placeAbsorber(0, 7, 20, 8);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		gmb.moveBall(ball);
		System.out.println(ball.getX() + " " + ball.getY());
		assertEquals(ball.getVelo(), new Vect(0,0));
	}
	
	@Test
	void assignBallRadius() {
		Ball ball = gmb.getBalls().iterator().next();
		ball.setBallRadius(1);
		assertEquals((Double)1.0, (Double)ball.getRadius());
		ball.setBallRadius(20);
		assertEquals((Double)20.0, (Double)ball.getRadius());
		ball.setBallRadius(100);
		assertEquals((Double)100.0, (Double)ball.getRadius());
		ball.setBallRadius(101);
		assertEquals((Double)100.0, (Double)ball.getRadius());
		ball.setBallRadius(0.1);
		assertEquals((Double)1.0, (Double)ball.getRadius());
	}
	
	@Test
	void assignBallFriction() {
		Ball ball = gmb.getBalls().iterator().next();
		ball.setBallFriction(-0.1);
		assertEquals((Double)0.0, (Double)ball.getBallFriction());
		ball.setBallFriction(20);
		assertEquals((Double)20.0, (Double)ball.getBallFriction());
		ball.setBallFriction(100);
		assertEquals((Double)100.0, (Double)ball.getBallFriction());
		ball.setBallFriction(101);
		assertEquals((Double)100.0, (Double)ball.getBallFriction());
		ball.setBallFriction(0.0);
		assertEquals((Double)0.0, (Double)ball.getBallFriction());
	}
	
	@Test
	void testSetVelo() {
		Ball ball = gmb.getBalls().iterator().next();
		ball.setVelo(new Vect(Angle.DEG_135, 1.0));
		assertEquals((Double)1.0, (Double)ball.getVelo().length());
		ball.setVelo(new Vect(Angle.DEG_135, 0.0));
		assertEquals((Double)0.0, (Double)ball.getVelo().length());
		ball.setVelo(new Vect(Angle.DEG_135, -1.0));
		assertEquals((Double)1.0, (Double)ball.getVelo().length());
		ball.setVelo(new Vect(Angle.DEG_135, 199.0));
		assertEquals((Double)199.0, (Double)ball.getVelo().length());
		ball.setVelo(new Vect(Angle.DEG_135, 200.0));
		assertEquals((Double)200.0, (Double)ball.getVelo().length());
		ball.setVelo(new Vect(Angle.DEG_135, 201.0));
		assertEquals((Double)200.0, (Double)ball.getVelo().length());
	}
	
	@Test
	void testSetXYVelo() {
		Ball ball = gmb.getBalls().iterator().next();
		ball.setXYVelo(1.0, 0.0);
		assertEquals((Double)1.0, (Double)ball.getVelo().length());
		assertEquals((Double)1.0, (Double)ball.getXVelo());
		assertEquals((Double)0.0, (Double)ball.getYVelo());
		ball.setXYVelo(0.0, 0.0);
		assertEquals((Double)0.0, (Double)ball.getVelo().length());
		ball.setXYVelo(-1.0, 0.0);
		assertEquals((Double)1.0, (Double)ball.getVelo().length());
		ball.setXYVelo(199.0, 0.0);
		assertEquals((Double)199.0, (Double)ball.getVelo().length());
		ball.setXYVelo(0.0, 200.0);
		assertEquals((Double)200.0, (Double)ball.getVelo().length());
		ball.setXYVelo(201.0, 0.0);
		assertEquals((Double)200.0, (Double)ball.getVelo().length());
	}
	
	@Test
	void testName() {
		Ball ball = gmb.getBalls().iterator().next();
		Set<Ball> balls = new HashSet<Ball>();
		balls.add(ball);
		ball.setName("TestName");
		assertEquals("TestName", ball.getName());
	}
	
	@Test
	void testColour() {
		Ball ball = gmb.getBalls().iterator().next();
		ball.setColour(Color.DARK_GRAY);
		assertEquals(Color.DARK_GRAY, ball.getColour());
	}

}
