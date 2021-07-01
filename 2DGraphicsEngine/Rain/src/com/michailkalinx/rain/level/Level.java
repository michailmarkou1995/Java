package com.michailkalinx.rain.level;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.level.tile.Tile;

//base level will contain sub-classes this is super-class
public abstract class Level {
	
	protected int width, height;
	protected int[] tiles;
	
	public Level(int width, int height) {
		//random generated level
		this.width = width;
		this.height = height;
		tiles = new int[width*height];
		generateLevel();//random
	}
	public Level() {}
	
	public Level(String path) {
		//generate from file Level
		loadLevel(path);
	}
	
	protected void generateLevel() {
		
	}
	
	private void loadLevel(String path) {
		
	}
	
	public void update() {
		//update Level e.g. A.I bots positioning entities getting updated 60 ups lock
		
	}
	
	private void time() {//every sub-class level will have those
		//dynamic e.g. night day cycles
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {//Corner pins
		//ovveride in sub-class here empty
		
		//which tile we gonna collect
	    screen.setOffset(xScroll, yScroll);//actual loc of player
		int x0 = xScroll >> 6;//every 16 pixels == 1 tile divide by 16
		int x1 = (xScroll + screen.width) >> 6;
		int y0 = yScroll >>6;
		int y1 = (yScroll + screen.height) >> 6;
		
		for (int y=y0; y<y1; y++) {
			for (int x=x0; x<x1; x++) {
				getTile(x,y).render(x, y, screen);
			}
		}
	}

	public Tile getTile(int x, int y) {
//		if (tiles[x+y*width] == 0  ) return Tile.grass;
//		//return Tile.grass;
//		return Tile.voidTile;
		//return Tile.voidTile;
		if(x < 0 || y < 0) return Tile.voidTile;
		if(tiles[x + y * width] == 0) return Tile.grass;
		return Tile.voidTile;
	}
	
}
