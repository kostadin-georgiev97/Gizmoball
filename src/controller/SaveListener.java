package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.GameBoardModel;
import model.FileManagement.FileSaver;

public class SaveListener implements ActionListener {
    JTextField i;
    GameBoardModel gbm;

    public SaveListener(JTextField i, GameBoardModel gbm) {
        this.i = i;
        this.gbm = gbm;
    }

    @Override
//    public void actionPerformed(ActionEvent arg0) {
//        String fileName = i.getText();
//        if (fileName.equals("")) {
//            JOptionPane.showMessageDialog(null, "Please enter a filename in the inputbox provided");
//
//        } else {
//            fileName = fileName + ".txt";
//            try {
//                new FileSaver().saveLayout(fileName, gbm.getTiles(), gbm.getBalls(),false,gbm.getFrictionValues(),gbm.getGravityValue());
//
//
//            } catch (FileAlreadyExistsException e) {
//                System.out.println(e.getMessage());
//                int answer =JOptionPane.showConfirmDialog(null,"Do you want to overwrite " +fileName+"?");
//                if(answer== JOptionPane.YES_OPTION){
//                    try {
//                        new FileSaver().saveLayout(fileName, gbm.getTiles(), gbm.getBalls(),true,gbm.getFrictionValues(),gbm.getGravityValue());
//
//                    } catch (IOException e1) {
//                        System.out.println(e.getMessage());
//                        JOptionPane.showMessageDialog(null, e1.getMessage());
//                    }
//
//                }
//
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//                JOptionPane.showMessageDialog(null, e.getMessage());
//            }
//            i.setText("");
//        }
//
//    }
        public void actionPerformed(ActionEvent arg0) {
        Object[] options = {"Standard Format", "Extended Format", "Cancel"};

        int option = JOptionPane.showOptionDialog(null, "What save format would you like to choose?", "Save Format", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, JOptionPane.NO_OPTION);
        JFileChooser fc = new JFileChooser();

        if (option < 2) {


            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", ".txt");
            fc.setFileFilter(filter);
            int chosen = fc.showSaveDialog(null);

            if (chosen == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                FileSaver fs = new FileSaver();
                if (file != null) {
                    if (!file.getName().toLowerCase().endsWith(".txt")) {
                        file = new File(file.getParentFile(), file.getName() + ".txt");
                    }

                    try {
                        if (option == 1) {
                            fs.saveLayout(file, gbm.getTiles(), gbm.getBalls(), gbm.getFrictionValues(), gbm.getGravityValue());
                        } else{
                            fs.saveStandardLayout(file, gbm.getTiles(), gbm.getBalls(), gbm.getFrictionValues(), gbm.getGravityValue());

                    }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
//                JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            }
            i.setText("");

        }
    }
}
