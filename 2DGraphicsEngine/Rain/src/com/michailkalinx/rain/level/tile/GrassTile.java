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
		screen.renderTile(x<<6, y<<6, this); //multipley by 16 pixel precision
	}
	
}
