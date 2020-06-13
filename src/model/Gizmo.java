package model;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public abstract class Gizmo implements iPlaceable {
    private String name;
    private GizmoType type;
    private Color colour;
    int lastHit = 2;
    private int keyCode;
    private boolean isKeySet;

    private KeyFunc keyFunc;
    private boolean dead;
    private HashSet<iPlaceable> triggers;
    private boolean triggered;
    private boolean wallConnection;

    public Gizmo(String name, GizmoType type, Color colour) {
        dead = false;
        this.name = name;
        this.type = type;
        this.colour = colour;
        this.isKeySet = false;
        this.keyFunc = KeyFunc.undefined;
        this.keyCode = KeyEvent.VK_UNDEFINED;
        triggers = new HashSet<>();
        triggered=false;
        wallConnection = false;

    }

    public String getName() {
        return this.name;
    }

    public GizmoType getGizmoType() {
        return this.type;
    }

    public abstract int getXStartPos();

    public abstract int getYStartPos();

    public abstract int getXEndPos();

    public abstract int getYEndPos();

    public abstract int getOrientation();

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }


    public abstract void trigger();

    public abstract Set<model.physics.LineSegment> getLines();

    public abstract Set<model.physics.Circle> getCircles();


    public abstract void setXStartPos(int xPos);

    public abstract void setYStartPos(int yPos);

    public abstract void setXEndPos(int xPos);

    public abstract void setYEndPos(int yPos);

    public abstract void setOrientation(int rotation);

    public abstract boolean rotate();


    public void connectWalls() {
        wallConnection=true;
    }
    public void disconnectWalls(){
        wallConnection=false;
    }

    public void resetHit() {
        if (lastHit < 2) {
            lastHit++;
        }
    }
    
    public boolean hit(Ball ball) {
    	Boolean success = false;
    	if(lastHit == 2) {
    		success = true;
    		trigger();
    	}
    	lastHit = 0;
    	return success;
    }
    
    /**
     * Returns the key code if it is set,
     * otherwise returns undefined
     */
    public int getKeyCode() {
        if (this.isKeySet) {
            return this.keyCode;
        }
        return KeyEvent.VK_UNDEFINED;
    }

    public boolean setKeyCode(int keyCode) {
        if (this.isKeySet) {
            return false;
        }
        this.keyCode = keyCode;
        isKeySet = true;
        return true;
    }

    public boolean disconnectKey() {
        if (!this.isKeySet) {
            return false;
        }
        this.keyCode = KeyEvent.VK_UNDEFINED;
        this.keyFunc = KeyFunc.undefined;

        isKeySet = false;
        return true;
    }

    @Override
    public boolean setKeyFunc(KeyFunc key) {

        this.keyFunc = key;
        return true;
    }

    @Override
    public KeyFunc getKeyFunc() {
        return keyFunc;
    }


    public void setDead() {

        dead = true;

    }

    public boolean getDead() {

        return dead;
    }

    public TriggerType getTriggerType() {
        return TriggerType.NONE;
    }

    @Override
    public void setTriggerType(TriggerType type) {

    }

    public void triggerConnectedGizos() {
        for (iPlaceable placeable : triggers
        ) {

            placeable.triggerOnce();

        }

    }

    @Override
    public void disconnectConnectedGizmos() {
        triggers = new HashSet<>();

    }
    public void connectGizmo(iPlaceable placeable1) {
        triggers.add(placeable1);

    }
    @Override
    public Set<iPlaceable> getConnectedGizmos() {
        return triggers;
    }
    public boolean isTriggered(){
        return triggered;
    }
    public void setTriggered(){
        triggered=true;
    }
    public void resetTriggered(){
        triggered =false;
    }

    @Override
    public boolean isConnectedWalls() {
        return wallConnection;
    }

}
