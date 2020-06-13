package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import controller.AddBallListener;
import controller.AddGizmoListener;
import controller.AddWallConnectionsListener;
import controller.BallSizeListener;
import controller.BallVelListener;
import controller.BuildModeAction;
import controller.ChooseTriggerTypeListener;
import controller.ClearBoardListener;
import controller.ColourSelectionListener;
import controller.ConnectGizmoListener;
import controller.ConnectKeyActionListener;
import controller.ConnectKeyKeyListener;
import controller.DeleteBallListener;
import controller.DeleteGizmoListener;
import controller.DisconnectConnectedGizmosListener;
import controller.DisconnectKeyListener;
import controller.GridClickListener;
import controller.LoadListener;
import controller.MoveBallListener;
import controller.MoveGizmoListener;
import controller.OptionsListener;
import controller.RemoveTriggerListener;
import controller.RemoveWallConnectionsListener;
import controller.RotateGizmoListener;
import controller.RunModeListener;
import controller.SaveListener;
import controller.SetGizmoColourListener;
import controller.SetGravityListener;
import model.Ball;
import model.GameBoardModel;
import model.KeyFunc;
import model.TriggerType;
import model.iPlaceable;
import model.Bumpers.RoundBumper;
import model.Bumpers.TriangleBumper;
import model.Flipper.Flipper;
import model.Flipper.LeftFlipper;
import model.Flipper.RightFlipper;


public class BuildGui extends Observable implements Observer {
	
	public static final int GRID_LINES_X = 20;
	public static final int GRID_LINES_Y = 20;
	public static final int GRID_SQUARE_PIXELS = 25;
	private BuildModeAction action;
	private int sourceX, sourceY, targetX, targetY;
	private int pressedKey;
	private KeyFunc keyFunc;
	private TriggerType triggerType;
	private GameBoardModel model;
	JFrame frame;
	JComboBox<String> gizmoDrop, colourDrop;
	JComboBox<TriggerType> triggerDrop;
	JLabel bottomLine;
	private JLabel lblGizmoColour = new JLabel("Current Gizmo Colour");
	private JLabel lblBallColour = new JLabel("Current Ball Colour");
	private JPanel pGizmoOptions = new JPanel(new GridLayout(0,2));
	private JPanel pBallOptions = new JPanel(new GridLayout(0,2));
	
	private JPanel pFileOptions = new JPanel(new GridLayout(0,1));

	private JSlider xvelSlider = new JSlider(-1000,1000,0);
	private JSlider yvelSlider = new JSlider(-1000,1000,0);
	private JSlider ballSizeSlider = new JSlider(25,100,50);
	private JSlider gravitySlider = new JSlider(0,1000,100);

	private Hashtable gravityLabelTable = new Hashtable();
	private Hashtable sizeLabelTable = new Hashtable();
	private Hashtable velocityLabelTable = new Hashtable();


	private boolean isGOptionsVis=false, isBOptionsVis=false, isFOptionsVis=false;
	
	
	public BuildGui(GameBoardModel model) {
		setupSliders();
pBallOptions.setPreferredSize(new Dimension(700,500));

		this.model =model;
		this.action = BuildModeAction.None;
		this.sourceX = -1;
		this.sourceY = -1;
		this.targetX = -1;
		this.targetY = -1;
		this.pressedKey = KeyEvent.VK_UNDEFINED;
		this.keyFunc=KeyFunc.undefined;
		triggerType=TriggerType.NONE;
		this.model.addObserver(this);
	}


