package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.BuildGui;

public class RunModeListener implements ActionListener {

    private BuildGui buildGui;

    public RunModeListener(BuildGui build_View){
        buildGui=build_View;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        buildGui.setVisible(false);
    }
}
