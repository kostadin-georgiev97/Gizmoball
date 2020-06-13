package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.AutoRunListener;
import controller.BuildModeListener;
import controller.RunStartListener;
import model.Ball;
import model.GameBoardModel;
import model.iPlaceable;
import model.Bumpers.RoundBumper;
import model.Bumpers.TriangleBumper;
import model.Flipper.Flipper;
import model.Flipper.LeftFlipper;
import model.Flipper.RightFlipper;

public class RunGui extends Observable implements Observer
{

    int a;

    public static final int GRID_LINES_X = 20;
    public static final int GRID_LINES_Y = 20;
    public static final int GRID_SQUARE_PIXELS = 25;
    GameBoardModel gameBoardModel;
    boolean isRunning = false;
    
    public RunGui(GameBoardModel model)
    {

	gameBoardModel = model;

	gameBoardModel.addObserver(this);
    }

    private JFrame frame;
    private double velocityOfBall = 0.0;
    JLabel bottomLine;
    public JFrame setUp()
    {

	Container contentPane;

	frame = new JFrame("Gizmoball: Run mode!");

	contentPane = frame.getContentPane();
	contentPane.setLayout(new BorderLayout(2, 2));
	
	JPanel buttonsPane = new JPanel(new GridLayout(1,3,2,2));
	
	
	JButton bTick = new JButton("TICK");
	bTick.addActionListener(new RunStartListener(this));

	JButton bPlay = new JButton("Play");
	bPlay.addActionListener(new AutoRunListener(this));

	JButton build_mode = new JButton("Build Mode");
	build_mode.addActionListener(new BuildModeListener(this,gameBoardModel));
	
	buttonsPane.add(build_mode);
	buttonsPane.add(bTick);
	buttonsPane.add(bPlay);
	
	// frame.setSize(800, 600);
	frame.setSize(600, 1000);
	frame.setLocationRelativeTo(null); // Puts it in the middle

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel grid = new Draw(gameBoardModel);
	// grid.setBorder(new EmptyBorder(0,300,0,120)); //top, left, bottom,
	// right
	contentPane.add(grid, BorderLayout.CENTER);
	JPanel pan = new JPanel(new GridLayout(0, 1,2,2));
	pan.add(buttonsPane);
	
	 bottomLine = new JLabel("Current Speed of ball: " + velocityOfBall + "L/s");
	
	pan.add(bottomLine);
	contentPane.add(pan, BorderLayout.SOUTH);

	frame.revalidate();
	frame.repaint();
	frame.setSize(600, 1000);
	frame.pack();

	frame.setLocationRelativeTo(null); // Puts it in the middle
		frame.setResizable(false);

	return frame;
    }

	public void setVisible(boolean vis) {
		//this.frame.setVisible(vis);
		setChanged();
		System.out.println("Runnn");
		notifyObservers(this);
	}

	private class Draw extends JPanel
    {

	/**
	 * No idea what this is but importing Graphics wanted it
	 */
	private static final long serialVersionUID = 1L;

	private GameBoardModel gameBoardModel;

	public Draw(GameBoardModel gameBoardModel)
	{
	    // 1 L = 25 Pixels, grid needs to be 20Lx20L, so we need the thing
	    // to be 500 pixels long/high

	    setBackground(Color.white);
	    this.setPreferredSize(new Dimension(500, 500));
	    this.gameBoardModel = gameBoardModel;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    Collection<iPlaceable> tiles = gameBoardModel.getTiles();
	    for (iPlaceable tile : tiles)
	    {
		drawBlob(tile, g);
	    }

	    Collection<Ball> balls = gameBoardModel.getBalls();
        for(Ball b : balls) {
        drawBall(b, g);
        }
	}

