package com.michailkalinx.rain.entity.mob;

import com.michailkalinx.rain.entity.Entity;
import com.michailkalinx.rain.graphics.Sprite;

//mobile Entity
//Template cant be instansiated like an new object
public abstract class Mob extends Entity{
	
	protected Sprite spite;
	protected int dir = 0;//north 1 is east 2 is south 3 is west -1 if not initialized in constructor of Player 		spriteP = Sprite.player_forward;

	protected boolean moving = false;
	
	public void move(int xa, int ya) {
		/*
		 * those who want 8 directions here,
			`` dir = xa + ya * 3; `` put this in the move method instead of what he had
			This'll return a number based off xa and ya
			-4 - North West
			-3 - South
			-2 - North East
			-1 - West
			0 - Standing Still
			1 - East
			2 - South West
			3 - South
			4 - South East
		 */
		
		//track locations direction of mobs
		if (xa > 0) dir = 1;//right
		if (xa < 0) dir = 3;//left
		if (ya > 0) dir = 2;//bottom
		if (ya < 0 ) dir = 0;//up compass
		
		//test to pass for increase x,y
		if(!collision()) {
			//3 possibles outcome left, right or not move at all, -1, 0 , 1
			x+=xa;
			y+=ya;
		}
	}
	
	public void update() {
		
	}
	
	private boolean collision() {
		return false;
	}
	
	public void render() {
		
	}
	
}
