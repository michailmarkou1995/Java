package com.mime.minefront.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mime.minefront.RunGame;
import com.mime.minefront.input.InputHandler;

public class Pause extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	boolean running = false;
	Thread thread;
	protected JPanel window = new JPanel();
	InputHandler input;
	public static Pause pausedThread = null;
	public static int ID_PAUSED_THREAD=0;
	
	public Pause() {
		//pausedThread = this;
		setTitle("Minefront Launcher");
		setSize(new Dimension(200, 200));
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		input = new InputHandler();
		addKeyListener(input);
		
		//add(this);
		//RunGame.getGameInstance().wait();
		startPauseMenu();
		//RunGame.getGameInstance().stop();
	}
	
	@Override
	public void run() {
		requestFocus();
		while(running) {
			//System.out.println("Pause Menu");
			try {
				renderMenu();
			} catch(IllegalStateException e) {
				System.out.println("Catched");
				e.printStackTrace();
			}
			
			//updateFrame();
		}
	}
	
	private void updateFrame() {
		
	}

	private void renderMenu() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
//same window window.add this?
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
		if (input.key[KeyEvent.VK_ENTER]) {
			System.out.println(KeyEvent.VK_ENTER);
			//RunGame.getGameInstance().wait();
			//RunGame.getGameInstance().notify();
			RunGame.getGameInstance().continued();
			ID_PAUSED_THREAD=1;
			this.dispose();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		g.dispose();
		bs.show();
	}

	public void startPauseMenu() {
		running = true;
		thread = new Thread(this, "pause");
		thread.start();
	}
	
	public void stopPauseMenu() {//throws new Exception return to caller the handle try catch not here
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
