package com.mime.minefront;

import java.awt.event.KeyEvent;

import com.mime.minefront.input.Controller;

public class Game {

	public int time;
	public Controller controls;
	
	public Game() {
		controls = new Controller();
	}
	
	public void tick(boolean[] key) {
		time++;
		//get the array boolean[] if its true or false from method calls inside inputHandler
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean right = key[KeyEvent.VK_D];
		boolean left = key[KeyEvent.VK_A];
		boolean turnLeft = key[KeyEvent.VK_LEFT];
		boolean turnRight = key[KeyEvent.VK_RIGHT];

		controls.tick(forward, back, left, right, turnLeft, turnRight);
	}
}
