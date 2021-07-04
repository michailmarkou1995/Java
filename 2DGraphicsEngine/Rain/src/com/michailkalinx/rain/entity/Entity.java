package com.michailkalinx.rain.entity;

import java.util.Random;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.level.Level;

//framework entity needs to be render without sprite
//entities can move
//entitie any object on the screen e.g. mob(creature, player) except the map(tiles)
public abstract class Entity {
	
	public int x, y;//if not has a sprite is not that nessecary x and y here
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();//AI in random
	
	//60 ups
	private void update() {
		
	}
	
	//render fast as it can
	public void render(Screen screen) {
		
	}
	
	public void remove() {
		//Remove from level
		removed = true;
	}
	
	public boolean isRemove() {
		return removed;
	}

}
