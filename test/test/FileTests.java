package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import model.GameBoardModel;
import model.GizmoType;
import model.iPlaceable;
import model.FileManagement.FileLoader;
import model.FileManagement.FileSaver;

public class FileTests {
	
	GameBoardModel gmb;
	
	@BeforeEach
	public void init() {
		gmb = new GameBoardModel();
		gmb.setTiles(new HashSet<>());
		System.out.println("Setup done");
	}
	
	
	@Test
	public void testWorkingFile() {
		init();
		FileLoader fl = new FileLoader();
		try {
			Set<iPlaceable> tiles = fl.loadLayout("testA.txt");
			System.out.println(tiles.size());
			gmb.setTiles(tiles);
			System.out.println("Added");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(gmb.getTileAt(8, 7).getGizmoType().equals(GizmoType.Triangle));
		assertTrue(gmb.getTileAt(4, 4).getGizmoType().equals(GizmoType.Circle));
		assertTrue(gmb.getTileAt(0, 18).getGizmoType().equals(GizmoType.Absorber));
		assertTrue(gmb.getTileAt(19, 19).getGizmoType().equals(GizmoType.Absorber));
		assertTrue(gmb.getTileAt(20, 20) == null);
		
		assertEquals(1, fl.returnBalls().size());
		assertEquals((Double)0.25, (Double)fl.returnBalls().iterator().next().getRadius());
		
	}
	
	@Test(expected = IOException.class)
	public void testBrokenFile() throws Exception {
		FileLoader fl = new FileLoader();
		Set<iPlaceable> tiles = fl.loadLayout("testB.txt");
	}
	
	@Test
	public void testTempSaveLoad() {
		init();
		gmb.placeAbsorber(1, 1, 3, 3);
		gmb.placeGizmo(GizmoType.Square, 5, 5);
		gmb.getTileAt(5, 5).setColour(Color.pink);
		gmb.saveTempBoard();
		gmb.deleteGizmoAt(1, 1);
		gmb.deleteGizmoAt(5, 5);
		
		assertNull(gmb.getTileAt(1, 1));
		assertNull(gmb.getTileAt(5, 5));
		
		gmb.loadTempBoard();
		
		assertEquals(GizmoType.Absorber, gmb.getTileAt(2, 2).getGizmoType());
		assertEquals(GizmoType.Square, gmb.getTileAt(5, 5).getGizmoType());
		assertEquals(Color.pink, gmb.getTileAt(5, 5).getColour());
	}
	
	@Test
	public void testNameSaveLoad() throws Exception {
		init();
		gmb.placeAbsorber(1, 1, 3, 3);
		gmb.placeGizmo(GizmoType.Square, 5, 5);
		gmb.getTileAt(5, 5).setColour(Color.pink);
		FileSaver fs = new FileSaver();
		fs.saveLayout("test.tst", gmb.getTiles(), gmb.getBalls(), false, gmb.getFrictionValues(), gmb.getGravityValue());
		gmb.deleteGizmoAt(1, 1);
		gmb.deleteGizmoAt(5, 5);
		
		assertNull(gmb.getTileAt(1, 1));
		assertNull(gmb.getTileAt(5, 5));
		gmb.resetGameBoard();
		FileLoader fl = new FileLoader();
		gmb.setTiles(fl.loadLayout("test.tst"));
		gmb.setBalls(fl.returnBalls());
		gmb.setFrictionValues(fl.returnFrictionValues());
		gmb.setGravityValue(fl.returnGravity());
		
		assertEquals(GizmoType.Absorber, gmb.getTileAt(2, 2).getGizmoType());
		assertEquals(GizmoType.Square, gmb.getTileAt(5, 5).getGizmoType());
		assertEquals(Color.pink, gmb.getTileAt(5, 5).getColour());
	}
	
	@Test
	public void testFileSaveLoad() throws Exception {
		init();
		File file = new File("test.tst");
		gmb.placeAbsorber(1, 1, 3, 3);
		gmb.placeGizmo(GizmoType.Square, 5, 5);
		gmb.getTileAt(5, 5).setColour(Color.pink);
		FileSaver fs = new FileSaver();
		fs.saveLayout(file, gmb.getTiles(), gmb.getBalls(), gmb.getFrictionValues(), gmb.getGravityValue());
		gmb.deleteGizmoAt(1, 1);
		gmb.deleteGizmoAt(5, 5);
		
		assertNull(gmb.getTileAt(1, 1));
		assertNull(gmb.getTileAt(5, 5));
		gmb.resetGameBoard();
		FileLoader fl = new FileLoader();
		gmb.setTiles(fl.loadLayout(file));
		gmb.setBalls(fl.returnBalls());
		gmb.setFrictionValues(fl.returnFrictionValues());
		gmb.setGravityValue(fl.returnGravity());
		
		assertEquals(GizmoType.Absorber, gmb.getTileAt(2, 2).getGizmoType());
		assertEquals(GizmoType.Square, gmb.getTileAt(5, 5).getGizmoType());
		assertEquals(Color.pink, gmb.getTileAt(5, 5).getColour());
	}
	
	

}
