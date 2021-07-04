package com.michailkalinx.rain.entity.mob;

public class Player extends Mob{
	
	//e.g. spawn default location
	public Player() {
		
	}
	
	//e.g. specific spawn point of realm after save
	//x from MOB
	public Player(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	//Ovverides of Mob
	public void update() {
		
	}
	
	public void render() {
		
	}

}
