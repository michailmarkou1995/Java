package com.michailkalinx.rain.level.tile;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		//screen.renderTile(x, y, this);//pixel format not tile format ..position
		//screen.renderTile(x, y, this);//render pixels convert back not tiles tiles is abstract
		
		//TILES TO PIXELS below
		screen.renderTile(x<<4, y<<4, this); //multipley by 16 pixel precision instead of TILES
		//empty render from Tile here is ovveriden
		//screen.renderTile(x<<6, y<<6, sprite); //if in Tile Screen class in renderTile is Sprite instead of Tile
	}
	
}
