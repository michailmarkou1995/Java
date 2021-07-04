package com.michailkalinx.rain.level;

import java.util.Random;

public class RandomLevel extends Level{
	//if not default constuctor then error in class(in super class i mean if not def constructor)
	//then must implement here a constuctor

	private static final Random random = new Random();
	
	public RandomLevel(int width, int height) {
		//oti balis width height ta stelni sto Level constructor kai ekteli ekino
		super(width, height);//runs constructor of Level public Level(int width, int height) 
		}
	
//	public static RandomLevel r=new RandomLevel();
//	public RandomLevel() {}
	
	protected void generateLevel() {
		//height, width, tiles of LEVEL CLASS
		//for(int i=0; i<tiles.length; i++) {} //does not control specific tiles but gives same res as below
		for (int y=0; y < height; y++) {
			for(int x=0; x<width; x++) {
				tiles[x+y*width] = random.nextInt(4);//if blank all space of numbers.. random tile ID
				//e.g. tiles[10+8*width] = 3; <--water tile se coordinate 10x 8y
			}
		}
	}
	

}
