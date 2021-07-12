package com.mime.minefront;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RunGame {

	public RunGame(Point WindowLocation) {
	    BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
	    Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");
		Display game = new Display();
		//JFrame frame = new JFrame();//if here and not in Game class THEN not able to run in on WINDOW FPS LABEL 
//		frame = new JFrame();
//		frame.add(game);
//		frame.setResizable(false);
//		frame.setTitle(TITLE);
//		frame.pack();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		//frame.setSize(WIDTH,HEIGHT);// remove if setPreffered is set on Constructor
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
		game.frame = new JFrame();
		game.frame.add(game);
		game.frame.setResizable(false);
		game.frame.setTitle(Display.TITLE);//if not here delay to appear a little
		
		//game.frame.pack();
		game.frame.setSize(Display.getGameWidth(), Display.getGameHeight());
		
		//game.frame.getContentPane().setCursor(blank);
		
		//game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		//frame.setSize(WIDTH,HEIGHT);// remove if setPreffered is set on Constructor
		game.frame.setLocationRelativeTo(null);
		WindowLocation=game.frame.getLocation();
		game.frame.addComponentListener(game.input);//new InputHandler()
		game.frame.setVisible(true);
		
		game.start();
	}
}
