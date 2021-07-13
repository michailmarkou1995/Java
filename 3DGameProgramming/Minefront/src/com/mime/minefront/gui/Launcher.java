package com.mime.minefront.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.mime.minefront.Configuration;
import com.mime.minefront.Display;
import com.mime.minefront.RunGame;

public class Launcher extends JFrame{

	private static final long serialVersionUID = 1L;
	
	protected JPanel window = new JPanel();
	private JButton play, options, help, quit;
	private Rectangle rplay, roptions, rhelp, rquit;//if not = something e.g. 0 is null pointer
	Configuration config = new Configuration();
	
	protected int width = 240, height = 320, buttonWidth = 80, buttonHeight = 40;

	public Launcher(int id) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new JFrame is not neede because we use directly JFrame extends
		setTitle("Minefront Launcher");
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		
		if (id == 0)
		drawButtons();

	}
	
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
				new Options(1);//()
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
	
}
