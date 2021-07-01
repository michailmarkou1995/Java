package com.michailkalinx.rain.level.tile;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.graphics.Sprite;

public abstract class Tile {//template super class
	public int x,y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);//constant grass not changes to solid eg.
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	//each Tile gona have a sprite even null
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		
	}
	
	public boolean solid() {
		return false;//if not override in subclass then return default false
	}
}
