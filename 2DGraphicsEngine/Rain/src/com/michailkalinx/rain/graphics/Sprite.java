package com.michailkalinx.rain.graphics;

import com.michailkalinx.rain.level.RandomLevel;

public class Sprite {
	
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(64,0,0, SpriteSheet.tiles);//null
	public static Sprite voidSprite = new Sprite(64, 0x1B87E0);//16

	public Sprite(int size, int x, int y, SpriteSheet sprite) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x*size;
		this.y = y*size;
		this.sheet= sprite;
		load();
	}
	
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
		for (int y=0; y<SIZE; y++) {
			for (int x=0; x<SIZE; x++) {
				pixels[x+y*SIZE] = sheet.pixels[(x + this.x)+(y+this.y)*sheet.SIZE];
				//RandomLevel r = new RandomLevel();
				//r.generateLevel();
				//RandomLevel.r.generateLevel();
			}
		}
	}

}