	private void setupSliders() {
		gravityLabelTable.put(0,new JLabel("0"));
		gravityLabelTable.put(100,new JLabel("10"));
		gravityLabelTable.put(1000,new JLabel("100"));
		gravitySlider.setLabelTable(gravityLabelTable);
		gravitySlider.setBorder(BorderFactory.createTitledBorder("<HTML>< Gravity L/s/s </HTML>"));//TODO: this correct measurement

		gravitySlider.setMinorTickSpacing(50);
		gravitySlider.setPaintTicks(true);
		gravitySlider.setPaintLabels(true);

		sizeLabelTable.put(25,new JLabel("0.25"));
		sizeLabelTable.put(50,new JLabel("0.5"));
		sizeLabelTable.put(100,new JLabel("1"));
		ballSizeSlider.setLabelTable(sizeLabelTable);
		ballSizeSlider.setBorder(BorderFactory.createTitledBorder("<HTML>< Ball Radius L</HTML>"));

		ballSizeSlider.setMinorTickSpacing(5);
		ballSizeSlider.setPaintTicks(true);
		ballSizeSlider.setPaintLabels(true);

		velocityLabelTable.put(-1000, new JLabel("-100.0"));
		velocityLabelTable.put(0,new JLabel("0"));
		velocityLabelTable.put(1000, new JLabel("100.0"));
		xvelSlider.setLabelTable(velocityLabelTable);
		xvelSlider.setBorder(BorderFactory.createTitledBorder("<HTML>< X Velocity L/s </HTML>"));
		xvelSlider.setMinorTickSpacing(250);

		yvelSlider.setLabelTable(velocityLabelTable);
		yvelSlider.setBorder(BorderFactory.createTitledBorder("<HTML>< Y Velocity L/s </HTML>"));
		yvelSlider.setMinorTickSpacing(250);

		xvelSlider.setPaintTicks(true);
		yvelSlider.setPaintTicks(true);
		xvelSlider.setPaintLabels(true);
		yvelSlider.setPaintLabels(true);
	}

	public double getBallSize()
	{
	    return ballSizeSlider.getValue()/100.0;
	}
	
	public double getVX()
	{
	    return xvelSlider.getValue()/10.0;
	}
	
	public double getVY()
	{
	    return yvelSlider.getValue()/10.0;
	}
	public double getGravity(){
		return gravitySlider.getValue()/10.0;
	}
	public JFrame setUp() {

		Container contentPane;
		
		GameBoardModel gameBoardModel = this.model;
		
		frame = new JFrame("Gizmoball: Build mode!");
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout(2,2));
		
		
		frame.setSize(600, 1000);
		frame.setLocationRelativeTo(null); //Puts it in the middle

		/* 1. Gizmo related settings: *************************/

		String gizmos[] = {"Absorber", "Circle Bumper", "Square Bumper", "Triangle Bumper", "Left Flipper", "Right Flipper"};
		gizmoDrop = new JComboBox<String>(gizmos);
		
		JLabel lblGizmo = new JLabel("Choose gizmo type:");
		gizmoDrop.setToolTipText("This is the current Gizmo type that can be added. Use drop Down menu to change");
	
		JPanel gizmoPanel = new JPanel(new BorderLayout(2,2));
		gizmoPanel.setBorder(BorderFactory.createTitledBorder(lblGizmo.getText()));
		gizmoPanel.add(gizmoDrop, "Center");
		
		JButton bAddGiz = new JButton("Add Gizmo");
		bAddGiz.addActionListener(new AddGizmoListener(this));
		
		JButton bRotateGiz = new JButton("Rotate Gizmo");
		bRotateGiz.addActionListener(new RotateGizmoListener(this));
		
		JButton bMoveGizmo = new JButton("Move Gizmo");
		bMoveGizmo.addActionListener(new MoveGizmoListener(this));
		
		JButton bDeleteGizmo = new JButton("Delete Gizmo");
		bDeleteGizmo.addActionListener(new DeleteGizmoListener(this));
		
		/* 2. Ball related settings: ************************/
		
		
		
		JButton bAddBall = new JButton("Add Ball");
		bAddBall.addActionListener(new AddBallListener(this));
		JButton bMoveBall = new JButton("Move Ball");
		bMoveBall.addActionListener(new MoveBallListener(this));
		JButton bDeleteBall = new JButton("Delete Ball");
		bDeleteBall.addActionListener(new DeleteBallListener(this));
		JButton bSetBallSize = new JButton("Set Ball Size");
		bSetBallSize.addActionListener(new BallSizeListener(this));
		JButton bSetBallVelocity = new JButton ("Set Ball Velocity");
		bSetBallVelocity.addActionListener(new BallVelListener(this));
		

		
		
		
				JPanel ballSizePanel = new JPanel(new GridLayout(1,2,2,2));
				ballSizePanel.setBorder(BorderFactory.createTitledBorder("Ball Size"));
				ballSizePanel.add(ballSizeSlider);
				ballSizePanel.add(bSetBallSize);
				
