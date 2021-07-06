package com.mime.minefront;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Minefront Pre-Alpha 0.02";
	
	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private Game game;
	private boolean running = false;
	private int[] pixels;
	private Render render;
	private InputHandler input;
	
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
	
	private void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {
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
		double unprocessedSeconds=0;
		long previousTime = System.nanoTime();
		double secondsPetTick = 1/60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		requestFocus();
		//game loop
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime/1000000000.0;
			
			while (unprocessedSeconds > secondsPetTick) {
				tick();
				unprocessedSeconds -= secondsPetTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + "fps");
					previousTime += 1000;
					frames=0;					
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
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
		g.dispose();
		bs.show();
		
	}

	private void tick() {
		game.tick(input.key);
	}

	public static void main(String[] args) {
			Display game = new Display();
			JFrame frame = new JFrame();
			frame.add(game);
			frame.setResizable(false);
			frame.setTitle(TITLE);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//frame.setSize(WIDTH,HEIGHT);// remove if setPreffered is set on Constructor
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			game.start();
	}


}
