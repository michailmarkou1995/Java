package com.michailkalinx.rain.level;

import com.michailkalinx.rain.graphics.Screen;
import com.michailkalinx.rain.level.tile.Tile;

//base level will contain sub-classes this is super-class
public abstract class Level {
	
	//for random generation below because you get those from file input image
	protected int width, height;//if private cant run RandomLevel generateLevel ovveride with vars of height, width
	//Tile ID like data which tile goes where ..index of specific tile
	protected int[] tiles;
	
	public Level(int width, int height) {
		//random generated level
		this.width = width;
		this.height = height;
		tiles = new int[width*height];
		generateLevel();//random call RandomLevel
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
	
	//Rendering CORNER PINS below method x0 top left x1 top Right(width) y0 top up y1(height) top down)
	//like a line scan whole screen LEFT SIDE OF SCREEN is x0
	public void render(int xScroll, int yScroll, Screen screen) {//Corner pins
		//ovveride in sub-class here empty
		
		//which tile we gonna collect the x0,x1,y0,y1 below
	    screen.setOffset(xScroll, yScroll);//actual location of player OFFSET see Screen renderTile method with x,yOffsets
	    //tile 16 wide, pixel to tile precision?(YES) bitwise is faster
		int x0 = xScroll >> 4;//every 16 pixels == 1 tile divide by 16 -> log( 16 )   ÷   log( 2 ) which 2^x is == 16?
		int x1 = (xScroll + screen.width + 16) >> 4;//+16//procedural rendering fix AKA black bars
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height  + 16) >> 4;//was >> 6 //+16//procedural rendering fix AKA black bars
		
		//retrieve tile to render after has been collected from above steps
		//where to render every pixel from top to bottom left to right corner pin
		//converted from above to TILE PRECISION so programs which TILE is where and where to render 16 px == 1 Tile FROM int x0 = xScroll >> 4;
		for (int y=y0; y<y1; y++) {
			for (int x=x0; x<x1; x++) {
				//returns Tile object from method below thats why no static render method
				//Tile Class has no render instruction or any default
				//render is been called from the sub-Class Tile of extended static Tiles if ts grass or Void this render is been called
				getTile(x,y).render(x, y, screen);//render from Tile class because public Tile getTile return type
			}
		}
	}

	//retrieve tile to render after has been collected from above steps
	public Tile getTile(int x, int y) {
//		if (tiles[x+y*width] == 0  ) return Tile.grass;
//		//return Tile.grass;
//		return Tile.voidTile;
		//return Tile.voidTile;
		//no map bound error left or down right error
		if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;//Tile.grass make it like ARENA 
		if(tiles[x + y * width] == 0) return Tile.grass;//if RANDOM returns 0 then grass FROM RandomLevel Class
		return Tile.voidTile;
	}
	
}
