package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class DeleteBallListener implements ActionListener
{
    BuildGui gui;
    
    public DeleteBallListener(BuildGui gui)
    {
	this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
	gui.setAction(BuildModeAction.DeleteBall);
	gui.setBotLineText("Click on a ball to remove it from the game board");
    }

}
