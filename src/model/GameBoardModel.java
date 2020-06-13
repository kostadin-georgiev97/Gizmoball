package model;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import model.Bumpers.RoundBumper;
import model.Bumpers.SquareBumper;
import model.Bumpers.TriangleBumper;
import model.FileManagement.FileLoader;
import model.FileManagement.FileSaver;
import model.Flipper.Flipper;
import model.Flipper.LeftFlipper;
import model.physics.Angle;
import model.physics.Circle;
import model.physics.Geometry;
import model.physics.Geometry.VectPair;
import model.physics.LineSegment;
import model.physics.Vect;;

@SuppressWarnings("deprecation")
public class GameBoardModel extends Observable {

    Set<iPlaceable> tiles;

    Set<Ball> balls ;
    Set<iPlaceable> wallConnectedGizmos;
    Set<LineSegment> wallLines = new HashSet<>();
    Set<Circle> wallCircles = new HashSet<>();
    double gravityValue = 25;
    double mu = 0.025;
    double mu2 = 0.025;
    private int SquareNameNumber = 0;
    private int CircleNameNumber = 0;
    private int TriangleNameNumber = 0;
    private int RightFlipperNameNumber = 0;
    private int LeftFlipperNameNumber = 0;
    private int AbsorberNameNumber = 0;
    private int BallNameNumber =0;


    private iPlaceable selectedGizmo;


    public GameBoardModel() {
        selectedGizmo =null;
        wallConnectedGizmos = new HashSet<>();



        tiles = new HashSet<>();
        balls = new HashSet<>();
        FileLoader fl = new FileLoader();
        try {
            tiles= fl.loadLayout("temp.txt");
            balls= fl.returnBalls();
            gravityValue = fl.returnGravity();
            setFrictionValues(fl.returnFrictionValues());
            wallConnectedGizmos= fl.returnWallConnectedGizmos();
        } catch (Exception e) {
            tiles = new HashSet<>();
            balls = new HashSet<>();
        }finally {
            addWalls();
        }


    }

    public boolean deleteGizmoAt(int x, int y) {
        iPlaceable gizmo = this.getTileAt(x, y);
        if (gizmo != null) {
            tiles.remove(gizmo);
            return true;
        } else {
            return false;
        }
    }

    public boolean canGizmoMoveAt(iPlaceable gizmo, int destX, int destY) {
        int gizmoWidth = gizmo.getXEndPos() - gizmo.getXStartPos(),
                gizmoHeight = gizmo.getYEndPos() - gizmo.getYStartPos();
        for (int i = destX; i < destX + gizmoWidth; i++) {
            for (int j = destY; j < destY + gizmoHeight; j++) {
                if (this.tileOccupied(i, j)) {
                    return false;
                }
            }
        }
        if (destX + gizmoWidth > 20 || destY + gizmoHeight > 20) {
            return false;
        }

        return true;
    }

