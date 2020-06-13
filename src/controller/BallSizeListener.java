package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class BallSizeListener implements ActionListener
{
    BuildGui gui;
    
    public BallSizeListener(BuildGui gui)
    {
	this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
	gui.setAction(BuildModeAction.ChangeBallSize);
	gui.setBotLineText("Select a ball to change its size to the set value");
    }

}
