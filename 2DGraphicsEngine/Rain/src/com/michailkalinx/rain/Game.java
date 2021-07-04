package com.michailkalinx.rain;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.input.Keyboard;
import com.michailkalinx.rain.level.Level;
import com.michailkalinx.rain.level.RandomLevel;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//calculate pixels from resolution pixel size calculator
	//300x168 50400 pixels
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "Rain";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private boolean running = false;
	
	private Screen screen;

	//create an image to draw to
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//get data structure of raster and manipulate array of pixels from the created image
	//access the image
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		//Dimension size = new Dimension(width , height);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = new RandomLevel(64,64);//getTile from Level and RandomLevel 0-3 (4) and why generateLevel in constructor actually calls RandomLevel overridden method and not his empty one instead
		
		addKeyListener(key);
	}

	public synchronized void start() {//this is public in thread sharing res
		running = true;
		thread = new Thread(this, "Display");//this == new Game() , name of thread
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {//new Game() or this in start thread function/operation
		//boolean test = false; int t=0;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1_000_000_000.0 / 60.0 ;//60 times per second
		double delta = 0;
		int frames=0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			//System.out.println(now - lastTime);//distance
			// System.out.println(lastTime + ", " + now);
			//return;
			// System.out.println("Running...");
			// tick();update();
			delta += (now - lastTime) / ns;
			//delta += (now - lastTime);
			//System.out.println(delta + " delta");
//			if(test) {
//				System.out.phttps://www.youtube.com/watch?v=QRahYJqidws&list=PLlrATfBNZ98eOOCk2fOFg7Qg5yoQfFAdf&index=15rintln(t +" t");
//				System.out.println(now -  + lastTime +" TimeTaken");
//			System.exit(0);
//			}
			lastTime = now;
			while (delta >= 1) {//only 60 times per second..delta >= ns
				update();
				updates++;
				delta--;
				//delta-=ns;
				//t++;
				
			}
			render();
			frames++;
//			for(int x = 0; x<=99999999; x++) {
//				for(int y= 0; y<=99999999; y++) {
//					int i=0;
//				}
//			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " ups, " + frames + " fps");
				System.out.println(System.currentTimeMillis() + " | " + timer);
				System.out.println(System.currentTimeMillis() - timer);
				frame.setTitle(title + "    |    " + updates + " ups, " + frames + " fps");
				updates=0;
				frames=0;
				//test=true;
			}
		}
		stop();
	}

	int x=0,y=0;
	public void update() {
		//System.out.println("update RUNNED...");
//		y++;
//		if(y%10==0)x++;//not smooth scrolling map
		//x++;//smooth scrolling map
		//x++;
		key.update();
		if(key.up)  y--;//controlling the map with tiles
		if(key.down)  y++;
		if(key.left)  x--;
		if(key.right)  x++;//x+=16; move one tile right instead of smooth
//		for(int x = 0; x<=99999999; x++) {
//		for(int y= 0; y<=99999999; y++) {
//			int i=0;
//		}
//	}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		//screen.renderOld(x,y);//0,0|x,0
		level.render(x, y, screen);//call generateLevel from overridden method cause Level level = new RandomLevel(64,64);
		
		for (int i=0; i < pixels.length; i++) {
			pixels[i]=screen.pixels[i];
		}
//		for(int x = 0; x<=99999999; x++) {
//		for(int y= 0; y<=99999999; y++) {
//			int i=0;
//		}
//	}
		Graphics g = bs.getDrawGraphics();
//		g.setColor(Color.BLACK);new color(R,G,B)
//		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) { //psvm here driver method
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

}
