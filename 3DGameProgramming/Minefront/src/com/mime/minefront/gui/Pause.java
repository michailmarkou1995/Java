package com.mime.minefront.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mime.minefront.Display;
import com.mime.minefront.RunGame;
import com.mime.minefront.input.Controller;
import com.mime.minefront.input.InputHandler;

public class Pause extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	boolean running = false;
	Thread thread;
	protected JPanel window = new JPanel();
	InputHandler input, input1;
	public static Pause pausedThread = null;
	public static Pause pausedThreadTry2 = null;
	public static int ID_PAUSED_THREAD=0;
	public static Thread statusThread;
	
	public Pause() {
		//pausedThreadTry2 = this;
//		setTitle("Minefront Launcher");
//		setSize(new Dimension(200, 200));
//		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setLocationRelativeTo(null);
//		
//		setResizable(false);
//		setVisible(true);
//		window.setLayout(null);
//		input = new InputHandler();
		//addKeyListener(input);
		 input1 = RunGame.getGameInstance().input;
		addKeyListener(input1);
		
		//add(this);
		//RunGame.getGameInstance().wait();
		startPauseMenu();
		RunGame.getGameInstance().paused();
		//RunGame.getGameInstance().stop();
		pausedThreadTry2 = this;//works only here after whole Construct Object initialized
	}
	
	@Override
	public void run() {
		requestFocus();
		while(running) {
			System.out.println("Pause Menu");
			//System.out.println("Thread is "+thread.isAlive());
			try {
				inputKey(input1.key);
				//System.out.println("Keep Pause");
				//System.out.println(input1.key[KeyEvent.VK_ESCAPE]);
				//if(Controller.Pause_Menu != null) {Controller.Pause_Menu.stopPauseMenu(); Controller.Pause_Menu = null;}
				//renderMenu();
			} catch(IllegalStateException e) {
				System.out.println("Catched");
				e.printStackTrace();
				//stopPauseMenu();
			}
			
			//updateFrame();
		}
	}
	
	private void updateFrame() {
		
	}

	private void renderMenu() {
		if (input1.key[KeyEvent.VK_ESCAPE]) {
			//System.out.println(KeyEvent.VK_ESCAPE);
			System.out.println(input1.key[KeyEvent.VK_ESCAPE]);
			//RunGame.getGameInstance().wait();
			//RunGame.getGameInstance().notify();
			this.dispose();
			RunGame.getGameInstance().continued();
			//ID_PAUSED_THREAD=1;
			//this.dispose();
			Controller.not_paused=true;
			
			//works if new window only???
			try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}//if only 2 windows opened as seperate threads work
			//if(Controller.Pause_Menu != null) {Controller.Pause_Menu.stopPauseMenu(); Controller.Pause_Menu = null;}
		}
		if (input1.key[KeyEvent.VK_ENTER]) {
			this.dispose();
			Display.setLauncherInstance(new Launcher(0));
		}
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(2);
			return;
		}
//same window window.add this?
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
//		if (input.key[KeyEvent.VK_ESCAPE]) {
//			System.out.println(KeyEvent.VK_ESCAPE);
//			//RunGame.getGameInstance().wait();
//			//RunGame.getGameInstance().notify();
//			this.dispose();
//			RunGame.getGameInstance().continued();
//			//ID_PAUSED_THREAD=1;
//			//this.dispose();
//			
//			//works if new window only???
//			//try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}//if only 2 windows opened as seperate threads work
//			
//		}
//		if (input.key[KeyEvent.VK_ENTER]) {
//			this.dispose();
//			Display.setLauncherInstance(new Launcher(0));
//		}
		g.dispose();
		bs.show();
	}

	public synchronized void startPauseMenu() {
		running = true;
		thread = new Thread(this, "paused");
		//statusThread=thread;
		thread.start();
	}
	
	public synchronized void stopPauseMenu() {//throws new Exception return to caller the handle try catch not here
		try {
			//System.out.println(statusThread.getState());
			thread.join();
			//System.out.println("Thread is "+thread.isAlive());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static Pause getPauseInstance() {
//		if (pausedThreadTry2 == null) {
//			pausedThreadTry2 = new Pause();
//		}
		return pausedThreadTry2;
	}
	
	private boolean inputKey(boolean[] keys) {
		boolean test = (keys[KeyEvent.VK_ESCAPE] );//== input1.key[KeyEvent.VK_ESCAPE]
		//System.out.println(test);
		if (input1.key[KeyEvent.VK_1]) {
			//System.out.println(input1.key[KeyEvent.VK_ESCAPE]);
			System.out.println(input1.KeyPressedButton);
			//InputHandler.KeyPressedButton=false;
			//this.dispose();
			RunGame.getGameInstance().continued();

			Controller.not_paused=true;
			
			//works if new window only???
			try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}//if only 2 windows opened as seperate threads work
			//if(Controller.Pause_Menu != null) {Controller.Pause_Menu.stopPauseMenu(); Controller.Pause_Menu = null;}
		}
		if (input1.key[KeyEvent.VK_2]) {
			this.dispose();
			Display.setLauncherInstance(new Launcher(0));
		}
		return test;
	}
	
//	private boolean inputKey(boolean[] keys) {
//		boolean test = (keys[KeyEvent.VK_ESCAPE] );//== input1.key[KeyEvent.VK_ESCAPE]
//		System.out.println(test);
//		if (input1.key[KeyEvent.VK_ESCAPE]) {
//			System.out.println(input1.key[KeyEvent.VK_ESCAPE]);
//
//			this.dispose();
//			RunGame.getGameInstance().continued();
//
//			Controller.not_paused=true;
//			
//			//works if new window only???
//			try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}//if only 2 windows opened as seperate threads work
//			//if(Controller.Pause_Menu != null) {Controller.Pause_Menu.stopPauseMenu(); Controller.Pause_Menu = null;}
//		}
//		if (input1.key[KeyEvent.VK_ENTER]) {
//			this.dispose();
//			Display.setLauncherInstance(new Launcher(0));
//		}
//		return test;
//	}

}