	protected void drawBall(Ball b, Graphics g)
	{
		velocityOfBall = b.getVelo().length();
		if(getBalls().size()==1) {
			bottomLine.setText("Current Speed of ball: " + velocityOfBall + "L/s");
			
		}else{
			//Calculate and displau average speed
			double totalSpeed=0;
			for (Ball ball:getBalls()) {
				totalSpeed=totalSpeed+ball.getVelo().length();
			}
			double averageSpeed = totalSpeed/getBalls().size();
			bottomLine.setText("Average Ball Speed: "+averageSpeed+"L/s");
		}
	    g.setColor(b.getColour());
	    int diameter = (int) (b.getDiameter() * GRID_SQUARE_PIXELS);
	    g.drawOval((int) (GRID_SQUARE_PIXELS * b.getX() - (0.5 * diameter)),
			    (int) (GRID_SQUARE_PIXELS * b.getY() - (0.5 * diameter)), diameter, diameter);
	    g.fillOval((int) (GRID_SQUARE_PIXELS * b.getX() - (0.5 * diameter)),
		    (int) (GRID_SQUARE_PIXELS * b.getY() - (0.5 * diameter)), diameter, diameter);

	}


	protected void drawCircle(int x, int y, Color colour, Graphics g)
	{
	    int pixelX = x * GRID_SQUARE_PIXELS;
	    int pixelY = y * GRID_SQUARE_PIXELS;
	    g.setColor(colour);
	    g.fillOval(pixelX, pixelY, GRID_SQUARE_PIXELS, GRID_SQUARE_PIXELS);
	}

	protected void drawSquare(int x, int y, Color colour, Graphics g)
	{
	    int pixelX = x * GRID_SQUARE_PIXELS;
	    int pixelY = y * GRID_SQUARE_PIXELS;
	    g.setColor(colour);
	    g.drawRect(pixelX, pixelY, GRID_SQUARE_PIXELS, GRID_SQUARE_PIXELS);
	    g.fillRect(pixelX, pixelY, GRID_SQUARE_PIXELS, GRID_SQUARE_PIXELS);

}
	
	 


	protected void drawTriangle(int x, int y, int orientation, Color colour, Graphics g)
	{
	    int pixelX = x * GRID_SQUARE_PIXELS;
	    int pixelY = y * GRID_SQUARE_PIXELS;
	    Polygon triangle = new Polygon();

	    switch (orientation % 4)
	    {
	    case 0:
		triangle.addPoint(pixelX, pixelY);
		triangle.addPoint(pixelX + GRID_SQUARE_PIXELS, pixelY);
		triangle.addPoint(pixelX, pixelY + GRID_SQUARE_PIXELS);
		break;
	    case 1:
		triangle.addPoint(pixelX, pixelY);
		triangle.addPoint(pixelX + GRID_SQUARE_PIXELS, pixelY);
		triangle.addPoint(pixelX + GRID_SQUARE_PIXELS, pixelY + GRID_SQUARE_PIXELS);
		break;
	    case 2:
		triangle.addPoint(pixelX + GRID_SQUARE_PIXELS, pixelY);
		triangle.addPoint(pixelX, pixelY + GRID_SQUARE_PIXELS);
		triangle.addPoint(pixelX + GRID_SQUARE_PIXELS, pixelY + GRID_SQUARE_PIXELS);
		break;
	    case 3:
		triangle.addPoint(pixelX, pixelY);
		triangle.addPoint(pixelX, pixelY + GRID_SQUARE_PIXELS);
		triangle.addPoint(pixelX + GRID_SQUARE_PIXELS, pixelY + GRID_SQUARE_PIXELS);
		break;
	    }

	    g.setColor(colour);
	    g.fillPolygon(triangle);
	}

