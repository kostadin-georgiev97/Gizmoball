package model.FileManagement;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import model.Absorber;
import model.Ball;
import model.GizmoType;
import model.KeyFunc;
import model.TriggerType;
import model.iPlaceable;
import model.Bumpers.RoundBumper;
import model.Bumpers.SquareBumper;
import model.Bumpers.TriangleBumper;
import model.Flipper.LeftFlipper;
import model.Flipper.RightFlipper;

public class FileLoader {
    //Fields
    private Set<iPlaceable> placeables;
    private Set<Ball> balls;
    private double friction_mu_1;
    private double friction_mu_2;
    private double gravity;
    private int lineNumber;
   private final double defaultGravityValue = 25;
    private final double mu = 0.025;
    private final double mu2 = 0.025;
    private Set<iPlaceable> wallConnectedGizmos;
    private final Color squareColour = Color.red;
    private final Color triangleColour = Color.BLUE;
    private final Color circleColour = Color.GREEN;
    private  final Color flipperColour = Color.yellow;
    private    final Color absorberColour= Color.pink;
    /**
     * Model.FileManagement.FileLoader Constructor
     */
    public FileLoader() {
        placeables = new HashSet<>();
        friction_mu_1 = mu;
        friction_mu_2 = mu2;
        gravity = defaultGravityValue;
        lineNumber=0;
        balls=new HashSet<>();
        wallConnectedGizmos = new HashSet<>();

    }

    public Set<Ball> returnBalls() {
        return balls;
    }

    public Set<iPlaceable> returnWallConnectedGizmos(){
        return wallConnectedGizmos;
    }
    /**
     * Effects: returns the gravity configuration, if none present returns 0.0
     */
    public double returnGravity() {
        return gravity;
    }

    /**
     * Effects: returns a List of Doubles containing the friction values, if none are configured both are 0.0
     */
    public List<Double> returnFrictionValues() {
        List<Double> frictions = new ArrayList<>();
        frictions.add(friction_mu_1);
        frictions.add(friction_mu_2);
        return frictions;
    }

