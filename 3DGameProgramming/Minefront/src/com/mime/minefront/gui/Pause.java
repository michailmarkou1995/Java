package com.mime.minefront.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.mime.minefront.RunGame;

public class Pause extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	boolean running = false;
	Thread thread;
	
	public Pause() {
		//add(this);
		startPauseMenu();
		//RunGame.getGameInstance().stop();
	}
	
	@Override
	public void run() {
		//requestFocus();
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
