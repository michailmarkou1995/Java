package com.mime.minefront;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.mime.minefront.gui.Launcher;

//Main JFrame here for main method to be called
public class RunMiniGame {
	
	static Color game, game1;

	public RunMiniGame() {
	    BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
	    Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");
	    game = new Color(640, 390); 
	 //   game1=new Color();

		game.frame = new JFrame();
		game.frame.add(game);
		game.frame.setResizable(false);
		game.frame.setTitle("Color");
		
		game.frame.setSize(game.getGameWidth(), game.getGameHeight());
		
		game.frame.getContentPane().setCursor(blank);
		game.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				//System.exit(0);
				Display.setLauncherInstance(new Launcher(0));
				//RunMiniGame.getDispose();
				//RunMiniGame.getGameInstance(RunGame.getGameInstance()).stop();
			}
		});
		game.frame.setLocationRelativeTo(null);
		//game.frame.addComponentListener(game.input);
		game.frame.setVisible(true);
		
		Launcher.getDispose();
		game.start();
		//game1.start(); //if thread start is not in Constructor problem of Instances of new Thread is not possible
		stopMenuThread();
	}
	
	private void stopMenuThread() {
		Display.getLauncherInstance().stopMenu();
	}
	
	public static Color getGameInstance() {
		return RunMiniGame.game;
	}
}
