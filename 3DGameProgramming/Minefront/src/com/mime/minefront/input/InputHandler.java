package com.mime.minefront.input;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class InputHandler implements KeyListener, FocusListener, MouseListener
, MouseMotionListener, ComponentListener {

	//public boolean[] key = new boolean[65536];
	public boolean[] key = new boolean[300];
	public static int MouseX, MouseY, MouseButton, MouseDX, MouseDY, MousePX,
	MousePY;//d for drag P for pressed mouse loc(coordinates)
	public static int MouseXwrap, MouseYwrap;
	public static int WindowX, WindowY;
	Object MouseCapture;
	public static boolean dragged = false;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		MouseDX = e.getX();
		MouseDY = e.getY();
		 dragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();//relative to window frame not whole screen
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//MouseButton = e.getButton();		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		MouseButton = e.getButton();
		MousePX = e.getX();
		MousePY = e.getY();
		System.out.println(e.getClickCount());
		//e.getClickCount();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragged = false;	
		MouseButton = 0;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//system out something?
	}

	@Override
	public void mouseExited(MouseEvent e) {
		MouseX=e.getX();
		MouseY=e.getY();
		MouseCapture = e.getSource();
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	//outside of window frame
	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < key.length; i++) {
			key[i] = false;
		}

//		if (keyCode > 0 && keyCode < key.length) {
//			key[keyCode] = false;
//		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
		}
		//key[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		//if will work for multiple keyPressed instead of for loop
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
//		for (int i = 0; i < key.length; i++) {
//			key[i] = false;
//		}
		
		//key[keyCode] = false;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		WindowX=e.getComponent().getX();
		WindowY=e.getComponent().getY();
		//e.getComponent().getLocation().x // causes heap allocation above preferable
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public String toString() {
//		return "InputHandler [key=" + Arrays.toString(key) + "]";
//	}
	

}
