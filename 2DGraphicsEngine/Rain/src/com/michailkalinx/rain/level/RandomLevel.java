package com.michailkalinx.rain.level;

import java.util.Random;

public class RandomLevel extends Level{

	private static final Random random = new Random();
	
	public RandomLevel(int width, int height) {
		super(width, height);
		}
	
//	public static RandomLevel r=new RandomLevel();
//	public RandomLevel() {}
	
	protected void generateLevel() {
		//for(int i=0; i<tiles.length; i++) {} //does not control specific tiles but gives same res as below
		for (int y=0; y < height; y++) {
			for(int x=0; x<width; x++) {
				tiles[x+y*width] = random.nextInt(4);
				//e.g. tiles[10+8*width] = 3; <--water tile
			}
		}
	}
	

}
