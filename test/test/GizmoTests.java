package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import model.Ball;
import model.GameBoardModel;
import model.Gizmo;
import model.GizmoType;
import model.iPlaceable;
import model.Bumpers.SquareBumper;

class GizmoTests {

	@Test
	void testHitTriggering() {
		Gizmo sqb = new SquareBumper("tst", GizmoType.Square, 3, 3, Color.red);
		Ball ball = new Ball("ball",0,0,1,1);
		assertTrue(sqb.hit(ball));
		assertFalse(sqb.hit(ball));
		sqb.resetHit();
		assertFalse(sqb.hit(ball));
		sqb.resetHit();
		sqb.resetHit();
		assertTrue(sqb.hit(ball));
	}
	
	@Test
	void checkNoNulls() {
		Gizmo sqb = new SquareBumper("tst", GizmoType.Square, 3, 3, Color.red);
		assertEquals(sqb.getColour(), Color.red);
		assertNotNull(sqb.getName());
		assertNotNull(sqb.getKeyCode());
		assertEquals(sqb.getGizmoType(), GizmoType.Square);
	}
	
	@Test
	void testDeleteGizmo() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<>());
		gmb.placeGizmo(GizmoType.Square, 4, 4);
		assertTrue(gmb.deleteGizmoAt(4, 4));
	}
	
	@Test
	void testDeleteAbsentGizmo() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<>());
		assertFalse(gmb.deleteGizmoAt(4, 4));
	}
	
	@Test
	void testPlaceBumpers() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<>());
		gmb.placeGizmo(GizmoType.Square, 1, 1);
		gmb.placeGizmo(GizmoType.Square, 1, 2);
		gmb.placeGizmo(GizmoType.Triangle, 2, 1);
		gmb.placeGizmo(GizmoType.Triangle, 2, 2);
		gmb.placeGizmo(GizmoType.Circle, 3, 1);
		gmb.placeGizmo(GizmoType.Circle, 3, 2);
		gmb.placeGizmo(GizmoType.Square, 3, 2); //Check it doesn't override
		
		assertEquals(GizmoType.Square, gmb.getTileAt(1, 1).getGizmoType());
		assertEquals(GizmoType.Square, gmb.getTileAt(1, 2).getGizmoType());
		assertEquals(GizmoType.Triangle, gmb.getTileAt(2, 1).getGizmoType());
		assertEquals(GizmoType.Triangle, gmb.getTileAt(2, 1).getGizmoType());
		assertEquals(GizmoType.Circle, gmb.getTileAt(3, 1).getGizmoType());
		assertEquals(GizmoType.Circle, gmb.getTileAt(3, 2).getGizmoType());
	}
	
	@Test
	void testMoveGizmoLegal() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<iPlaceable>());
		
		gmb.placeGizmo(GizmoType.Circle, 5, 5);
		gmb.moveGizmo(5, 5, 3, 3);
		
		assertNull(gmb.getTileAt(5, 5));
		assertEquals(GizmoType.Circle, gmb.getTileAt(3, 3).getGizmoType());
	}
	
	@Test
	void testMoveGizmoIllegal() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<iPlaceable>());
		
		gmb.placeGizmo(GizmoType.Circle, 5, 5);
		gmb.placeGizmo(GizmoType.Square, 3, 3);
		gmb.moveGizmo(5, 5, 3, 3);
		
		assertEquals(GizmoType.Circle, gmb.getTileAt(5, 5).getGizmoType());
		assertEquals(GizmoType.Square, gmb.getTileAt(3, 3).getGizmoType());
	}
	
	@Test
	void testMoveAbsorberLegal() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<iPlaceable>());
		
		gmb.placeAbsorber(1, 1, 3, 3);
		gmb.moveGizmo(1, 1, 4, 4);
		
		assertNull(gmb.getTileAt(2, 2));
		assertEquals(GizmoType.Absorber, gmb.getTileAt(4, 4).getGizmoType());
		assertEquals(GizmoType.Absorber, gmb.getTileAt(5, 5).getGizmoType());
		assertEquals(GizmoType.Absorber, gmb.getTileAt(6, 6).getGizmoType());
	}
	
	@Test
	void testMoveAbsorberIllegal() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<iPlaceable>());
		
		gmb.placeAbsorber(1, 1, 3, 3);
		gmb.placeGizmo(GizmoType.Square, 5, 5);
		gmb.moveGizmo(1, 1, 4, 4);
		
		assertNull(gmb.getTileAt(4, 4));
		assertEquals(GizmoType.Absorber, gmb.getTileAt(2, 2).getGizmoType());
		assertEquals(GizmoType.Square, gmb.getTileAt(5, 5).getGizmoType());
	}
	
	@Test
	void testReset() {
		GameBoardModel gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<iPlaceable>());
		
		gmb.placeAbsorber(1, 1, 3, 3);
		gmb.placeGizmo(GizmoType.Square, 5, 5);
		gmb.resetGameBoard();
		assertEquals(0, gmb.getBalls().size());
		assertEquals(0, gmb.getTiles().size());
	}

}
