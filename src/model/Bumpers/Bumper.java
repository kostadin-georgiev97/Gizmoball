package model.Bumpers;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import model.Gizmo;
import model.GizmoType;
import model.TriggerType;
import model.iPlaceable;

public abstract class Bumper extends Gizmo {

	int x;
	int y;
	int orientation;
	Color colour;
	TriggerType triggerType;

	public Bumper(String name, GizmoType type, int x, int y, int orientation, Color colour) {
		this(name, type, x, y, orientation, colour, new HashSet<iPlaceable>(), new HashSet<Integer>());
	}

	public Bumper(String name, GizmoType type, int x, int y, int orientation, Color colour, Set<iPlaceable> triggers, Set<Integer> keyCodes) {
		super(name,type,colour);
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.colour = colour;
		triggerType = TriggerType.NONE;
	}


	@Override
	public int getXStartPos() {
		return x;
	}
	@Override
	public int getYStartPos() {
		return y;
	}

	public int getXEndPos() {
		return x + 1;
	}

	public int getYEndPos() {
		return y + 1;
	}

	@Override
	public int getOrientation() {
		return orientation;
	}


	public void trigger() {
		//triggerConnectedGizmos
	super.triggerConnectedGizos();

		//Bumpers have no action
		carryOutTrigger();

	}

	public void carryOutTrigger() {
		Gizmo copy = this;
		super.setTriggered();
		switch (triggerType){
			case NONE:
				resetTriggered();
				return;
			case RANDOM_COLOUR:
				float r = Math.round(Math.random() * 255);
				float g = Math.round(Math.random() * 255);
				float b = Math.round(Math.random() * 255);
				super.setColour(Color.getHSBColor(r,g,b));
				resetTriggered();
				break;
			case DEAD:
				resetTriggered();//no point waiting around for this only used in switch to build mode
				Thread t = new Thread(){
					@Override
					public void run() {
						try {
							Thread.sleep(new Random().nextInt(10000));
							copy.setDead();
							resetTriggered();
						} catch (InterruptedException e) {
							System.out.println("Aborting thread");
							resetTriggered();
						}
					}
				};t.start();
				break;
			case TRIANGLE_ROTATE:
				resetTriggered();//same reason as above
				if(this instanceof TriangleBumper) {
					Thread t2 = new Thread(){
						@Override
						public void run() {
							try {
								Thread.sleep(500);
								copy.rotate();
								resetTriggered();
							} catch (InterruptedException e) {
								System.out.println("Aborting thread");
								resetTriggered();
							}
						}
					};t2.start();
					
				}
				break;
		}
	}

	public void triggerOnce() {
		carryOutTrigger();
	}

	@Override
	public void setXStartPos(int xPos) {
		this.x = xPos;
	}

	@Override
	public void setYStartPos(int yPos) {
		this.y = yPos;
	}

	@Override
	public void setXEndPos(int xPos) {
		
	}

	@Override
	public void setYEndPos(int yPos) {
		
	}

	public void setOrientation(int rotation) {
		orientation =rotation;

	}


	public void setTriggerType(TriggerType type){
		triggerType=type;

	}
	@Override
	public TriggerType getTriggerType() {
		return  triggerType;
	}

	}




