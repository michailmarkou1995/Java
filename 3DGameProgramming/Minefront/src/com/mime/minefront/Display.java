package com.mime.minefront;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.JFrame;

import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.input.Controller;
import com.mime.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Minefront Pre-Alpha 0.03";

	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private Game game;
	private boolean running = false;
	private int[] pixels;
	private Render render;
	private InputHandler input;
	private JFrame frame;
	private int newX=0, newY=0, oldX=0, oldY;
	private int fpsInnerText;
	Robot robot;
	
	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(WIDTH,HEIGHT);
		game = new Game();
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running) return;
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	

	@Override
	public void run() {

		int frames=0;
		double unprocessedSeconds=0;//AKA delta time in s(seconds) here not ns(nanoseconds)
		long previousTime = System.nanoTime();
		double secondsPetTick = 1/60.0;
		int tickCount = 0;
		boolean ticked = false;
		long timer = System.currentTimeMillis();

		requestFocus();
		//game loop
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime/1000000000.0;//nanoseconds to seconds
	
			while (unprocessedSeconds > secondsPetTick) {
				tick();
				unprocessedSeconds -= secondsPetTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					frame.setTitle(TITLE + " FPS: " + frames);
					fpsInnerText = frames;
					//System.out.println(frames + "fps");
					previousTime += 1000;
					frames=0;
//					var temp = Controller.jumped;
//					Controller.jumped = !temp;
					//timer++;
					//System.out.println(System.currentTimeMillis() - timer);
					
					//ThreadTest tt1 = new ThreadTest();
					

					//tt1.pause();
					//tt1.run();
					//tt1.start();
				}
			}
			
			if (System.currentTimeMillis() - timer > 1000) {
			Controller.timeJ1 = System.currentTimeMillis() - timer;
			timer += 1000;
			//var temp = Controller.jumped;
			//Controller.jumped = !temp;
			//System.out.println(timer);
			Controller.jumped = false;
			if(Controller.timeJ < 7500) Controller.timeJ++; else Controller.timeJ = 0;
			//Controller.timeJ = timer;
		}
			
//			if (System.currentTimeMillis() - timer > 2000) {
//				Controller.timeJ1 = System.currentTimeMillis() - timer;
//				timer += 2000;
//				//var temp = Controller.jumped;
//				//Controller.jumped = !temp;
//				//System.out.println(timer);
//				Controller.jumped = false;
//				if(Controller.timeJ < 7500) Controller.timeJ++; else Controller.timeJ = 0;
//				//Controller.timeJ = timer;
//			}
			
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
			//Mouse Position Track Debug
			//System.out.println("X: " + InputHandler.MouseX + " Y: " + InputHandler.MouseY);
			
			newX = InputHandler.MouseX;
			newY = InputHandler.MouseY;
			//String temp = newX < oldX ? System.out.println("Left"); : "";
			if (newX > oldX) {
				//System.out.println("Right");
				Controller.turnRightM = true;
			}
			if ( newX == oldX) {
				//System.out.println("Still X");
				Controller.turnLeftM = false;
				Controller.turnRightM = false;
			}
			//if(newX == WIDTH/2 || newX < WIDTH/2)//goes with direct below if
			if (newX < oldX) {
				//System.out.println("Left");
				Controller.turnLeftM = true;
			}
//			if (newY == oldY) {
//				//System.out.println("Still Y");
//			}
					oldX = newX;
					//oldY = newY;
		}
	}

	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render(game);
		
		for (int i = 0; i <WIDTH * HEIGHT; i++) {
			pixels[i] = screen.PIXELS[i];
		}
		
		//Graphics2D g = (Graphics2D) bs.getDrawGraphics();//GRAPHICS Graphics2D
		Graphics g = bs.getDrawGraphics();
		//g.drawImage(img, 0, 0, WIDTH*20, HEIGHT*20, null);
		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("Verdana", 0, 50));//0,1,2,3 bold italics bold and italics
		g.setColor(Color.WHITE);
		g.drawString(fpsInnerText + " fps", 20, 50);//drawString me +"" oxi sketo int tha baraei error
		g.dispose();
		bs.show();
		
	}

	private void tick() {
		try {robot = new Robot();} catch (AWTException e) {e.printStackTrace();}
		//robot.keyPress(KeyEvent.VK_H);
		//robot.keyRelease(KeyEvent.VK_H);
		//System.out.println(Arrays.toString(input.key));//cant overriden because its point to array as whole not for each element! thats why for loop must
		//System.out.println(input.key);
		for (int i =0; i < input.key.length; i++) {
			if(input.key[i] == true) { 
				//System.out.println(i); System.out.println(Arrays.toString(input.key));
			}}
		game.tick(input.key);
		//System.out.println("break");
		
	}

	public static void main(String[] args) {
		    BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		    Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");
			Display game = new Display();
			//JFrame frame = new JFrame();//if here and not in Game class THEN not able to run in on WINDOW FPS LABEL 
//			frame = new JFrame();
//			frame.add(game);
//			frame.setResizable(false);
//			frame.setTitle(TITLE);
//			frame.pack();
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			//frame.setSize(WIDTH,HEIGHT);// remove if setPreffered is set on Constructor
//			frame.setLocationRelativeTo(null);
//			frame.setVisible(true);
			game.frame = new JFrame();
			game.frame.add(game);
			game.frame.setResizable(false);
			game.frame.setTitle(TITLE);//if not here delay to appear a little
			game.frame.pack();
			//game.frame.getContentPane().setCursor(blank);
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//frame.setSize(WIDTH,HEIGHT);// remove if setPreffered is set on Constructor
			game.frame.setLocationRelativeTo(null);
			game.frame.setVisible(true);
			
			game.start();
	}


}
