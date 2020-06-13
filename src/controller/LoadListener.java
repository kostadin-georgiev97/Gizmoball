package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Ball;
import model.GameBoardModel;
import model.iPlaceable;
import model.FileManagement.FileLoader;
import model.FileManagement.InvalidFileFormatException;

@SuppressWarnings("deprecation")
public class LoadListener extends Observable implements ActionListener {
	JTextField i;
	GameBoardModel gbm;
	public LoadListener(JTextField i, GameBoardModel gbm) {
		this.i = i;
		this.gbm = gbm;
	}
	
	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		String fileName = i.getText();
//		if(fileName.equals("")) {
//			JOptionPane.showMessageDialog(null, "There is nothing in the input box so can't do as you asked");
//
//		}
//		else {
//			fileName = fileName + ".txt";
//			try {
//				FileLoader fl = new FileLoader();
//				Set<iPlaceable> tiles = fl.loadLayout(fileName);
//				gbm.resetNameCount();
//				gbm.setTiles(tiles);
//				Set<Ball> balls = fl.returnBalls();
//				if(balls != null) {
//					gbm.setBalls(balls);
//				}else{
//					balls = new HashSet<>();//making there no balls
//					gbm.setBalls(balls);
//				}
//				gbm.setGravityValue(fl.returnGravity());
//				gbm.setFrictionValues(fl.returnFrictionValues());
//
//				setChanged();
//				notifyObservers(this);
//			} catch (Exception e){
//				System.out.println(e.getMessage());
//				//e.printStackTrace();
//				JOptionPane.showMessageDialog(null,e.getMessage());
//
//			}
//		}
//		i.setText("");
//	}
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		//FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", ".txt");
		//fileChooser.setFileFilter(filter);


		fileChooser.showDialog(null, "Load Layout");

		File chosen = fileChooser.getSelectedFile();

		FileLoader fl = new FileLoader();
		if (chosen != null) {
			System.out.println("reached");
			try {
				if (!chosen.getName().toLowerCase().endsWith(".txt")) {
					throw new InvalidFileFormatException("Incorrect File Format, Layout could not be loaded");
				}

				Set<iPlaceable> tiles = fl.loadLayout(chosen);
				gbm.resetNameCount();
				gbm.setTiles(tiles);
				Set<Ball> balls = fl.returnBalls();
				if (balls != null) {
					gbm.setBalls(balls);
				} else {
					balls = new HashSet<>();//making there no balls
					gbm.setBalls(balls);
				}
				gbm.setGravityValue(fl.returnGravity());
				gbm.setFrictionValues(fl.returnFrictionValues());
				gbm.setConnectedWallGizmos(fl.returnWallConnectedGizmos());

				setChanged();
				notifyObservers(this);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());

			}

			i.setText("");
		}
	}
	}