				JPanel ballVelocityPanel = new JPanel(new GridLayout(1,2,2,2));
				ballVelocityPanel.setBorder(BorderFactory.createTitledBorder("Ball Velocity"));
				ballVelocityPanel.add(yvelSlider);
				ballVelocityPanel.add(xvelSlider);
				ballVelocityPanel.add(bSetBallVelocity);
				
				
				
		





		/* 4. Adding KEY and TRIGGER stuff: ********* Includes Clear Board ***/
		JButton bConnectKey = new JButton("Connect Key");
		bConnectKey.addActionListener(new ConnectKeyActionListener(this));
		bConnectKey.addKeyListener(new ConnectKeyKeyListener(this,gameBoardModel));
		
		JButton bDisconnectKey = new JButton("Disconnect Key");
		bDisconnectKey.addActionListener(new DisconnectKeyListener(this));
		
	JButton connectGizmo = new JButton("Connect Gizmo");
	connectGizmo.addActionListener(new ConnectGizmoListener(this));
	JButton disconnectGizmos = new JButton("Disconnect Connected Gizmos");
	disconnectGizmos.addActionListener(new DisconnectConnectedGizmosListener(this));

	JButton connectWalls = new JButton("ConnectWalls");
	connectWalls.addActionListener(new AddWallConnectionsListener(this));

	JButton disconnectWalls = new JButton("Disconnect Walls");
	disconnectWalls.addActionListener(new RemoveWallConnectionsListener(this));

		JButton bClearTriggers = new JButton("Remove Trigger");
		bClearTriggers.addActionListener(new RemoveTriggerListener(this));
		
		TriggerType triggerTypes[] = {TriggerType.NONE, TriggerType.RANDOM_COLOUR, TriggerType.DEAD, TriggerType.TRIANGLE_ROTATE};
		triggerDrop = new JComboBox<>(triggerTypes);
		
		triggerDrop.setToolTipText("<HTML>This is the current trigger type that can be applied. Use drop Down menu to change"
				+ "<ul><li>RANDOM_COLOUR : The gizmo with this trigger will change colour upon contact from a ball</li>"
				+ "<li>DEAD : After a random amount of time, a gizmo with this trigger that was hit by a ball will dissapear</li>"
				+ "<li>TRIANGLE_ROTATE : When hit by a ball, a triangle with this trigger will rotate</li></ul></HTML>");
	
		JButton triggerTypeButton = new JButton("Set Trigger ");
		triggerTypeButton.addActionListener(new ChooseTriggerTypeListener(this));
		
		JPanel triggerPanel = new JPanel(new GridLayout(0,2,2,2));
		triggerPanel.add(triggerTypeButton);
		triggerPanel.add(triggerDrop);
		
		
		/*5. GRAVITY: ****************************/

		JButton bSetGravity = new JButton ("Set Gravity");
		bSetGravity.addActionListener(new SetGravityListener(this, this.model));

		
		JPanel gravPanel = new JPanel(new GridLayout(1,2, 2,2));
		gravPanel.setBorder(BorderFactory.createTitledBorder("Control Gravity:"));
		gravPanel.add(gravitySlider);
		gravPanel.add(bSetGravity);
		
		/*6. COLOUR- 2 Copies of it: ********************************/
		JButton bSetColour1 = new JButton("Set Colour");
		bSetColour1.setName("Gizmo");
		bSetColour1.addActionListener(new SetGizmoColourListener(this));
		
		JButton bUseCurrentColour1 = new JButton("Use Current Colour");
		bUseCurrentColour1.setName("Current Gizmo");
		bUseCurrentColour1.addActionListener(new SetGizmoColourListener(this));
		
		JPanel coloursPanel1 = new JPanel(new GridLayout(2,2,2,2));
		coloursPanel1.setBorder(BorderFactory.createTitledBorder("Colours"));
		coloursPanel1.add(bSetColour1);
		coloursPanel1.add(bUseCurrentColour1);
		lblGizmoColour.setForeground(Color.RED);
		coloursPanel1.add(this.lblGizmoColour);
		
		
		