	protected void drawFlipper(Flipper flip, Graphics g)
	{
	    int pixelLeftX = (int) (flip.getLeftCircleX() * GRID_SQUARE_PIXELS - GRID_SQUARE_PIXELS / 4),
		    pixelLeftY = (int) (flip.getLeftCircleY() * GRID_SQUARE_PIXELS - GRID_SQUARE_PIXELS / 4),
		    pixelRightX = (int) (flip.getRightCircleX() * GRID_SQUARE_PIXELS - GRID_SQUARE_PIXELS / 4),
		    pixelRightY = (int) (flip.getRightCircleY() * GRID_SQUARE_PIXELS - GRID_SQUARE_PIXELS / 4),
		    pixelTopLineX1 = (int) (flip.getTopLineX1() * GRID_SQUARE_PIXELS),
		    pixelTopLineY1 = (int) (flip.getTopLineY1() * GRID_SQUARE_PIXELS),
		    pixelTopLineX2 = (int) (flip.getTopLineX2() * GRID_SQUARE_PIXELS),
		    pixelTopLineY2 = (int) (flip.getTopLineY2() * GRID_SQUARE_PIXELS),
		    pixelBottomLineX1 = (int) (flip.getBottomLineX1() * GRID_SQUARE_PIXELS),
		    pixelBottomLineY1 = (int) (flip.getBottomLineY1() * GRID_SQUARE_PIXELS),
		    pixelBottomLineX2 = (int) (flip.getBottomLineX2() * GRID_SQUARE_PIXELS),
		    pixelBottomLineY2 = (int) (flip.getBottomLineY2() * GRID_SQUARE_PIXELS);
	    Polygon rect = new Polygon();

	    rect.addPoint(pixelTopLineX1, pixelTopLineY1);
	    rect.addPoint(pixelTopLineX2, pixelTopLineY2);
	    rect.addPoint(pixelBottomLineX2, pixelBottomLineY2);
	    rect.addPoint(pixelBottomLineX1, pixelBottomLineY1);

	    g.setColor(flip.getColour());
	    g.fillOval(pixelLeftX, pixelLeftY, GRID_SQUARE_PIXELS / 2, GRID_SQUARE_PIXELS / 2);
	    g.fillOval(pixelRightX, pixelRightY, GRID_SQUARE_PIXELS / 2, GRID_SQUARE_PIXELS / 2);
	    g.fillPolygon(rect);
	}

	protected void drawBlob(iPlaceable tile, Graphics g)
	{
	    if (tile instanceof RightFlipper || tile instanceof LeftFlipper)
	    {
		drawFlipper((Flipper) tile, g);
		if(tile instanceof RightFlipper)
		{
		    ((RightFlipper) tile).updateRotation();
		}
		else
		{
		    ((LeftFlipper) tile).updateRotation();
		}
	    }
	    else
	    {
		for (int i = tile.getXStartPos(); i < tile.getXEndPos(); i++)
		{
		    for (int j = tile.getYStartPos(); j < tile.getYEndPos(); j++)
		    {
			if (tile instanceof RoundBumper)
			{
			    drawCircle(i, j, tile.getColour(), g);
			}
			else
			    if (tile instanceof TriangleBumper)
			    {
				drawTriangle(i, j, tile.getOrientation(), tile.getColour(), g);
			    }
			    else
			    {
				drawSquare(i, j, tile.getColour(), g);
			    }
		    }
		}
	    }
	}

    }

    public Collection<Ball> getBalls(){
    	return gameBoardModel.getBalls();
    }
    
    
    public void moveBall(Ball b)
    {
	gameBoardModel.moveBall(b);
    }

    public JFrame repainter()
    {
	frame.repaint();
	frame.revalidate();
	frame.requestFocus();

	// frame.setSize(600, 1000);
	// frame.pack();

	// frame.setLocationRelativeTo(null); //Puts it in the middle
	return frame;

    }

    public boolean getIsRunning() {
    	return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
    	this.isRunning = isRunning;
    }
    
    public void setButton(JButton b, String newTitle) {
    	b.setText(newTitle);
    }
    
    public void runStuff() {
    
    }
    
    
    @Override
    public void update(Observable o, Object arg)
    {
	System.out.println("UPDATINGGG");
	repainter();
    }

}
