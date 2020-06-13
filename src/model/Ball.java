package model;

import java.awt.Color;

import model.physics.Angle;
import model.physics.Circle;
import model.physics.Vect;

public class Ball
{

    private String name;
    private double x; // X coord of ball centre
    private double y; // Y coord of ball centre
    private double r; // For radius
    private Color ballColour;
    private Vect velocity; // unit vector of angle and length
    private Circle ballCircle;
    private double ballFriction;
    private double xVelocity, yVelocity;
    private boolean stationary = false;

    /**
     * Creates a ball with centre (cx, cy) and radius r. Default colour will be
     * Orange, and no ball friction {@code .}
     * 
     * @param name
     *            the name of this ball
     * @param cx
     *            the centre x coordinate in L
     * @param cy
     *            the centre y coordinate in L
     * @param xVelocity
     *            the given x velocity that wont update automatically
     * @param yVelocity
     *            the given y velocity that also wont update automatically
     *
     */
    public Ball(String name, double cx, double cy, double xVelocity, double yVelocity)
    {
	this.name = name;
	x = cx;
	y = cy;
	r = 0.25; // As per spec which says ball should be with diameter of
		  // approx 0.5L
	ballColour = Color.ORANGE;
	// velocity = new Vect(Angle.DEG_45, 0.25); //D0 2.5 because spec says
	// it should be 50L/s, which means 2.5L/0.05s FOR ABSORBER
	updateCircle();
	ballFriction = 0;
	this.xVelocity = xVelocity;
	this.yVelocity = yVelocity;
	updateVelocity();
    }

    public Ball(String name, int cx, int cy, int xVelocity, int yVelocity, Color ballColour)
    {
	this.name = name;
	x = cx;
	y = cy;
	r = 0.25; // As per spec which says ball should be with diameter of
		  // approx 0.5L
	this.ballColour = ballColour;
	// velocity = new Vect(Angle.DEG_45, 0.25); //D0 2.5 because spec says
	// it should be 50L/s, which means 2.5L/0.05s FOR ABSORBER
	updateCircle();
	ballFriction = 0;
	this.xVelocity = xVelocity;
	this.yVelocity = yVelocity;
	updateVelocity();
    }

    /**
     * Gets the name of the ball
     * 
     * @return The name of the ball
     */
    public String getName()
    {
	return name;
    }

    /**
     * This is used to set the name of the ball
     * 
     * @param Takes
     *            in the name you wish to set the ball's name to be
     */
    public void setName(String name)
    {
	this.name = name;
    }

    /**** VELOCITY, CIRCLE AND COLOUR: ***********/

    /**
     * Returns the velocity of the ball - there is only one ball velocity
     * 
     * @return The ball's velocity
     */
    public Vect getVelo()
    {
	return velocity;
    }

    /**
     * Sets the ball's velocity and limits it to be in range of 0 to 200
     * inclusive
     * 
     * @param velocity
     *            The new velocity you wish to set the ball's velocity to be
     */
    public void setVelo(Vect velocity)
    {
	double velo = velocity.length();
	if (velo >= 0 && velo < 200)
	    this.velocity = velocity;
	else
	    if (velo < 0)
		this.velocity = new Vect(velocity.angle(), 0);
	    else
		this.velocity = new Vect(velocity.angle(), 200);
    }

    /**
     * This will use the parameters to update the ball's overall velocity
     * 
     * @param xVelocity
     *            The X velocity of the ball in Ls
     * @param yVelocity
     *            The Y velocity of the ball in Ls
     */
    public void setXYVelo(double xVelocity, double yVelocity)
    {
	this.xVelocity = xVelocity;
	this.yVelocity = yVelocity;
	updateVelocity();
    }

    /**
     * This is just used to update the ball's velocity using xVelocity and
     * yVelocity
     */
    public void updateVelocity()
    {
	Vect xVect = new Vect(Angle.ZERO, xVelocity);
	Vect yVect = new Vect(Angle.DEG_90, yVelocity);
	Vect joint = xVect.plus(yVect);
	Vect joint2 = yVect.plus(xVect);
	setVelo(joint);
	System.out.println(
		"X+Y: " + joint.angle() + "; " + joint.length() + "; Y+X : " + joint2.angle() + "; " + joint2.length());
    }

    /**
     * 
     * @return The ball's X Velocity
     */
    public double getXVelo()
    {
	return xVelocity;
    }

    /**
     * 
     * @return The ball's Y Velocity
     */
    public double getYVelo()
    {
	return yVelocity;
    }

    /**
     * This will get the MIT Physics Circle of the ball which uses the ball's
     * Radius and coordinates
     * 
     * @return The Circle of the ball
     */
    public Circle getCircle()
    {
	return ballCircle;
    }

    /**
     * This function is just here to be used to update the Circle of the ball
     */
    public void updateCircle()
    {
	ballCircle = new Circle(x, y, r);
    }

    /**
     * @return The Colour of the ball
     */
    public Color getColour()
    {
	return ballColour;
    }

    /**
     * @param colour
     *            The colour you want the ball to be known to be
     */
    public void setColour(Color colour)
    {
	ballColour = colour;
    }

    /***** BALL FRICTION: ********************/

    /**
     * 
     * @return The friction of the ball
     */
    public double getBallFriction()
    {
	return ballFriction;
    }

    /**
     * Will set the ball's friction variable to be the param value, but will
     * ensure it is within the range of 0 to 100 inclusive
     * 
     * @param ballFriction
     *            The value you want the ball friction to be
     */
    public void setBallFriction(double ballFriction)
    {
	if (ballFriction >= 0 && ballFriction <= 100)
	{
	    this.ballFriction = ballFriction;
	}
	else
	    if (ballFriction < 0)
	    {
		this.ballFriction = 0;
	    }
	    else
		if (ballFriction > 100)
		{
		    this.ballFriction = 100;
		}
    }

    /**** X, Y AND RADIUS: *********************/
    /**
     * Sets the X and Y coordinates of the ball, using L as units- not pixels
     * 
     * @param cx
     *            is the centre X coordinate in L
     * @param cy
     *            is the centre Y coordinate in L
     */
    public void setXY(double cx, double cy)
    {
	x = cx;
	y = cy;
	updateCircle();
    }

    /**
     * Gets the centre X value of the ball
     * 
     * @return The X coordinate of the ball
     */
    public double getX()
    {
	return x;
    }

    /**
     * Gets the centre Y value of the ball
     * 
     * @return The Y coordinate of the ball
     */
    public double getY()
    {
	return y;
    }

    /**
     * 
     * @return The radius of the ball in L
     */
    public double getRadius()
    {
	return r;
    }

    /**
     * This uses the ball's radius to figure out diameter
     * 
     * @return The ball's diameter in L
     */
    public double getDiameter()
    {
	return r * 2;
    }

    /**
     * Used to set the radius of the ball, ensuring it is in range 1 to 100
     * inclusive
     * 
     * @param radius
     *            The new radius of the ball in L
     */
    public void setBallRadius(double radius)
    {
	    r = radius;
    }

    /**
     * This is mainly used by other methods to see if the ball should be moving
     * 
     * @return True if the ball should be classed as stationary
     * @return False if the ball should be classed as moving/NOT stationary
     */
    public boolean isStationary()
    {
	return stationary;
    }

    /**
     * Used to set whether a ball should be classed as stationary
     * 
     * @param nowStationary
     *            should be true when you want the ball to be classed as
     *            stationary
     * @param nowStationary
     *            should be false when you want the ball to be classed as
     *            moving/not stationary
     */
    public void setStationary(boolean nowStationary)
    {
	stationary = nowStationary;
    }

}