    /**
     * Requires: fileName !=NULL
     * Effects: returns a list of all placeables that are in the save file
     * Throws IOException if save file is malformed, FileNotFoundException if save file doesn't exist
     */
    public Set<iPlaceable> loadLayout(String fileName) throws Exception {


        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            lineNumber++;
            if (!line.equals("\n")) {
                LineAnalyser(line);
                System.out.println(line);
            }
            line = br.readLine();
        }
        System.out.println("Configuration File Successfully loaded");
        return placeables;

    }
    public Set<iPlaceable> loadLayout(File file) throws Exception {


        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            lineNumber++;
            if (!line.equals("\n")) {
                LineAnalyser(line);
                System.out.println(line);
            }
            line = br.readLine();
        }
        System.out.println("Configuration File Successfully loaded");
        return placeables;

    }

    /**
     * Requires: inputLine !=NULL and placeables != NULL
     * Effects
     */
    private void LineAnalyser(String inputLine) throws Exception {
        Scanner scan = new Scanner(inputLine);
        iPlaceable placeable = null;

        String name;
        String command;

        if(scan.hasNext()) {
            command = scan.next();
            System.out.println(command);

            int xPos;
            int yPos;
            try {
                switch (command) {

                    case "Triangle":
                        name = scan.next();
                        xPos = scan.nextInt();
                        yPos = scan.nextInt();
                        placeable = new TriangleBumper(name, GizmoType.Triangle, xPos, yPos, triangleColour);

                        break;
                    case "Square":
                        name = scan.next();
                        xPos = scan.nextInt();
                        yPos = scan.nextInt();
                        placeable = new SquareBumper(name, GizmoType.Square, xPos, yPos, squareColour);

                        break;
                    case "Circle":
                        name = scan.next();
                        xPos = scan.nextInt();
                        yPos = scan.nextInt();
                        placeable = new RoundBumper(name, GizmoType.Circle, xPos, yPos, circleColour);
                        break;
                    case "RightFlipper": //TODO: Will need to fill this out later
                        name = scan.next();
                        xPos = scan.nextInt();
                        yPos = scan.nextInt();
                        placeable = new RightFlipper(name, GizmoType.RightFlipper, xPos, yPos, 0, flipperColour);

                        break;
                    case "LeftFlipper":  //TODO: Will need to fill this out later
                        name = scan.next();
                        xPos = scan.nextInt();
                        yPos = scan.nextInt();
                        placeable = new LeftFlipper(name, GizmoType.LeftFlipper, xPos, yPos, 0, flipperColour);//--
                        break;
                    case "Ball":
                    	if(balls==null) {
                    		balls = new HashSet<>();
                    	}
                        name = scan.next();
                        double xPosD = scan.nextDouble();
                        double yPosD = scan.nextDouble();
                        double xVelo = scan.nextDouble();
                        double yVelo = scan.nextDouble();
                        Ball ballToAdd = new Ball(name, xPosD, yPosD, xVelo, yVelo); //TOOK OUT RADIUS
                        balls.add(ballToAdd);
                        break;
                    case "Absorber":
                        name = scan.next();
                        xPos = scan.nextInt();
                        yPos = scan.nextInt();
                        int xEndPos = scan.nextInt();
                        int yEndPos = scan.nextInt();
                        placeable = new Absorber(name, GizmoType.Absorber, xPos, yPos, xEndPos, yEndPos, absorberColour);
                        break;

                    case "Rotate":
                        name = scan.next();
                        rotateGizmo(name);
                        break;
                    case "Move":
                        name = scan.next();
                        moveGizmo(inputLine, name);
                        break;
                    case "KeyConnect":
                        keyConnect(inputLine);
                        break;
                    case "Connect":
                        connectGizmo(inputLine);
                        break;
                    case "Delete":
                        name = scan.next();
                        deleteGizmo(name);
                        break;
                    case "Gravity":
                        gravity = scan.nextDouble();
                        break;
                    case "Friction":
                        friction_mu_1 = scan.nextDouble();
                        friction_mu_2 = scan.nextDouble();
                        break;

                    case "Colour":
                        changeColour(inputLine);
                        break;
                    case "Size":
                        changeSize(inputLine);
                        break;
                    case "TriggerType":
                        changeTriggerType(inputLine);
                        break;
                    default:
                        throw new IOException("Unknown Command Line at"+lineNumber+": " + inputLine);


                }

            } catch (NoSuchElementException e) {
//            System.out.println("cought ya");
                throw new InvalidLineFormatException("Invalid Line on file at "+lineNumber+": " + inputLine);
            }
            if (placeable != null) {
                placeables.add(placeable);
            }
        }
        scan.close();


    }

    private void changeTriggerType(String inputLine) throws NoSuchGizmoException {
        Scanner scan = new Scanner(inputLine);
        scan.next();
        String name =scan.next();
        String triggerType = scan.next();

        iPlaceable giz = findGizmo(name);

        switch (triggerType){
            case "RANDOM_COLOUR":
                giz.setTriggerType(TriggerType.RANDOM_COLOUR);
                break;
            case "DEAD":
                giz.setTriggerType(TriggerType.DEAD);
                break;
            case "TRIANGLE_ROTATE":
                giz.setTriggerType(TriggerType.TRIANGLE_ROTATE);
        }
    }

    private void changeSize(String inputLine) throws IOException {
        Scanner scanner = new Scanner(inputLine);
        scanner.next();
        String ball_name = scanner.next();
        double ball_size = scanner.nextDouble();
        Ball chosen = findBall(ball_name);
        chosen.setBallRadius(ball_size);
        scanner.close();
    }

    /**
     * Requires: name !=NULL
     * Effects: removes the Model.Gizmo from the list of Gizmos.
     */
    private void deleteGizmo(String name)throws NoSuchGizmoException {
        iPlaceable placeable = findGizmo(name);
        placeables.remove(placeable);
    }

    /**
     * Requires: name != NULL and line !=NULL
     * Effects: changes the desired gizmos position
     */
    private void moveGizmo(String line, String name)throws IOException{//can be either double or int
        Scanner scan = new Scanner(line);
        iPlaceable placeable=null;
        Ball b = null;
        try {
             placeable = findGizmo(name);
        }catch (NoSuchGizmoException e){
            b =findBall(name);
        }

        if (placeable == null) {
            double xPos = scan.nextDouble();
            double yPos = scan.nextDouble();
            assert b != null;
            b.setXY(xPos, yPos);
        } else {
            int xPos = scan.nextInt();
            int yPos = scan.nextInt();
            placeable.setXStartPos(xPos);
            placeable.setYStartPos(yPos);

        }
        scan.close();

    }

    /**
     * Requires: name !=NULL
     * Effects: rotates the desired gizmo
     */
    private void rotateGizmo(String name)throws NoSuchGizmoException {
        iPlaceable placeable = findGizmo(name);
        int rotation = placeable.getOrientation();
        rotation++;
        placeable.setOrientation(rotation);
    }

    /**
     * @Requires line != NULL
     * @Effects connects the desired Model.Gizmo action to the key being specified
     */
    private void keyConnect(String line)throws NoSuchGizmoException {
        //format goes command key keyFunction identifier
        Scanner scan = new Scanner(line);
        scan.next();
        scan.next();
        int keynum = scan.nextInt();//skip first two words
        String keyfunc = scan.next();
        String identifier = scan.next();
        iPlaceable placeable = findGizmo(identifier);
        if (keyfunc.equals("up")) {

            if(placeable.getKeyFunc().equals(KeyFunc.undefined)) {
                placeable.setKeyCode(keynum); //TODO: add Code for this
                placeable.setKeyFunc(KeyFunc.up);
            }else{
                placeable.setKeyFunc(KeyFunc.dual);
            }

        } else {
            if(placeable.getKeyFunc().equals(KeyFunc.undefined)) {
                placeable.setKeyCode(keynum);
                placeable.setKeyFunc(KeyFunc.down);
            }else {
                placeable.setKeyFunc(KeyFunc.dual);
            }
        }
        scan.close();
    }

    /**
     * Requires: line != NULL
     * Effects: connects the specified gizmo to the specified flipper.
     */
    private void connectGizmo(String line) throws NoSuchGizmoException{
        Scanner scan = new Scanner(line);
        scan.next();//skip command word
        String producer = scan.next();
        String consumer = scan.next();

        if(!producer.toLowerCase().equals("outerwalls")) {
            iPlaceable placeable = findGizmo(producer);

            iPlaceable placeable1 = findGizmo(consumer);
            placeable.connectGizmo(placeable1);
        }else{
            connectWalls(consumer);
        }
        scan.close();

    }

    private void connectWalls(String GizmoName) throws NoSuchGizmoException {
        iPlaceable placeable = findGizmo(GizmoName);
        placeable.connectWalls();
        wallConnectedGizmos.add(placeable);
    }

    /**
     * Requires: name != NULL
     * Effects: searches and returns the position of the desired gizmo in the placeables list
     */
    private iPlaceable findGizmo(String name) throws NoSuchGizmoException {
        for (iPlaceable gizmo : placeables) {
            if (gizmo.getName().equals(name)) {
                return gizmo;

            }
        }
        System.out.println("unable to find Gizmo");
        throw new NoSuchGizmoException("No Gizmo associated with " + name + " at line: "+lineNumber);

    }
    
    private Ball findBall(String name) throws NoSuchBallException{
        for (Ball b : balls) {
            if (b.getName().equals(name)) {
                return b;

            }
        }
        System.out.println("unable to find Ball");
        throw new NoSuchBallException("No Ball associated with " + name);

    }
    

    private void changeColour(String line) throws IOException {

        Scanner scan = new Scanner(line);
        scan.next();
        String GizmoName =scan.next();
        int GizmoColour = scan.nextInt();
        System.out.println(GizmoColour);
        System.out.println(GizmoName);
        iPlaceable placeable=null;
        Ball b = null;
        try{
            placeable= findGizmo(GizmoName);
        }catch (NoSuchGizmoException e){
            b = findBall(GizmoName);
        }

        if(placeable==null){
            //find ball
            assert b != null;
            b.setColour(new Color(GizmoColour));
        }else {

            placeable.setColour(new Color(GizmoColour));
        }
        scan.close();
    }
}
