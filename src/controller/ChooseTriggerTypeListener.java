package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class ChooseTriggerTypeListener implements ActionListener {
    private BuildGui gui;

    public ChooseTriggerTypeListener(BuildGui bg) {
        gui=bg;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        gui.setAction(BuildModeAction.setTriggerType);
        System.out.println("here");
                gui.setBotLineText("You have selected Trigger Type:" + gui.getTriggerDrop().getSelectedItem()+" Choose a Gizmo to apply it to");


            }
        }



