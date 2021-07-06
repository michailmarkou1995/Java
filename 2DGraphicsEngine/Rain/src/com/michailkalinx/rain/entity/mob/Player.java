package com.michailkalinx.rain.entity.mob;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.input.Keyboard;

public class Player extends Mob{
	
	private Keyboard input;
	
	
	//e.g. spawn default location
	public Player(Keyboard input) {//InputHandler
		this.input =input;
	}
	
	//e.g. specific spawn point of realm after save
	//x from MOB
	public Player(int x, int y, Keyboard input) {
		this.x=x;
		this.y=y;
		this.input = input;
	}
	
	//in multilpayer controls all entities Class x,y on screen location,position!
	//Ovverides of Mob
	public void update() {
//		if(input.up) y--;
//		if(input.down) y++;
//		if(input.left) x--;
//		if(input.right) x++;
		
		//reset to 0 every time that cames back correct method to move Player
		int xa=0, ya=0;//direction of which player to move
		if(input.up) ya--;
		if(input.down) ya++;
		if(input.left) xa--;
		if(input.right) xa++;
		
		if(xa != 0 || ya != 0) move(xa, ya);
	}
	
	//player must not all time in center of screen render e.g. cutscene
	//location of the camera rather location of player
	public void render(Screen screen) {
		
		int xx = x - 16;
		int yy =y - 16;
		
		screen.renderPlayer(xx, yy, spite.player0);
		screen.renderPlayer(xx+16, yy, spite.player1);
		screen.renderPlayer(xx, yy+16, spite.player2);
		screen.renderPlayer(xx+16, yy+16, spite.player3);
	}

}
