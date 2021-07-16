package com.mime.minefront.level;

import java.util.Random;

import com.mime.minefront.graphics.Sprite;

public class Level {

	public Block[] blocks;
	public final int width, height;
	
	public Level(int width,int height) {
		this.width = width;
		this.height = height;
		blocks = new Block[width * height];
		Random random = new Random();
		
		//here generate changes before render Happens e.g. change amount of volume
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Block block = null;
				if (random.nextInt(18) == 0) {
					block = new SolidBlock();//true block
				} else {
					block = new Block();//false no block
					if(random.nextInt(15) == 0)//every 5 without it 1 sprite every block("tile")
					block.addSprite(new Sprite(0,0,0));
				}
				blocks[x+y*width] = block;
			}
		}
	}
	
	public Block create(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Block.solidWall; //return till now just true ..nothing!
		}
		return blocks[x+y*width];
	}
	
	public Block createSimple(int x, int y) {
		return blocks[x+y*width];
	}
}
