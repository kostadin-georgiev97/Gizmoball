package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class MoveBallListener implements ActionListener
{
    BuildGui gui;
    
    public MoveBallListener(BuildGui gui)
    {
	this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
	gui.setAction(BuildModeAction.MoveBall);
	gui.setBotLineText("Select a ball to move");
    }

}
