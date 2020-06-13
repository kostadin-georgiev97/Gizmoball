package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class BallVelListener implements ActionListener
{
    BuildGui gui;
    
    public BallVelListener(BuildGui gui)
    {
	this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
	gui.setAction(BuildModeAction.ChangeBallVel);
	gui.setBotLineText("Select a ball to change its velocity to the set values");
    }

}
