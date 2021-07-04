package com.michailkalinx.rain.entity.mob;

import com.michailkalinx.rain.entity.Entity;
import com.michailkalinx.rain.graphics.Sprite;

//mobile Entity
//Template cant be instansiated like an new object
public abstract class Mob extends Entity{
	
	protected Sprite spite;
	protected int dir = 0;//north 1 is east 2 is south 3 is west
	protected boolean moving = false;
	
	public void move() {
		
	}
	
	public void update() {
		
	}
	
	private boolean collision() {
		return false;
	}
	
	public void render() {
		
	}
	
}
