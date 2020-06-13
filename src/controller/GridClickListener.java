package controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Absorber;
import model.Ball;
import model.GameBoardModel;
import model.GizmoType;
import model.KeyFunc;
import model.TriggerType;
import view.BuildGui;

public class GridClickListener implements MouseListener
{
	private BuildGui gui;
	private GameBoardModel model;
	private Ball ball;

	public GridClickListener(BuildGui gui, GameBoardModel model)
	{
		this.gui = gui;
		this.model = model;
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{

		if (gui.getAction() == BuildModeAction.None)
		{
			gui.setBotLineText("Please select an option!");
		}
		else
		{

			int x = arg0.getX() / BuildGui.GRID_SQUARE_PIXELS;
			int y = arg0.getY() / BuildGui.GRID_SQUARE_PIXELS;
			boolean isOccupied = model.tileOccupied(x, y);
			System.out.println(x + ":" + y + " " + "occupied: " + isOccupied);

			switch (gui.getAction())
			{
			case AddGizmo:
				if (!isOccupied)
				{
					String gizmoType = String.valueOf(gui.getGizmoDrop().getSelectedItem());
					GizmoType type = null;
					switch (gizmoType)
					{
					case "Absorber":
						type = GizmoType.Absorber;
						break;
					case "Circle Bumper":
						type = GizmoType.Circle;
						break;
					case "Square Bumper":
						type = GizmoType.Square;
						break;
					case "Triangle Bumper":
						type = GizmoType.Triangle;
						break;
					case "Left Flipper":
						type = GizmoType.LeftFlipper;
						break;
					case "Right Flipper":
						type = GizmoType.RightFlipper;
						break;
					default:
						break;
					}
					if (gizmoType.equals("Absorber"))
					{
						if (gui.getSourceX() == -1 && gui.getSourceY() == -1 && gui.getTargetX() == -1
								&& gui.getTargetY() == -1)
						{
							if (!isOccupied)
							{
								gui.setSourceX(x);
								gui.setSourceY(y);
								gui.setBotLineText(
										"Click on another empty grid location to choose the opposite corner of the absorber.");
							}
							else
							{
								gui.setBotLineText(
										"This field is not empty. Click on an empty grid location to choose one of the absorber's corners.");
							}
						}
						else
							if (gui.getSourceX() != -1 && gui.getSourceY() != -1 && gui.getTargetX() == -1
							&& gui.getTargetY() == -1)
							{
								if (!isOccupied)
								{
									gui.setTargetX(x);
									gui.setTargetY(y);
									int startX = 0, startY = 0, endX = 0, endY = 0;
									if (gui.getSourceX() <= gui.getTargetX() && gui.getSourceY() <= gui.getTargetY())
									{
										startX = gui.getSourceX();
										startY = gui.getSourceY();
										endX = gui.getTargetX();
										endY = gui.getTargetY();
									}
									else
										if (gui.getSourceX() > gui.getTargetX() && gui.getSourceY() < gui.getTargetY())
										{
											startX = gui.getSourceX() - (gui.getSourceX() - gui.getTargetX());
											startY = gui.getSourceY();
											endX = gui.getTargetX() + (gui.getSourceX() - gui.getTargetX());
											endY = gui.getTargetY();
										}
										else
											if (gui.getSourceX() < gui.getTargetX()
													&& gui.getSourceY() > gui.getTargetY())
											{
												startX = gui.getSourceX();
												startY = gui.getSourceY() - (gui.getSourceY() - gui.getTargetY());
												endX = gui.getTargetX();
												endY = gui.getTargetY() + (gui.getSourceY() - gui.getTargetY());
											}
											else
												if (gui.getSourceX() > gui.getTargetX()
														&& gui.getSourceY() > gui.getTargetY())
												{
													startX = gui.getSourceX() - (gui.getSourceX() - gui.getTargetX());
													startY = gui.getSourceY() - (gui.getSourceY() - gui.getTargetY());
													endX = gui.getTargetX() + (gui.getSourceX() - gui.getTargetX());
													endY = gui.getTargetY() + (gui.getSourceY() - gui.getTargetY());
												}
												else
												{

												}
									if (model.placeAbsorber(startX, startY, endX + 1, endY + 1))
									{
										gui.setSourceX(-1);
										gui.setSourceY(-1);
										gui.setTargetX(-1);
										gui.setTargetY(-1);
										gui.repainter();
										gui.setBotLineText(
												"Click on an empty grid location to choose one of the absorber's corners.");
									}
									else
									{
										gui.setTargetX(-1);
										gui.setTargetY(-1);
										gui.setBotLineText(
												"The absorber intersects with other gizmos! Please, choose another empty field for the opposite corner of the absorber!");
									}
								}
								else
								{
									gui.setBotLineText(
											"This field is not empty. Click on an empty grid location to choose one of the absorber's corners.");
								}
							}
							else
							{

							}
					}
					else
					{
						if (model.placeGizmo(type, x, y))
						{
							gui.repainter();
							gui.setBotLineText("Click on an empty grid location to add Gizmo.");
						}
						else
						{
							gui.setBotLineText(
									"This field is already occupied or the gizmo goes out of bounds! Please, choose an empty grid location to add the gizmo!");
						}
					}
				}
				else
				{
					gui.setBotLineText(
							"This field is already occupied! Please, choose an empty grid location to add the gizmo!");
				}
				break;
			case RotateGizmo:
				if (isOccupied)
				{
					if (model.getTileAt(x, y).rotate())
					{
						gui.repainter();
					}
					else
					{
						gui.setBotLineText("This gizmo can't rotate! Please, choose another gizmo to rotate it!");
					}
				}
				else
				{
					gui.setBotLineText("This field is empty! Please, choose a gizmo to rotate it!");
				}
				break;
			case MoveGizmo:
				if (gui.getSourceX() == -1 && gui.getSourceY() == -1 && gui.getTargetX() == -1
				&& gui.getTargetY() == -1)
				{
					if (!isOccupied)
					{
						gui.setBotLineText("This field is empty. Please, select an object to move it!");
					}
					else
					{
						gui.setSourceX(x);
						gui.setSourceY(y);
						gui.setBotLineText("Please, choose an empty field to move the object to!");
					}
				}
				else
					if (gui.getSourceX() != -1 && gui.getSourceY() != -1 && gui.getTargetX() == -1
					&& gui.getTargetY() == -1)
					{
						if (!isOccupied)
						{

							gui.setTargetX(x);
							gui.setTargetY(y);
							if (model.moveGizmo(gui.getSourceX(), gui.getSourceY(), gui.getTargetX(), gui.getTargetY()))
							{
								gui.setSourceX(-1);
								gui.setSourceY(-1);
								gui.setTargetX(-1);
								gui.setTargetY(-1);
								gui.repainter();
								gui.setBotLineText("Click on an object on the board to move it.");
							}
							else
							{
								gui.setTargetX(-1);
								gui.setTargetY(-1);
								gui.setBotLineText(
										"The gizmo intersects with other gizmos or goes out of bounds! Please, choose an empty field to move the object to!");
							}
						}
						else
						{
							gui.setBotLineText(
									"This field is not empty. Please, select an empty field to move the object to!");
						}
					}
				break;
			case DeleteGizmo:
				if (isOccupied)
				{
					if (model.deleteGizmoAt(x, y))
					{
						gui.repainter();
						gui.setBotLineText("Click on a gizmo to delete it.");
					}
					else
					{
						gui.setBotLineText("This gizmo could not be deleted!");
					}
				}
				else
				{
					gui.setBotLineText("This is not a gizmo! Please, select a gizmo to delete!");
				}
				break;
			case SetGizmoColour:
				boolean ballChanged = false;
				String type = "Gizmo";
		

				if (isOccupied)
				{
					model.getTileAt(x, y).setColour(gui.getLastColour(type));

					gui.repainter();
					gui.setBotLineText("Gizmo Colour changed!");

				}
				else
				{
					if(!ballChanged)
						gui.setBotLineText("This is not a gizmo! Please, select a gizmo to change its colour!");
				}
				break;
			case SetBallColour:
				boolean ballChanged1 = false;
				String type1 = "Ball";
				for (Ball b : model.getBalls())
				{
					if (b.getX() - 0.5 == x && b.getY() - 0.5 == y)
					{
						b.setColour(gui.getLastColour(type1));
						gui.repainter();
						gui.setBotLineText("Ball Colour Changed! ");
						ballChanged1=true;
						break;
					}
				}

				if(!ballChanged1)
					gui.setBotLineText("This is not a ball! Please, select a ball to change its colour!");
			
				break;
			case ConnectKey:
				if (gui.getPressedKey() == KeyEvent.VK_UNDEFINED)
				{
					gui.setBotLineText("Press a key from the keyboard to connect to a gizmo.");
				}
				else
				{
					if (isOccupied)
					{
						if (model.getTileAt(x, y).setKeyCode(gui.getPressedKey())
								&& model.getTileAt(x, y).setKeyFunc(gui.getKeyFunc()))
						{
							// activate it right now its defaulted
							gui.setPressedKey(KeyEvent.VK_UNDEFINED);
							gui.setKeyFunc(KeyFunc.undefined);
							gui.setBotLineText(
									"You've successfully binded the keypress! Press another key from the keyboard to connect to a gizmo.");
						}
						else
						{
							gui.setBotLineText(
									"This gizmo is already connected to a key! Please chose another gimo to bind "
											+ KeyEvent.getKeyText(gui.getPressedKey()) + " to.");
						}
					}
					else
					{
						gui.setBotLineText("This field is empty. Please select a gizmo to bind "
								+ KeyEvent.getKeyText(gui.getPressedKey()) + " to.");
					}
				}
				break;
			case DisconnectKey:
				if (isOccupied)
				{
					if (model.getTileAt(x, y).disconnectKey())
					{
						gui.setBotLineText("Success! Please, click on another gizmo to disconnect its keybind!");
					}
					else
					{
						gui.setBotLineText(
								"This gizmo is not connected to a key. Please, click on a gizmo to disconnect its keybind!");
					}
				}
				else
				{
					gui.setBotLineText("This field is empty. Please, click on a gizmo to disconnect its keybind!");
				}
				break;
			case setTriggerType:
				if (isOccupied)
				{

					if(!(model.getTileAt(x,y).getGizmoType().equals(GizmoType.LeftFlipper)||model.getTileAt(x,y).getGizmoType().equals(GizmoType.RightFlipper)||model.getTileAt(x,y).getGizmoType().equals(GizmoType.Absorber)))
					{
						TriggerType tt = (TriggerType)gui.getTriggerDrop().getSelectedItem();
						if(tt == TriggerType.TRIANGLE_ROTATE) {
							if(model.getTileAt(x,y).getGizmoType().equals(GizmoType.Triangle)) {
								model.getTileAt(x, y).setTriggerType(tt);	
							}
							else {
								gui.setBotLineText("Sorry, the chosen TriggerType: \""+( gui.getTriggerDrop().getSelectedItem())+"\" only works on triangles!");
								break;
							}
						}
						else {
							model.getTileAt(x, y).setTriggerType(tt);

						}
						gui.setBotLineText("Success! TriggerType "+( gui.getTriggerDrop().getSelectedItem())+" applied to "+model.getTileAt(x,y).getName());
					}else {
						gui.setBotLineText("Invalid Gizmo selected, please choose a bumper");
					}

				}
				else
				{
					gui.setBotLineText("This field is empty. Please, click on a bumper to set its trigger");
				}
				break;
			case AddBall:
				if (!isOccupied){
					model.addBall(x+0.5,y+0.5,10,10);//TODO: actual velocities??

					gui.repainter();
					//gui.setAction(BuildModeAction.None);
					gui.setBotLineText("Ball placed, click more squares to add more!! ");
				}
				else{
					Absorber a = null;
					//Gizmo ab = null;
					if(model.getTileAt(x, y) instanceof Absorber)
						 a = (Absorber)model.getTileAt(x, y);
					if(a!=null) {
						Ball b = model.addBall(x+0.5,y+0.5,10,10);
						
						if(b!=null) {
							if(!a.hit(b)) {
								model.removeBall(b);
								gui.setBotLineText("The absorber can only hold one ball at a time, select an empty position.");
								break;
							}
							gui.repainter();
						}
						
					}
					
					gui.setBotLineText("Select an empty position.");
				}
				break;
			case DeleteBall:
				boolean deleted = false;
				for (Ball b : model.getBalls())
				{
					if (b.getX() - 0.5 == x && b.getY() - 0.5 == y)
					{
						Absorber a = null;
						if(model.getTileAt(x, y) instanceof Absorber) {
							System.out.println("KNEW ABOUT ABS");
							 a = (Absorber)model.getTileAt(x, y);
						}
						if(a!=null) {
							a.trigger();
							a.resetHit();
							a.resetHit();
							
						}
						
						model.getBalls().remove(b);
						deleted = true;
						gui.repainter();
						gui.setAction(BuildModeAction.None);
						gui.setBotLineText(" ");
						break;
					}
				}
				if (!deleted)
				{
					gui.setBotLineText("Select a position holding a ball");
				}
				break;
			case MoveBall:
				if (ball == null)
				{
					for (Ball b : model.getBalls())
					{
						if (b.getX() - 0.5 == x && b.getY() - 0.5 == y)
						{
							ball = b;
							gui.repainter();
							gui.setBotLineText("Select an empty position to move the ball too");

							break;
						}
					}
					if (ball == null)
					{
						gui.setBotLineText("Select a position holding a ball");
					}
				}
				else
				{
					if (isOccupied)
					{
						gui.setBotLineText("Select an empty position");
					}
					else
					{
						ball.setXY(x + 0.5, y + 0.5);
						gui.repainter();
						ball = null;
						gui.setAction(BuildModeAction.None);
						gui.setBotLineText(" ");
					}
				}
				break;
			case ChangeBallSize:
				boolean changed = false;
				for (Ball b : model.getBalls())
				{
					if (b.getX() - 0.5 == x && b.getY() - 0.5 == y)
					{
						double r;
						changed = true;


							r = gui.getBallSize();

							boolean isBlocked = false;
							double ballX = b.getX();
							double ballY = b.getY();
							double startXCheck = ballX-1;
							double startYCheck = ballY-1;
							
							if(r>0.5 && r<=1) {
							for(int i=0 ; i< 3 ; i++) {
								for(int j= 0 ; j<3 ; j++) {
									if(!(j==1&&i==1)) //as that would be the centre, original ball
										isBlocked = model.tileOccupied((int)startXCheck+i, (int)startYCheck+j);
								}
							}
							
							}
							if(!isBlocked) {
							b.setBallRadius(r);
							gui.repainter();
//							gui.setAction(BuildModeAction.None);
							gui.setBotLineText("Ball Size Changed, Choose another ball to change the Size of it");
							}
							else {
								gui.setBotLineText("There wasn't enough space around the ball to enlarge it, sorry!");
							}

						break;
					}
				}
				if (!changed)
				{
					gui.setBotLineText("Select a position holding a ball");
				}
				break;
			case ChangeBallVel:
				boolean found = false;
				for (Ball b : model.getBalls())
				{
					if (b.getX() - 0.5 == x && b.getY() - 0.5 == y)
					{
						double vx, vy;
						found = true;

						{
							vx = gui.getVX();
							vy = gui.getVY();
							b.setXYVelo(vx, vy);
							gui.setBotLineText("Ball "+b.getName()+" XVelocity: "+b.getXVelo()+" YVelocity: "+b.getYVelo()+". Choose  a New ball to change its velocity!");

							gui.repainter();
//							gui.setAction(BuildModeAction.None);
//							gui.setBotLineText(" ");
						}

					}
				}
				if (!found)
				{
					gui.setBotLineText("Select a position holding a ball");
				}
				break;
			case RemoveTrigger:
				if (isOccupied)
				{
					if(!(model.getTileAt(x,y).getGizmoType().equals(GizmoType.LeftFlipper)||model.getTileAt(x,y).getGizmoType().equals(GizmoType.RightFlipper))){
						if(model.getTileAt(x,y).getTriggerType().equals(TriggerType.NONE)){
							gui.setBotLineText("No Trigger associated with selected Gizmo");
						}else {
							model.getTileAt(x, y).setTriggerType(TriggerType.NONE);

							gui.setBotLineText("Success! Please, click on another gizmo to remove its trigger functionality!");
						}
					}
					else
					{
						gui.setBotLineText(
								"This gizmo is not a bumper, click on a bumper to remove its trigger functionality!");
					}
				}
				else
				{
					gui.setBotLineText("This field is empty. Please, click on a gizmo to remove its trigger");

				}
				break;
			case ConnectGizmo:

				if(isOccupied){
					if(model.getSelectedGizmo()==null) {
						model.setSelectedGizmo(model.getTileAt(x, y));
						gui.setBotLineText("Please selet a Gizmo to connect to "+model.getSelectedGizmo().getName());
					}else{
						//already selected
						model.getSelectedGizmo().connectGizmo(model.getTileAt(x,y));
						gui.setBotLineText("Connected "+model.getSelectedGizmo().getName()+" to "+model.getTileAt(x,y).getName());
						model.setSelectedGizmo( null);

					}
				}else {
					if(model.getSelectedGizmo()!=null) {
						gui.setBotLineText("Filed is empty. Please click a on Gizmo to connect to  ");
					}else {
						gui.setBotLineText("Field is empty. Please click on a Gizmo to connect to another Gizmo");
					}
				}

				break;
				case ConnectWalls:

					if(isOccupied){
						if(model.getSelectedGizmo()==null) {
							model.setSelectedGizmo(model.getTileAt(x, y));
							gui.setBotLineText("Connected Gizmo " +model.getTileAt(x,y).getName()+" to Outer Walls");
							model.getSelectedGizmo().connectWalls();
							model.connectGizmoToWalls(model.getSelectedGizmo());
							model.setSelectedGizmo( null);

						}
					}else {
						if(model.getSelectedGizmo()!=null) {
							gui.setBotLineText("Field is empty. Please click a on Gizmo to connect to the Outer Walls ");
						}
					}

					break;
				case DisconnectWalls:

					if(isOccupied){
						if(model.getSelectedGizmo()==null) {
							model.setSelectedGizmo(model.getTileAt(x, y));
							gui.setBotLineText("Disonnected Gizmo " +model.getTileAt(x,y).getName()+" from Outer Walls");
							model.getSelectedGizmo().disconnectWalls();
							model.removeConnectedWallGizmo(model.getSelectedGizmo());
							model.setSelectedGizmo( null);

						}
					}else {
						if(model.getSelectedGizmo()!=null) {
							gui.setBotLineText("Field is empty. Please click a on Gizmo to connect to the Outer Walls ");
						}
					}

					break;

			case DisconnectConnectedGizmo:
				if(isOccupied) {
					gui.setBotLineText("Disconnected all Gizmos connected to "+model.getTileAt(x,y).getName());

					model.getTileAt(x,y).disconnectConnectedGizmos();
				}else {
					gui.setBotLineText("Empty field selected please click ona Gizmo!");
				}
				break;
			default:
				break;
			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