    public boolean moveGizmo(int sX, int sY, int tX, int tY) {
        iPlaceable tile = this.getTileAt(sX, sY);

        if (this.canGizmoMoveAt(tile, tX, tY)) {
            if (tile instanceof Absorber) {
                int width = tile.getXEndPos() - tile.getXStartPos(),
                        height = tile.getYEndPos() - tile.getYStartPos();

                tile.setXStartPos(tX);
                tile.setXEndPos(tX + width);
                tile.setYStartPos(tY);
                tile.setYEndPos(tY + height);
            } else {
                tile.setXStartPos(tX);
                tile.setYStartPos(tY);

                if (tile instanceof Flipper) {
                    ((Flipper) tile).build();
                    ((Flipper) tile).addToSets();
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public iPlaceable getTileAt(int x, int y) {
        for (iPlaceable tile : tiles) {
            if (tile.getXStartPos() <= x && tile.getXEndPos() > x && tile.getYStartPos() <= y && tile.getYEndPos() > y) {
                return tile;
            }
        }
        return null;
    }

    public boolean placeAbsorber(int startX, int startY, int endX, int endY) {
        iPlaceable gizmo = new Absorber("abs" + AbsorberNameNumber, GizmoType.Absorber, startX, startY, endX, endY, Color.pink);
        if (this.canGizmoMoveAt(gizmo, startX, startY)) {
            tiles.add(gizmo);
            AbsorberNameNumber++;
            return true;
        } else {
            return false;
        }
    }

    public boolean placeGizmo(GizmoType type, int x, int y) {
        iPlaceable gizmo = null;
        boolean escape = false;
        while (!escape) {//not the best way but should work
            switch (type) {
                case Circle:
                    if (!doesGizmoExist("c" + CircleNameNumber)) {
                        gizmo = new RoundBumper("c" + CircleNameNumber, GizmoType.Circle, x, y, Color.magenta);
                        escape = true;
                    }
                    CircleNameNumber++;
                    break;
                case Square:
                    if (!doesGizmoExist("s" + SquareNameNumber)) {
                        gizmo = new SquareBumper("s" + SquareNameNumber, GizmoType.Square, x, y, Color.blue);
                        escape = true;
                    }
                    SquareNameNumber++;
                    break;
                case Triangle:
                    if (!doesGizmoExist("t" + TriangleNameNumber)) {
                        gizmo = new TriangleBumper("t" + TriangleNameNumber, GizmoType.Triangle, x, y, 0, Color.blue);
                        escape = true;
                    }
                    TriangleNameNumber++;
                    break;
                case LeftFlipper:
                    if (!doesGizmoExist("lf" + LeftFlipperNameNumber)) {
                        gizmo = new LeftFlipper("lf" + LeftFlipperNameNumber, GizmoType.LeftFlipper, x, y, 0, Color.yellow);
                        escape = true;
                    }
                    LeftFlipperNameNumber++;
                    break;
                case RightFlipper:
                    if (!doesGizmoExist("rf" + RightFlipperNameNumber)) {
                        gizmo = new LeftFlipper("rf" + RightFlipperNameNumber, GizmoType.RightFlipper, x, y, 0, Color.yellow);
                        escape = true;
                    }
                    RightFlipperNameNumber++;
                    break;
                default:
                    gizmo = null;
                    break;
            }
        }
        if (this.canGizmoMoveAt(gizmo, x, y)) {
            tiles.add(gizmo);
            return true;
        } else {
            return false;
        }
    }
    public Ball addBall(double x, double y,double xvel,double yvel) {
        boolean escape = false;
        Ball b=null;
        while (!escape){

            if(!doesBallExist("B1"+BallNameNumber)){
                 b = new Ball("B1" + BallNameNumber, x, y,xvel, yvel);
                escape=true;
            }
            BallNameNumber++;
    }
    balls.add(b);
    return b;
    }
    
    public void removeBall(Ball ballToRemove) {
    	balls.remove(ballToRemove);
    }

    public Set<iPlaceable> getTiles() {
        //return Collections.unmodifiableCollection(tiles);
        return tiles;
    }

    public void setTiles(Set<iPlaceable> newTiles) {
        tiles.removeAll(tiles);
        tiles.addAll(newTiles);
    }

	/*public void setBall(Ball b) {
		this.ball = b;
	}*/

    //Where x,y is the top left corner of the tile
    public boolean tileOccupied(int x, int y) {
        for (iPlaceable tile : tiles) {
            if (tile.getXStartPos() <= x && tile.getXEndPos() > x && tile.getYStartPos() <= y && tile.getYEndPos() > y) {
                return true;
            }
        }
        return false;
    }

    public boolean canBePlaced(iPlaceable candidate) {
        for (int x = candidate.getXStartPos(); x < candidate.getXEndPos(); x++) {
            for (int y = candidate.getYStartPos(); y < candidate.getXEndPos(); y++) {
                if (tileOccupied(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Set<model.physics.LineSegment> getLines() {
        Set<model.physics.LineSegment> lines = new HashSet<model.physics.LineSegment>();
        for (iPlaceable tile : tiles) {
            lines.addAll(tile.getLines());
        }
        return lines;
    }


    private void addWalls() {
        Vect topLeft = new Vect(0, 0);
        Vect topRight = new Vect(20, 0);
        Vect bottomLeft = new Vect(0, 20);
        Vect bottomRight = new Vect(20, 20);

        wallLines.add(new LineSegment(topLeft, topRight));
        wallLines.add(new LineSegment(topRight, bottomRight));
        wallLines.add(new LineSegment(bottomRight, bottomLeft));
        wallLines.add(new LineSegment(bottomLeft, topLeft));

        wallCircles.add(new Circle(0, 0, 0));
        wallCircles.add(new Circle(20, 0, 0));
        wallCircles.add(new Circle(0, 20, 0));
        wallCircles.add(new Circle(20, 20, 0));

    }

    public Set<Ball> getBalls() {
        return balls;
    }

    public void setBalls(Set<Ball> balls) {
        this.balls = balls;
    }

    public void moveBall(Ball ball) {
        if (ball.isStationary() == true) {
            return;
        }
        iPlaceable nearObj = null;
        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
        Circle ballCircle = ball.getCircle();
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = ballVelocity;
        double smallestTUC = Double.POSITIVE_INFINITY; //Crazy big so it'll defo update

        int collidee = -1; //0 = line, 1 = circle, 2 = ball
        LineSegment collideLine = null;
        Circle collideCircle = null;
        Ball collideBall = null;
        for (iPlaceable tile : tiles) {
            if (tile instanceof Gizmo) {
                ((Gizmo) tile).resetHit();
            }
            for (LineSegment line : tile.getLines()) {
                double tuc = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                if (tuc < smallestTUC) {
                    smallestTUC = tuc;
                    nearObj = tile;
                    collideLine = line;
                    collidee = 0;
                }
            }
            for (Circle circle : tile.getCircles()) {
                double tuc = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (tuc < smallestTUC) {
                    smallestTUC = tuc;
                    nearObj = tile;
                    collideCircle = circle;
                    collidee = 1;
                }
            }
        }

        for (LineSegment line : wallLines) {
            double tuc = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
            if (tuc < smallestTUC) {
                smallestTUC = tuc;
                nearObj = new WallDummy();
                collideLine = line;
                collidee = 0;
            }
        }

        for (Circle circle : wallCircles) {
            double tuc = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
            if (tuc < smallestTUC) {
                smallestTUC = tuc;
                nearObj = new WallDummy();
                collideCircle = circle;
                collidee = 1;

            }
        }

        // Will need to be for the set of balls
        for (Ball b : balls) {
            double tuc = Geometry.timeUntilBallBallCollision(ball.getCircle(), ball.getVelo(), b.getCircle(), b.getVelo());
            if (tuc < smallestTUC) {
                smallestTUC = tuc;
                nearObj = null;
                collideBall = b;
                collidee = 2;
            }
        }

        if (smallestTUC < 0.05) {

            if (smallestTUC <= 0.0001) {
                switch (collidee) {
                    case 0:
                        newVelo = Geometry.reflectWall(collideLine, ball.getVelo());
                        break;
                    case 1:
                        newVelo = Geometry.reflectCircle(collideCircle.getCenter(), ball.getCircle().getCenter(), ball.getVelo());
                        break;
                    case 2:
                        double massOfBalls = 2;
                        System.out.println(smallestTUC + "VELO1 " + ball.getVelo() + "Velo2 " + collideBall.getVelo());
                        VectPair vp = Geometry.reflectBalls(ball.getCircle().getCenter(), massOfBalls, ball.getVelo(),
                                collideBall.getCircle().getCenter(), massOfBalls, collideBall.getVelo());
                        newVelo = vp.v1;
                        //System.out.println("VELOCITY V1 : " + newVelo);
                        //System.out.println("XY : " + ball.getX() + ", " +  ball.getY() + "X2Y2 : " + collideBall.getX() + ", " +  collideBall.getY());
                        collideBall.setVelo(vp.v2);
                }
            }
            if (nearObj != null) {
                if (nearObj instanceof Gizmo) {
                   
                    if(nearObj instanceof Absorber) {
                    	Absorber a = (Absorber)nearObj;
                    
                    double ballHeight = ball.getDiameter();
        			double absHeight = a.endY - a.startY;
        			double absWidth = a.endX - a.startX;
        			boolean isBlocked = false;
        			
        			if(absHeight>ballHeight && absWidth>ballHeight) {
        				if(ballHeight<=1) {
        					isBlocked = this.tileOccupied(a.endX-1, a.startY-1);
        				}
        				else if(ballHeight<=2) {
        					for(int i=1 ; i<3 ; i++) { //I'd want this to be until the rounded up version of ballHeight but dunno how to
        						for(int j=1 ; j<3 ; j++) {
        							if(tileOccupied(a.endX-i, a.startY-j)) {
        								isBlocked = true;
        							}
        						}
        					}
        				}
        				if(!isBlocked) {
            				((Gizmo) nearObj).hit(ball);
            			}
        			}
        			
                }
                    else {
                    	 ((Gizmo) nearObj).hit(ball);
                    }
            }
                
            }
            tiles.removeIf(iPlaceable::getDead);//one liner for removing gizmos with dead trigger


            if (ball.isStationary() == false) {
                if (newVelo.length() < 0) {
                    System.out.println("BALL : " + ball.getName() + " ; VELOCITY: " + newVelo.length());
                }

                moveBallForTime(ball, smallestTUC);
                if (collidee == 2)
                    newVelo = new Vect(newVelo.angle(), newVelo.length() - ball.getRadius());
                Vect newVelo2 = newVelo.plus(getGravityVector(smallestTUC));
                double f = Math.abs(this.getFrictionLength(newVelo2, smallestTUC));
                Vect v2 = new Vect(newVelo2.angle(), f);
                ball.setVelo(v2);
            }
        } else {
            moveBallForTime(ball, moveTime); //passes in the constant velocity
        }

        setChanged();
        notifyObservers(this);

        System.out.println();

    }

    public void moveBallForTime(Ball b, double moveTime) {
        try {
            Vect ballPosVect = b.getCircle().getCenter();
            Vect ballVelo = b.getVelo();
            Vect scaledUpVelo = ballVelo.times(moveTime);
            Vect newVelo = ballPosVect.plus(scaledUpVelo);
            //ball.setXY(ball.getX() + ball.getVelo().x()*time, ball.getY() + ball.getVelo().y()*time); Another way to do it

            if (moveTime >= 0.05) {
                b.setXY(newVelo.x(), newVelo.y());
                Vect v = b.getVelo().plus(getGravityVector(moveTime));
                double fric = this.getFrictionLength(v, moveTime);

                Vect v2 = new Vect(v.angle(), fric);

                b.setVelo(v2);
            } else {
                b.setXY(newVelo.x(), newVelo.y());

            }


        } catch (NullPointerException e) {
            System.out.println("well nothing had started so what were you thinking");
        }

    }


    /*
     * Spec says gravity should be at 25L/sec^2
     * final velocity = initial velocity + (acceleration*time)
     * Hence here we are returning the (acceleration*time)
     */
    public Vect getGravityVector(double t) {
        Vect grav = new Vect(Angle.DEG_90, getGravityValue() * t);
        return grav; //Degree of 90 is down

    }

    public double getGravityValue() {
        return gravityValue;
    }

    public List<Double> getFrictionValues() {
        List<Double> frictionValues = new ArrayList<>();
        frictionValues.add(mu);
        frictionValues.add(mu2);
        return frictionValues;
    }

    public void setGravityValue(double newGravity) {
        System.out.println(newGravity);
        gravityValue = newGravity;
    }


    public double getFrictionLength(Vect oldVelo, double delta_t) {
        double oldLen = oldVelo.length();

        double newLen;

        newLen = oldLen * (1 /*- mu*delta_t*/ - mu2 * Math.abs(oldLen) * delta_t);
        //else
        //	newLen = oldLen - oldLen*mu;

        return newLen;
    }

    public void setFrictionValues(List<Double> frictionValues) {
        mu = frictionValues.get(0);
        mu2 = frictionValues.get(1);
    }

    public boolean doesGizmoExist(String name) {
        for (iPlaceable gizmo : tiles) {
            if (gizmo.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean doesBallExist(String name) {
        for (Ball b : balls) {
            if (b.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public void resetNameCount(){
         SquareNameNumber = 0;
         CircleNameNumber = 0;
         TriangleNameNumber = 0;
         RightFlipperNameNumber = 0;
        LeftFlipperNameNumber = 0;
         AbsorberNameNumber = 0;
         BallNameNumber =0;
    }
    public void resetGameBoard(){
        balls = new HashSet<>();
        tiles = new HashSet<>();
        wallConnectedGizmos=new HashSet<>();

         gravityValue = 25;
         mu = 0.025;
         mu2 = 0.025;
        resetNameCount();
        notifyObservers();
    }
    public void saveTempBoard(){
        FileSaver fs = new FileSaver();
        try {
            fs.saveLayout("temp.txt",tiles,balls,true,getFrictionValues(),getGravityValue());
        } catch (IOException e) {
            System.out.println("failed to save temp");
        }
    }
    public void loadTempBoard(){
        FileLoader fl = new FileLoader();
        try {
           tiles= fl.loadLayout("temp.txt");
            balls =fl.returnBalls();
            gravityValue = fl.returnGravity();
            setFrictionValues(fl.returnFrictionValues());
            wallConnectedGizmos=fl.returnWallConnectedGizmos();
        } catch (Exception e) {
            System.out.println("failed to load temp file");
        }

    }
    public void setSelectedGizmo(iPlaceable gizmo){
        selectedGizmo=gizmo;
    }
    public iPlaceable getSelectedGizmo(){
        return selectedGizmo;
    }
private void triggerWallConnections() {
    for (iPlaceable placceable : wallConnectedGizmos) {
        placceable.trigger();
    }
}
    public void connectGizmoToWalls(iPlaceable placeable){
        wallConnectedGizmos.add(placeable);
    }
    public void removeConnectedWallGizmo(iPlaceable placeable){
        wallConnectedGizmos.remove(placeable);
    }
    public void setConnectedWallGizmos(Set<iPlaceable>placeables){
        wallConnectedGizmos=placeables;
    }
}
