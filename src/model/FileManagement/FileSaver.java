package model.FileManagement;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

import model.Ball;
import model.KeyFunc;
import model.TriggerType;
import model.iPlaceable;

public class FileSaver {

    private BufferedWriter printer;
    private Set<iPlaceable> placeables;
    private Set<Ball> balls;

    final Color squareColour = Color.red;
    final Color triangleColour = Color.BLUE;
    final Color circleColour = Color.GREEN;
    final Color flipperColour = Color.yellow;
    final Color absorberColour= Color.pink;
    final double standardBallSize = 0.25;
    final Color ballColour = Color.ORANGE;
    final double defaultGravityValue = 25;
    final double mu = 0.025;
    final double mu2 = 0.025;
    /**
     *Requires: placeables !=NULL
     *Effects: saves a layout file containing all placeable objects
     */
    public void saveLayout(String name, Set<iPlaceable> placeables, Set<Ball> balls, Boolean override, List<Double> frictions, double gravity) throws IOException {
        this.balls = balls;
        this.placeables = placeables;
        File file = new File(name);
        if(file.exists()&& !override){
            throw new FileAlreadyExistsException(name + " Already exists in system");
        }
        FileWriter writer = new FileWriter(file);
        printer = new BufferedWriter(writer);
        try {
            for (iPlaceable placeable : this.placeables) {
                savePlaceable(placeable);

                rotatePlaceable(placeable);


                keyConnectPlaceable(placeable);

                saveTriggerType(placeable);

                //check for rotations
                //checkForConnections
                //checkForKeyConnections

                saveColour(placeable);
            }
            for (iPlaceable placeable:this.placeables) {
                connectPlaceable(placeable);

            }
            for(Ball b : this.balls) {
            	saveBall(b);
            	saveColour(b);
            	saveSize(b);
            }
            saveFriction(frictions);
            saveGravity(gravity);
        }catch (Exception e){
            throw  new IOException("Problem during saving file, file saving aborted");
        }finally {
            printer.close();
        }

    }



    private void saveTriggerType(iPlaceable placeable) throws IOException {
        TriggerType triggerType =placeable.getTriggerType();
        if(!triggerType.equals(TriggerType.NONE)){
            printer.write("TriggerType "+placeable.getName());
            printer.write(" "+placeable.getTriggerType());
            printer.newLine();

        }
        }


    public void saveLayout(File chosen, Set<iPlaceable> placeables, Set<Ball> balls,  List<Double> frictions, double gravity) throws IOException {
        this.placeables=placeables;
        this.balls=balls;

            FileWriter writer = new FileWriter(chosen);


        printer = new BufferedWriter(writer);
        try {
            for (iPlaceable placeable : this.placeables) {
                savePlaceable(placeable);

                rotatePlaceable(placeable);


                keyConnectPlaceable(placeable);
                saveTriggerType(placeable);
                //check for rotations
                //checkForConnections
                //checkForKeyConnections

                saveColour(placeable);

            }
            for (iPlaceable placeable:this.placeables) {
                connectPlaceable(placeable);

            }
            for(Ball b : this.balls) {
                saveBall(b);
                saveColour(b);
                saveSize(b);
            }
            saveFriction(frictions);
            saveGravity(gravity);
        }catch (Exception e){
            throw  new IOException("Problem during saving file, file saving aborted");
        }finally {
            printer.close();
        }

    }
    private void saveFriction(List<Double> frictionValues)throws IOException{
        if(frictionValues.get(0)!=mu||frictionValues.get(1)!=mu2)
        printer.write("Friction "+frictionValues.get(0)+" "+frictionValues.get(1));
        printer.newLine();
    }
    private void saveGravity(double gravity) throws IOException {
        if(gravity!= defaultGravityValue) {
            printer.write("Gravity " + gravity);
            printer.newLine();
        }
    }
    private void saveSize(Ball b) throws IOException {
        if(b.getRadius()!= standardBallSize){
            printer.write("Size "+b.getName()+" "+b.getRadius());
            printer.newLine();
        }
    }

    private void saveColour(Ball b) throws IOException {
        if(b.getColour()!=ballColour) {
            printer.write("Colour " + b.getName() + " " + b.getColour().getRGB());
            printer.newLine();
        }
    }

