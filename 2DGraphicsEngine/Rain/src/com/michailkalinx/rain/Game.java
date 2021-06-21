package com.michailkalinx.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.input.Keyboard;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "Rain";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private boolean running = false;
	
	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		
		addKeyListener(key);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
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

	public void run() {
		//boolean test = false; int t=0;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0 ;//60 times per second
		double delta = 0;
		int frames=0;
		int updates = 0;
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
//				System.out.println(t +" t");
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
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " ups, " + frames + " fps");
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
		if(key.right)  x++;
		
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.render(x,y);//0,0|x,0
		
		for (int i=0; i < pixels.length; i++) {
			pixels[i]=screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
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