		JButton bSetColour2 = new JButton("Set Colour");
		bSetColour2.setName("Ball");
		bSetColour2.addActionListener(new SetGizmoColourListener(this));
		
		JButton bUseCurrentColour2 = new JButton("Use Current Colour");
		bUseCurrentColour2.addActionListener(new SetGizmoColourListener(this));
		bUseCurrentColour2.setName("Current Ball");
		JPanel coloursPanel2 = new JPanel(new GridLayout(2,2,2,2));
		coloursPanel2.setBorder(BorderFactory.createTitledBorder("Colours"));
		coloursPanel2.add(bSetColour2);
		coloursPanel2.add(bUseCurrentColour2);
		lblBallColour.setForeground(Color.RED);
		coloursPanel2.add(this.lblBallColour);
		/***************************************************/
		

		pGizmoOptions.add(gizmoPanel);

		pGizmoOptions.add(coloursPanel1);
		pGizmoOptions.add(bAddGiz);
		pGizmoOptions.add(bRotateGiz);
		pGizmoOptions.add(bMoveGizmo);
		pGizmoOptions.add(bDeleteGizmo);

		pBallOptions.add(bAddBall);
		pBallOptions.add(bMoveBall);
		pBallOptions.add(bDeleteBall);
		
		pGizmoOptions.add(bConnectKey);
		pGizmoOptions.add(bDisconnectKey);
		pGizmoOptions.add(connectGizmo);
		pGizmoOptions.add(disconnectGizmos);
		pGizmoOptions.add(connectWalls);
		pGizmoOptions.add(disconnectWalls);

		pGizmoOptions.add(bClearTriggers);
		pGizmoOptions.add(triggerPanel);
		
		
		pBallOptions.add(gravPanel);
		
		pBallOptions.add(ballSizePanel);
		pBallOptions.add(coloursPanel2);
		pBallOptions.add(ballVelocityPanel);
		
		
		
		
		triggerPanel.setBorder(BorderFactory.createTitledBorder("<HTML><i> Extra:</i> Add more triggers </HTML>"));
		pGizmoOptions.add(triggerPanel);
		
		
		/* 5. Adding the general Game Options *************************/
		JButton bRunMode = new JButton("Run Mode");
		bRunMode.addActionListener(new RunModeListener(this)) ;


		pFileOptions.setBorder(BorderFactory.createTitledBorder("Enter file name for saving/loading"));
		pFileOptions.setBorder(BorderFactory.createTitledBorder("File Save/Loading"));
		JButton bSave = new JButton ("Save Layout");
		JButton bLoad = new JButton ("Load Layout");
		JTextField inputter = new JTextField(10);
		inputter.isEditable();
		inputter.setPreferredSize(new Dimension(10, 10));
		//pFileOptions.add(inputter); TODO May need this if there is bugs with the new setup
		pFileOptions.add(bSave);
		pFileOptions.add(bLoad);
		pFileOptions.setPreferredSize(new Dimension(300, 500));
		
		bSave.addActionListener(new SaveListener(inputter, gameBoardModel));
		
		LoadListener loady = new LoadListener(inputter, gameBoardModel);
		loady.addObserver(this);
		bLoad.addActionListener(loady);
		
		//rightPanel.add(savingPanel);		
		
		JPanel buttonStuff = new JPanel(new GridLayout(0,1,2,2));
		
		JButton bGizmoOptions = new JButton("Gizmo Options");
		bGizmoOptions.setName("Gizmo");
		bGizmoOptions.addActionListener(new OptionsListener(this));
		
		JButton bBallOptions = new JButton("Ball Options");
		bBallOptions.setName("Ball");
		bBallOptions.addActionListener(new OptionsListener(this));
		
		JButton bFileOptions = new JButton("File Options");
		bFileOptions.setName("File");
		bFileOptions.addActionListener(new OptionsListener(this));
		
		
		JPanel startingPosition = new JPanel(new GridLayout(5,1));
		
