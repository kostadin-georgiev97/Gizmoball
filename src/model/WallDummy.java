package model;

import java.awt.Color;
import java.util.Set;

import model.physics.Circle;
import model.physics.LineSegment;

public class WallDummy implements iPlaceable {

    @Override
    public void setTriggerType(TriggerType type) {

    }

    @Override
    public TriggerType getTriggerType() {
        return null;
    }

    @Override
    public boolean getDead() {
        return false;
    }

    @Override
    public int getXStartPos() {
        return 0;
    }

    @Override
    public int getYStartPos() {
        return 0;
    }

    @Override
    public int getXEndPos() {
        return 0;
    }

    @Override
    public int getYEndPos() {
        return 0;
    }

    @Override
    public int getOrientation() {
        return 0;
    }

    @Override
    public Color getColour() {
        return null;
    }

    @Override
    public void setColour(Color colour) {

    }

    @Override
    public Set<iPlaceable> getConnectedGizmos() {
        return null;
    }

    @Override
    public void disconnectConnectedGizmos() {

    }

    @Override
    public void connectGizmo(iPlaceable placeable1) {

    }

    @Override
    public void trigger() {

    }

    @Override
    public Set<LineSegment> getLines() {
        return null;
    }

    @Override
    public Set<Circle> getCircles() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public GizmoType getGizmoType() {
        return null;
    }

    @Override
    public void setXStartPos(int xPos) {

    }

    @Override
    public void setYStartPos(int yPos) {

    }

    @Override
    public void setXEndPos(int xPos) {

    }

    @Override
    public void setYEndPos(int yPos) {

    }

    @Override
    public void setOrientation(int rotation) {

    }

    @Override
    public boolean rotate() {
        return false;
    }

    @Override
    public int getKeyCode() {
        return 0;
    }

    @Override
    public boolean setKeyCode(int keyCode) {
        return false;
    }

    @Override
    public boolean setKeyFunc(KeyFunc key) {
        return false;
    }

    @Override
    public KeyFunc getKeyFunc() {
        return null;
    }

    @Override
    public boolean disconnectKey() {
        return false;
    }

    @Override
    public void connectWalls() {

    }

    @Override
    public void disconnectWalls() {

    }

    @Override
    public boolean isConnectedWalls() {
        return false;
    }

    @Override
    public void triggerOnce() {

    }


}
