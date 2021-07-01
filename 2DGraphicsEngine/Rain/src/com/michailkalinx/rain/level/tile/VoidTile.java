package com.michailkalinx.rain.level.tile;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		//screen.renderTile(x, y, this);//pixel format not tile format ..position
		screen.renderTile(x<<6, y<<6, this);
	}
	
}
