package model;

import java.awt.Color;
import java.util.Set;

public interface iPlaceable {
	void setTriggerType(TriggerType type);

	TriggerType getTriggerType();

	boolean getDead();

	
	int getXStartPos();

	int getYStartPos();

	int getXEndPos();

	int getYEndPos();

	int getOrientation();

	Color getColour();

	void setColour(Color colour);

	Set<iPlaceable> getConnectedGizmos();

	void disconnectConnectedGizmos();

	void connectGizmo(iPlaceable placeable1);

	void trigger();

	Set<model.physics.LineSegment> getLines();

	Set<model.physics.Circle> getCircles();

	String getName();

	GizmoType getGizmoType();

	void setXStartPos(int xPos);

	void setYStartPos(int yPos);

	void setXEndPos(int xPos);

	void setYEndPos(int yPos);

	void setOrientation(int rotation);

	boolean rotate();


	int getKeyCode();

	boolean setKeyCode(int keyCode);

	boolean setKeyFunc(KeyFunc key);

	KeyFunc getKeyFunc();

	boolean disconnectKey();

	void connectWalls();
	void disconnectWalls();
	boolean isConnectedWalls();

	void triggerOnce();



	}