package com.michailkalinx.rain.graphics;

public class Sprite {
	
	public final int SIZE;//monster larger than tiles or combine
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16,0,0, SpriteSheet.tiles);//null
	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);//16

	//from file Sprite Call Constructor
	public Sprite(int size, int x, int y, SpriteSheet sprite) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x*size;//location of spritesheet sprites
		this.y = y*size;
		this.sheet= sprite;
		load();
	}
	
	//voidSprite constructor
	public Sprite(int size, int color) {
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		setColour(color);
	}
	
	private void setColour(int color) {
		for (int i = 0; i < SIZE*SIZE; i++) {
			pixels[i] = color;
		}
	}

	private void load() {
		for (int y=0; y<SIZE; y++) {//without int y just y=0 and x=0 see res
			for (int x=0; x<SIZE; x++) {
				pixels[x+y*SIZE] = sheet.pixels[(x + this.x)+(y+this.y)*sheet.SIZE];//this from other class taken value constructor
				//RandomLevel r = new RandomLevel();
				//r.generateLevel();
				//RandomLevel.r.generateLevel();
			}
		}
	}

}