		startingPosition.add(bGizmoOptions);
		startingPosition.add(bBallOptions);
		startingPosition.add(bFileOptions);
		startingPosition.add(bRunMode);
		
		JButton bClearBoard = new JButton("Clear Board");
		bClearBoard.addActionListener(new ClearBoardListener(gameBoardModel,this));
		startingPosition.add(bClearBoard);
		
		buttonStuff.add(startingPosition);
		//buttonStuff.add(rightPanel);
		//***********************************************
		//grid.setBorder(new EmptyBorder(0,300,0,120)); //top, left, bottom, right
		JPanel grid = new DrawGrid(gameBoardModel);
		grid.addMouseListener(new GridClickListener(this, model));
		
		JPanel allTheStuff = new JPanel(new BorderLayout(2,2));
		allTheStuff.add(grid, BorderLayout.CENTER);
		allTheStuff.add(buttonStuff, BorderLayout.EAST);
		bottomLine = new JLabel("You're in build mode!! Hit a button to get started!");
		allTheStuff.add(bottomLine, BorderLayout.SOUTH);


		//----------------------------------------------------------------------------------
		

		//-------------------------------------------------------------------------------
		contentPane.add(allTheStuff, "Center");
		
		frame.revalidate();
		frame.repaint();
		frame.setSize(600, 1000);
		frame.pack();
		
		frame.setLocationRelativeTo(null); //Puts it in the middle
		frame.setResizable(false);

