package com.michailkalinx.rain.level.tile;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.graphics.Sprite;

//like null tile but and object SO you do not render nothing and get graphic issues
public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		//screen.renderTile(x, y, this);//pixel format not tile format ..position
		screen.renderTile(x<<4, y<<4, this);//<< 6 was
		//screen.renderTile(x<<6, y<<6, sprite);//if in Tile Screen class in renderTile is Sprite instead of Tile
	}
	
}
