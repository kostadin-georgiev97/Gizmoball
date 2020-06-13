package view;

import java.awt.Container;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.Controller;
import model.GameBoardModel;


public class GBallView implements Observer{


//GBallGui runModeGui;
//GBallGui buildModeGui;
//Model model;
//GBallListener runModeListener?;

public void createBuildGui() {
	
}

public void createRunGui() {
	
}

public void createAndShowGui() {
	
}

public void buildMode() {
	
}

/*********************************************************************************************/

private JPanel buildButtons;
private JPanel runButtons;
private JMenuBar buildBar;
private JMenuBar runBar;

private Container contentPane;
private JTabbedPane tabPane;
private JFrame frame1;
private JFrame frame2;
private KeyListener keyListener;
private GameBoardModel model;
private BuildGui bg;
private RunGui rg;
public GBallView(GameBoardModel model,KeyListener keyListener) {
	this.model =model;
	this.keyListener = keyListener;
	this.model = model;
	makeBuildFrame();
	makeRunFrame();
	
	//actionScreen?.addObserver(this);
}

private void makeBuildFrame() {
	bg = new BuildGui(model);
	frame1 = bg.setUp();
	bg.addObserver(this);
	frame1.setVisible(true);
	frame1.addKeyListener(keyListener);
}

private void makeRunFrame() {
	rg = new RunGui(model);
	frame2 = rg.setUp();
	//frame2.setVisible(true);
	rg.addObserver(this);
	frame2.addKeyListener(keyListener);
	
}



public static void main(String[] args) {
	GameBoardModel gbm = new GameBoardModel();
	Controller controller = new Controller(gbm);
	GBallView gbView = new GBallView(gbm,controller);
}

@Override
public void update(Observable arg0, Object arg1) {
	if(frame1.isVisible()) {
		System.out.println("Switching to Run Mode");
		model.saveTempBoard();
		frame1.setVisible(false);
		frame2.setVisible(true);
		frame2.repaint();
	}
	else {
		System.out.println("Switching to Build Mode");
		model.loadTempBoard();
		frame2.setVisible(false);
		frame1.setVisible(true);
		frame1.repaint();
	}
	
}

}
/*
 * TODO
@Override
public void update(Observable o, Object arg) {
	//IDK But it'll be wanted eventually 
}
*/



 