		return frame;
	}
	
	public void setVisible(boolean vis) {
		//this.frame.setVisible(vis);
		setChanged();
		System.out.println("BUILDDDDDD");
		notifyObservers(this);
	}
	
	public void showGizmoOptions() {
		Container c = frame.getContentPane();
		if(isGOptionsVis==false) {
			
			if(isBOptionsVis==true) {
				c.remove(pBallOptions);
				isBOptionsVis = false;
			}
			if(isFOptionsVis==true) {
				c.remove(pFileOptions);
				isFOptionsVis = false;
			}
			c.add(pGizmoOptions, "East");
			isGOptionsVis = true;
		}
		else {
			c.remove(pGizmoOptions);
			isGOptionsVis=false;
		}
		
		repainter();
	}
	
	public void showBallOptions() {
		Container c = frame.getContentPane();
		if(isBOptionsVis==false) {
			
			if(isGOptionsVis==true) {
				c.remove(pGizmoOptions);
				isGOptionsVis = false;
			}
			if(isFOptionsVis==true) {
				c.remove(pFileOptions);
				isFOptionsVis = false;
			}
			
			c.add(pBallOptions, "East");
			isBOptionsVis = true;
			
		}
		else {
			c.remove(pBallOptions);
			isBOptionsVis=false;
		}
		repainter();
	}
	
	public void showFileOptions() {
		Container c = frame.getContentPane();
		if(isFOptionsVis==false) {
			
			if(isGOptionsVis==true) {
				c.remove(pGizmoOptions);
				isGOptionsVis = false;
			}
			if(isBOptionsVis==true) {
				c.remove(pBallOptions);
				isBOptionsVis = false;
			}
			c.add(pFileOptions, "East");
			isFOptionsVis = true;
			
		}
		else {
			c.remove(pFileOptions);
			isFOptionsVis=false;
		}
		repainter();
	}
	
	
	
	
	public int getPressedKey() {
		return this.pressedKey;
	}
	
	public void setPressedKey(int pressedKey) {
		this.pressedKey = pressedKey;
	}
	public void setKeyFunc(KeyFunc func){
		this.keyFunc=func;
	}

	public KeyFunc getKeyFunc() {
		return keyFunc;
	}
	
	public void setBotLineText(String text) {
		this.bottomLine.setText(text);
	}
	
	public JComboBox<String> getGizmoDrop() {
		return gizmoDrop;
	}
	public JComboBox<TriggerType> getTriggerDrop(){
		return triggerDrop;
	}
	
	
	
	public BuildModeAction getAction() {
		return this.action;
	}

	public void setAction(BuildModeAction action) {
		this.action = action;
	}
	
	public int getSourceX() {
		return this.sourceX;
	}
	
	public void setSourceX(int x) {
		this.sourceX = x;
	}
	
	public int getSourceY() {
		return this.sourceY;
	}
	
	public void setSourceY(int y) {
		this.sourceY = y;
	}

	public int getTargetX() {
		return this.targetX;
	}
	
	public void setTargetX(int x) {
		this.targetX = x;
	}

	public int getTargetY() {
		return this.targetY;
	}
	
	public void setTargetY(int y) {
		this.targetY = y;
	}



	private class DrawGrid extends JPanel {

	       
        /**
		 * No idea what this is but importing Graphics wanted it
		 */
		private static final long serialVersionUID = 1L;
		
		private GameBoardModel gameBoardModel;
	
        public DrawGrid(GameBoardModel gameBoardModel) {
        	//1 L = 25 Pixels, grid needs to be 20Lx20L, so we need the thing to be 500 pixels long/high
	    	 
            setBackground(Color.white);
            this.setPreferredSize(new Dimension(500, 500));
            this.gameBoardModel = gameBoardModel;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.lightGray);
            int nRowCount = GRID_LINES_X;
            int currentX = 0;
            for (int i = 0; i < nRowCount; i++) {
              g.drawLine(0, currentX, 500, currentX);
             
                currentX = currentX + GRID_SQUARE_PIXELS;
            }
            
            int nColumnCount = GRID_LINES_Y;
            int currentY = 0;
            for (int i = 0; i < nColumnCount; i++) {
                g.drawLine(currentY, 0, currentY, 500);
                currentY = currentY + GRID_SQUARE_PIXELS;
            }
            
            Collection<iPlaceable> tiles = gameBoardModel.getTiles();
            for(iPlaceable tile : tiles) {
            	drawBlob(tile, g);
            }
            Collection<Ball> balls = gameBoardModel.getBalls();
            for(Ball b : balls) {
            drawBall(b, g);
            }
        }
        
        protected void drawBall(Ball b, Graphics g) {
        	g.setColor(b.getColour());
        	int diameter = (int)(b.getDiameter()*GRID_SQUARE_PIXELS);
        	g.fillOval((int)(GRID_SQUARE_PIXELS*b.getX() - (0.5*diameter)), (int)(GRID_SQUARE_PIXELS*b.getY() - (0.5*diameter)), diameter, diameter);
        	
        }
        
        protected void drawCircle(int x, int y, Color colour, Graphics g) {
        	int pixelX = x * GRID_SQUARE_PIXELS;
        	int pixelY = y * GRID_SQUARE_PIXELS;
        	g.setColor(colour);
        	g.fillOval(pixelX, pixelY, GRID_SQUARE_PIXELS, GRID_SQUARE_PIXELS);
        }

        protected void drawSquare(int x, int y, Color colour, Graphics g) {
        	int pixelX = x * GRID_SQUARE_PIXELS;
        	int pixelY = y * GRID_SQUARE_PIXELS;
        	g.setColor(colour);
        	g.fillRect(pixelX, pixelY, GRID_SQUARE_PIXELS, GRID_SQUARE_PIXELS);
        }
        
        protected void drawTriangle(int x, int y, int orientation, Color colour, Graphics g) {
        	int pixelX = x * GRID_SQUARE_PIXELS;
        	int pixelY = y * GRID_SQUARE_PIXELS;
        	Polygon triangle = new Polygon();

    		switch(orientation%4) {
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
        
        protected void drawBlob(iPlaceable tile, Graphics g) {
			//if (tile instanceof RightFlipper || tile instanceof LeftFlipper)
			if (tile instanceof Flipper)
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
			} else {
        	for(int i = tile.getXStartPos(); i<tile.getXEndPos(); i++) {
        		for(int j = tile.getYStartPos(); j < tile.getYEndPos(); j++) {
        			if(tile instanceof RoundBumper) {
        				drawCircle(i, j, tile.getColour(), g);
        			}
        			else if(tile instanceof TriangleBumper) {
        				drawTriangle(i, j, tile.getOrientation(), tile.getColour(), g);
        			}
        			else {
        				drawSquare(i, j, tile.getColour(), g);
        			}

        		}
        	}
			}
        }
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

	
	
	

	JColorChooser gizmoCC = null, ballCC=null;
	JFrame gizmoColourFrame = new JFrame();
	JFrame ballColourFrame = new JFrame();
	public void createColourFrame(String type) {
		if(type.equals("Gizmo")) {
		gizmoCC = new JColorChooser();
		gizmoCC.setColor(getLastColour(type));
		
		
		AbstractColorChooserPanel[] ccp = new AbstractColorChooserPanel[1];
		ccp[0] = gizmoCC.getChooserPanels()[0];
		gizmoCC.setChooserPanels(ccp);
		
		JButton bChooseColour1 = new JButton("Make this my new gizmo colour!");
		bChooseColour1.setName("Gizmo");
		JPanel pan1 = new JPanel(new BorderLayout(5,5));
		pan1.add(gizmoCC, "Center");
		pan1.add(bChooseColour1, "South");
		
		bChooseColour1.addActionListener(new ColourSelectionListener(this));
		
		gizmoColourFrame.add(pan1);
		gizmoColourFrame.pack();
		gizmoColourFrame.setTitle("Gizmo colour chooser!");
		gizmoColourFrame.setLocationRelativeTo(null);
		gizmoColourFrame.setSize(500, 300);
		gizmoColourFrame.setVisible(false);
		}
		else {
			ballCC = new JColorChooser();
			ballCC.setColor(getLastColour(type));
			
			
			AbstractColorChooserPanel[] ccp = new AbstractColorChooserPanel[1];
			ccp[0] = ballCC.getChooserPanels()[0];
			ballCC.setChooserPanels(ccp);
			
			JButton bChooseColour2 = new JButton("Make this my new Ball colour!");
			bChooseColour2.setName("Ball");
			JPanel pan = new JPanel(new BorderLayout(5,5));
			pan.add(ballCC, "Center");
			pan.add(bChooseColour2, "South");
			
			bChooseColour2.addActionListener(new ColourSelectionListener(this));
			
			ballColourFrame.add(pan);
			ballColourFrame.pack();
			ballColourFrame.setTitle("Gizmo colour chooser!");
			ballColourFrame.setLocationRelativeTo(null);
			ballColourFrame.setSize(500, 300);
			ballColourFrame.setVisible(false);
		}
	}
	
	public void hideColourFrame(String type) {
		if(type.equals("Gizmo"))
			gizmoColourFrame.setVisible(false);
		else
			ballColourFrame.setVisible(false);
	}
	
	public void showColourFrame(String type) {
		if(type!=null) {
		if(type.equals("Gizmo")) {
		if(gizmoCC==null) {
			createColourFrame(type);
		}
		gizmoColourFrame.repaint();
		gizmoColourFrame.pack();
		gizmoColourFrame.setLocationRelativeTo(null);
		gizmoColourFrame.setSize(500, 300);
		
		gizmoColourFrame.setVisible(true);
		}
		else {
			if(ballCC==null) {
				createColourFrame(type);
			}
			ballColourFrame.repaint();
			ballColourFrame.pack();
			ballColourFrame.setLocationRelativeTo(null);
			ballColourFrame.setSize(500, 300);
			
			ballColourFrame.setVisible(true);
		}
		}
	}
	
	public void setColourChoiceLbl(Color c, String type) {
		if(type.equals("Gizmo"))
			this.lblGizmoColour.setForeground(c);
		else {
			lblBallColour.setForeground(c);
		}
	}
	
	public Color getLastColour(String type) {
		if(type.equals("Gizmo"))
			return lblGizmoColour.getForeground();
		else
			return lblBallColour.getForeground();
	}
	
	public Color getChoice(String type) {
		if(type.equals("Gizmo"))
		return gizmoCC.getColor();
		else
			return ballCC.getColor();
	}
	public JFrame repainter() {
			
		  frame.repaint();
		  frame.pack();
		  frame.setLocationRelativeTo(null);
		  frame.revalidate();
		
		return frame;
		
    }
	@Override
	public void update(Observable o, Object arg) {
		
			System.out.println("UPDATINGGG IN BUILDER");
			repainter();
		
	}
    
	
	
}
