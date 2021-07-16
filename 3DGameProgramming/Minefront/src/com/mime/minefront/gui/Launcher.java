package com.mime.minefront.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.mime.minefront.Configuration;
import com.mime.minefront.Display;
import com.mime.minefront.RunMiniGame;
import com.mime.minefront.RunGame;
import com.mime.minefront.input.InputHandler;

public class Launcher extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	
	protected JPanel window = new JPanel();
	private JButton play, options, help, quit;
	private Rectangle rplay, roptions, rhelp, rquit;//if not = something e.g. 0 is null pointer
	Configuration config = new Configuration();
	boolean running = false;
	Thread thread;
	Display display;
	JFrame frame = new JFrame(); //perito mono gia ama NO extend JFrame + frame.add(this); MUST then
	
	protected int width = 800, height = 400, buttonWidth = 80, buttonHeight = 40;

	public Launcher(int id) {//, Display display
		//if(Display.getGameInstance(display) != null)Display.getGameInstance(display).stop();
		//if(Display.getGameInstance(RunGame.getGameInstance()) != null)Display.getGameInstance(RunGame.getGameInstance()).stop();

		//this.display=display; //if takes renderMenu from Display only then need to initialize here
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setUndecorated(true);
		//new JFrame is not neede because we use directly JFrame extends
		setTitle("Minefront Launcher");
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//getContentPane().add(window);
		//add(display);//display the component/BufferStrategy//commented out because moved from Display to Launcher renderMenu();
		//add(this);//frame.add(this); only if frame created //only if no extend JFrame and has Fields in class with new JFrame();
		setLocationRelativeTo(null);
		
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		
		if (id == 0)
		drawButtons();

		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
		//how to go back in Thread of Main menu disposing the Game? and closing the thread?
		startMenu();
		//display.start();/*start only when press PLAY close or hold the startMenu when return the opposite can be done?*/
	}
	
	public void updateFrame() {
		//setLocation(500,200);//live update in debug mode hot swap code
		//System.out.println("X: " + InputHandler.MouseX + " Y: " + InputHandler.MouseY);
		if(InputHandler.dragged) {
			Point p = getLocation();//frame.getLocation if you used Object independly of extend JFrame otherwise Glitchy Movement
			setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX,
					p.y + InputHandler.MouseDY - InputHandler.MousePY);
			//int x = getX();//frame.getY that itan logo JFRame oxi input handler
			//int y = getY();
			//System.out.println(x + InputHandler.MouseDX - InputHandler.MousePX);
			//global window X, Y + Spot you Start Dragging - spot you pressed for dragging static
//			setLocation(x + InputHandler.MouseDX - InputHandler.MousePX,
//					y + InputHandler.MouseDY - InputHandler.MousePY);//frame.setLocation
		}
	}
	
	public void startMenu() {
		//new Thread(this, "menu").start();
		running = true;
		thread = new Thread(this, "menu");
		thread.start();
		if(Display.getGameInstance(RunGame.getGameInstance()) != null) {
			RunGame.getDispose();
			Display.getGameInstance(RunGame.getGameInstance()).stop();
		}
	}
	
	public void stopMenu() {//throws new Exception return to caller the handle try catch not here
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void run() {
		requestFocus();
		while(running) {
			//System.out.println("Menu Running");
			//System.out.println("Menu thread running see in Debug mode console ");
			//new Display().renderMenu();
			//display.renderMenu();
			try {
				renderMenu();
				//System.out.println(Thread.activeCount());
			} catch(IllegalStateException e) {
				System.out.println("Catched");
				e.printStackTrace();
			}
			
			updateFrame();
		}
	}
	
	//deprecated GUI non-Custom
	private void drawButtons() {
		play = new JButton("Play!");
		rplay = new Rectangle(width/2 - buttonWidth/2, 90, buttonWidth, buttonHeight);
		play.setBounds(rplay);
		window.add(play);
		
		options = new JButton("Options");
		roptions = new Rectangle(width/2 - buttonWidth/2, 140, buttonWidth, buttonHeight);
		options.setBounds(roptions);
		window.add(options);
		
		help = new JButton("Help!");
		rhelp = new Rectangle(width/2 - buttonWidth/2, 190, buttonWidth, buttonHeight);
		help.setBounds(rhelp);
		window.add(help);
		
		quit = new JButton("Quit!");
		rquit = new Rectangle(width/2 - buttonWidth/2, 240, buttonWidth, buttonHeight);
		quit.setBounds(rquit);
		window.add(quit);
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.loadConfiguration("resources/settings/config.xml");
				dispose();
				new RunGame(Display.WindowLocation);
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//config.saveConfiguration("sd", Display.selection);
				dispose();
				new Options();//() //1
				//frame.dispose();
			}
		});
		
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);//1 is abnormal if it wasnt suspose to close an exception 0 is expected to close normal
			}
		});
		
	}
	
	private void renderMenu() throws IllegalStateException{
		//put it later in different thread? than Main Game?
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}

		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
		try {
			//g.drawImage(ImageIO.read(Display.class.getResource("/wallpapers/launcher_menu.jpg")),0,0, 800, 400, null);
			g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/launcher_menu.jpg")),0,0, 800, 400, null);
			
			//System.out.println("X:" + InputHandler.MouseX + " Y: " + InputHandler.MouseY);
			
			if(InputHandler.MouseX > 670 && InputHandler.MouseX < 670+100 &&
					InputHandler.MouseY >= 69 && InputHandler.MouseY < 69 + 20) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", 0, 20));
			g.drawString("Minigame", 670, 90);
			g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/arrow_small.png")),670+100,74, 22, 22, null);
			if(InputHandler.MouseButton == 1) {
				//System.out.println("PLAY");
				config.loadConfiguration("resources/settings/config.xml");
				dispose();
				new RunMiniGame();
			}
			} 
			else
			{
				g.setColor(Color.GRAY);
				g.setFont(new Font("Verdana", 0, 20));
				g.drawString("Minigame", 670, 90);
			}
			
			if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690+80 &&
					InputHandler.MouseY > 130 && InputHandler.MouseY < 130 + 30) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/play_on.png")),690,130, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/arrow_small.png")),690+80,134, 22, 22, null);
				if(InputHandler.MouseButton == 1) {
					//System.out.println("PLAY");
					config.loadConfiguration("resources/settings/config.xml");
					dispose();
					new RunGame(Display.WindowLocation);
				}
			}
			else
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/play_off.png")),690,130, 80, 30, null);
			
			
			if(InputHandler.MouseX > 641 && InputHandler.MouseX < 641+130 &&
					InputHandler.MouseY > 170 && InputHandler.MouseY < 170 + 30) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/options_on.png")),641,170, 130, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/arrow_small.png")),690+80,174, 22, 22, null);
				if(InputHandler.MouseButton == 1) {
					InputHandler.MouseButton=0;
					new Options();
					//dispose();
				}

			}
			else
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/options_off.png")),641,170, 130, 30, null);
			
			
			if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690+80 &&
					InputHandler.MouseY > 210 && InputHandler.MouseY < 210 + 30) {
				//g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/help_on.png")),690,210, 80, 30, null);
				//g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/arrow_small.png")),690+80,214, 22, 22, null);	
				if(InputHandler.MouseButton == 1) {
					//System.exit(0);
				}
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/help_off.png")),690,210, 80, 30, null);
			}
			else
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/help_off.png")),690,210, 80, 30, null);
			
			
			if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690+80 &&
					InputHandler.MouseY > 250 && InputHandler.MouseY < 250 + 30) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/exit_on.png")),690,250, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/arrow_small.png")),690+80,254, 22, 22, null);	
				if(InputHandler.MouseButton == 1) {
					System.exit(0);
				}
			}
			else
				g.drawImage(ImageIO.read(Launcher.class.getResource("/wallpapers/menu/exit_off.png")),690,250, 80, 30, null);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//g.setColor(Color.WHITE);
		//g.setFont(new Font("Verdana", 0, 30));
		//g.drawString("Play", 720, 90);
		g.dispose();
		bs.show();
		
	}
	
}