    /**
     * Requires: placeable !=NULL
     * modifies: printer file
     * Effects: Saves the placeable object and positon
     */
    private void savePlaceable(iPlaceable placeable) throws IOException {
        printer.write(placeable.getGizmoType()+" "+placeable.getName()+" ");
        switch (placeable.getGizmoType()) {

            case Absorber:
                printer.write(placeable.getXStartPos()+" "+placeable.getYStartPos()+" "+placeable.getXEndPos()+" "+placeable.getYEndPos());
                break;
            default:

                printer.write(placeable.getXStartPos() + " " + placeable.getYStartPos());
                break;

        }
        printer.newLine();
    }
    /**
     * Requires: placeable !=NULL
     * Modifies: printer file
     * Effects: Repeatably saves a rotation command until it matches the placeables orientation
     */
    private void rotatePlaceable(iPlaceable placeable) throws IOException {
        for(int index = 0; index<placeable.getOrientation(); index++) {
            printer.write("Rotate ");
            printer.write(placeable.getName());
            printer.newLine();
        }
    }

    /**
     * Requires: placeable !=NULL
     * Modifies: printer file
     * Effects: saves the connection of the placeableObject if no connection present doesn't save anything
     */
    private  void connectPlaceable(iPlaceable placeable) throws IOException {
        if (placeable.getConnectedGizmos().size()>0) {

            for (iPlaceable gizmo:placeable.getConnectedGizmos()) {


                printer.write("Connect " + placeable.getName() + " " + gizmo.getName());
                printer.newLine();
            }

        }
        if(placeable.isConnectedWalls()){
            printer.write("Connect OuterWalls "+placeable.getName());
            printer.newLine();
        }
    }
    /**
     * Requires: placeable !=NULL
     * Modifies: printer file
     * Effects: saves the keyconnection of the placeable object
     */
    private void keyConnectPlaceable(iPlaceable placeable) throws IOException {
        //check if connected
        if(!placeable.getKeyFunc().equals(KeyFunc.undefined)){

            if(placeable.getKeyFunc().equals(KeyFunc.dual)){
                printer.write("KeyConnect Key "+placeable.getKeyCode()+" "+KeyFunc.up+" "+placeable.getName());
                printer.newLine();
                printer.write("KeyConnect Key "+placeable.getKeyCode()+" "+KeyFunc.down+" "+placeable.getName());
                printer.newLine();


            }else {
                printer.write("KeyConnect Key " + placeable.getKeyCode() + " " + placeable.getKeyFunc() + " " + placeable.getName());
                printer.newLine();
            }
        }

    }
   
    private void saveBall(Ball ball) throws IOException {
        if(ball!=null) {
            printer.write("Ball " + ball.getName() + " " + ball.getX() + " " + ball.getY() + " " + ball.getXVelo() + " " + ball.getYVelo());
            printer.newLine();
        }
    }


    private void saveColour(iPlaceable placeable) throws IOException {
        //check if using default colours
        Color col = placeable.getColour();
        boolean changed= false;
        switch (placeable.getGizmoType()){
            case Circle:
                if(placeable.getColour()!=circleColour){
                    changed=true;
                }
                break;
            case Square:
                if(placeable.getColour()!=squareColour){
                    changed=true;
                }
                break;
            case Triangle:
                if(placeable.getColour()!=triangleColour){
                    changed=true;
                }
                break;
            case Absorber:
                if(placeable.getColour()!=absorberColour){
                    changed=true;
                }
                break;
            default:
                if(placeable.getColour()!=flipperColour){
                    changed=true;
                }

        }

        if(changed){

            printer.write("Colour "+placeable.getName()+" "+col.getRGB());
            printer.newLine();
        }
    }

    public void saveStandardLayout(File chosen, Set<iPlaceable> placeables, Set<Ball> balls,  List<Double> frictions, double gravity) throws IOException {
        this.placeables=placeables;
        this.balls=balls;

        FileWriter writer = new FileWriter(chosen);


        printer = new BufferedWriter(writer);
        try {
            for (iPlaceable placeable : this.placeables) {
                savePlaceable(placeable);

                rotatePlaceable(placeable);


                keyConnectPlaceable(placeable);


            }
            for (iPlaceable placeable:this.placeables) {
                connectPlaceable(placeable);

            }
            Ball b = balls.iterator().next();//get first ball
            saveBall(b);
            saveFriction(frictions);
            saveGravity(gravity);
        }catch (Exception e){
            throw  new IOException("Problem during saving file, file saving aborted");
        }finally {
            printer.close();
        }

    }
}
