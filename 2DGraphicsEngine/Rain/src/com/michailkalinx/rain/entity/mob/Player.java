package com.michailkalinx.rain.entity.mob;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.graphics.Sprite;
import com.michailkalinx.rain.input.Keyboard;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite spriteP;
	private int anim;
	private boolean walking = false;
	
	//e.g. spawn default location
	public Player(Keyboard input) {//InputHandler
		this.input =input;
		spriteP = Sprite.player_forward;
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
		//60 ups anim is increasing not "render" max for smooth anim
		if(anim < 7500) anim++; else anim = 0;
		if(input.up) ya--;
		if(input.down) ya++;
		if(input.left) xa--;
		if(input.right) xa++;
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		}
		else
		{
			walking = false;
		}
		
	}
	
	//player must not all time in center of screen render e.g. cutscene
	//location of the camera rather location of player
	public void render(Screen screen) {
		
		int flip = 0;
//		int xx = x - 16;
//		int yy =y - 16;
		
		//quad render method of player
//		screen.renderPlayer(xx, yy, spite.player0);
//		screen.renderPlayer(xx+16, yy, spite.player1);
//		screen.renderPlayer(xx, yy+16, spite.player2);
//		screen.renderPlayer(xx+16, yy+16, spite.player3);
		
		//screen.renderPlayer(xx, yy, spite.player);
		
		if(dir == 0) {
			spriteP = Sprite.player_forward;
			if (walking) {
				//every multilpe of 20 and greater than 10 ... half the time 0-10 10-20
				if(anim % 20 > 10 ) {
					spriteP = Sprite.player_forward1;
				} else {
					spriteP = Sprite.player_forward2;
				}
			}
		}
		if(dir == 1 || dir == 3) {
			spriteP = Sprite.player_right;
			if (walking) {
				if(anim % 20 > 10 ) {
					spriteP = Sprite.player_right1;
				} else {
					spriteP = Sprite.player_right2;
				}
			}
		}
		if(dir == 2) {
			spriteP = Sprite.player_back;
			if (walking) {
				if(anim % 20 > 10 ) {
					spriteP = Sprite.player_back1;
				} else {
					spriteP = Sprite.player_back2;
				}
			}
		}
		if(dir == 3) {
			spriteP = Sprite.player_left;
			if (walking) {
				if(anim % 20 > 10 ) {
					spriteP = Sprite.player_left1;
				} else {
					spriteP = Sprite.player_left2;
				}
			}
		}
//		if (dir == 3) {
//			if (walking) {
//				if(anim % 20 > 10 ) {
//					spriteP = Sprite.player_right1;
//				} else {
//					spriteP = Sprite.player_right2;
//				}
//			}
//			flip = 1;
//		}
		//screen.renderPlayer(x - 16, y - 16, spriteP, flip);
		screen.renderPlayer(x - 16, y - 16, spriteP);
	}

}
