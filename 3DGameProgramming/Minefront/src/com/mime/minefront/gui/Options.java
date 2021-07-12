package com.mime.minefront.gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.mime.minefront.Display;

public class Options extends Launcher{


	private static final long serialVersionUID = 1L;
	private int width = 550, height = 450;
	private JButton OK;
	private Rectangle rOK, rresolution;
	private Choice resolution = new Choice();

	public Options() {
		super(1);
		setTitle("Options");
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		
		drawButtons();
	}
	
	private void drawButtons() {
		OK = new JButton("OK");
		rOK = new Rectangle((width-100), height-70, buttonWidth, buttonHeight - 10);
		OK.setBounds(rOK);
		window.add(OK);
		
		rresolution = new Rectangle(50,80,80,25);
		resolution.setBounds(rresolution);
		resolution.add("640, 480");
		resolution.add("800, 600");
		resolution.add("1024, 768");
		resolution.select(1);
		window.add(resolution);
		
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.selection = resolution.getSelectedIndex();//which is 1 resolution.add("800, 600");
				//System.out.println(Display.selection);
				dispose();
				new Launcher(0);
			}
		});
	}

}
