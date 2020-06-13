package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class AddBallListener implements ActionListener
{
    BuildGui gui;
    
    public AddBallListener(BuildGui gui)
    {
	this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
	gui.setAction(BuildModeAction.AddBall);
	gui.setBotLineText("Click on an empty square to add a ball in that position");
    }

}
