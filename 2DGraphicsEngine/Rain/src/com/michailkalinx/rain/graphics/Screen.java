package com.michailkalinx.rain.graphics;

import java.util.Random;

import com.michailkalinx.rain.level.tile.Tile;

public class Screen {

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	//public int[] tiles = new int[64*64];//64 << 6
	public int[] tiles = new int[MAP_SIZE*MAP_SIZE];
	
	public int xOffset, yOffset;
	
	private Random random = new Random();

//	int counter=0;
//	int xtime=100, ytime=100;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; //0-50,399 = 50,400//1 integer for each pixel in our screen created here
		
		for(int i = 0; i < MAP_SIZE*MAP_SIZE; i++) {//64<<6
			tiles[i]=random.nextInt(0xffffff);
			tiles[0]=0;
		}
	}

	//clear after animation old void black pixel
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderOld(int xOffset, int yOffset) {
//		counter++;
//		if(counter % 100 == 0) xtime--;//xtime++;
//		if(counter % 100 == 0) ytime--;//ytime++;
//
//		for (int y = 0; y < height; y++) {
//		if(ytime < 0 || ytime >= height) break;
//			for (int x = 0; x < width; x++) {
//				if(xtime < 0 || xtime >= width) break;
//				pixels[xtime + ytime * width] = 0xFF00FF;
//			}
//		}
		// throw new ArrayIndexOutOfBoundsException();

		for (int y = 0; y < height; y++) {
			int yy=y+yOffset;
			int yp=y+yOffset;//-
			//if (y < 0 || y >= height)break;
			if(yp<0 || yp>= height) continue;
			for (int x = 0; x < width; x++) {
				int xx=x+xOffset;
				int xp=x+xOffset;//-
				//if (x < 0 || x >= width)break;				
				//int tileIndex = ((xx >> 4) & 63)+(((yy>>4)&63) << 6);	//(x/32)+( y/32)	 * 64	 || (x >> 4)+(y>>4)*64;
				//int tileIndex = ((xx >> 4)/*+ xOffset not smooth scrolling map*/& MAP_SIZE_MASK)+(((yy>>4)&MAP_SIZE_MASK) * MAP_SIZE);
				
				//TILESIZE 32 is from this -> (2*6) or x/32 or xx>>6 
				int tileIndex = ((xx >> 6)& MAP_SIZE_MASK)+(((yy>>6)&MAP_SIZE_MASK) * MAP_SIZE);		
				//pixels[x + y * width] = Sprite.grass.pixels[(xx&31) +(yy&31)*Sprite.grass.SIZE];//tiles[tileIndex]; // moves tiles wrapping
				if(xp<0 || xp>= width) continue;
				pixels[xp+ yp * width] = Sprite.grass.pixels[(x&63) +(y&63)*Sprite.grass.SIZE];
			}
		}
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		xp -=xOffset;
		yp -= yOffset;
		for (int y=0; y< tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x=0; x< tile.sprite.SIZE; x++) {
				int xa = x +xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= width) break;//render what on your screen
				pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];//offset changes on the screen but not the tile
				
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset=xOffset;
		this.yOffset=yOffset;
	}

}

/*
 * Here's an explanation I made for the "& 63" thing.

In binary, the number 63 is written as 111111 (If you don't understand binary, Google is very helpful). 
The map size, 64, has a range of 0-63. In theory, this means that this doesn't work with all numbers, 
but only powers of 2 (minus one, because we're in binary), for example; 1, 3, 7, 15, 31, 63, 127, and so on. 
That is because all of these numbers are written with all 1's in binary: 1, 11, 111, 1111, 11111, 111111, etc.

To compare two bits, you can use bitwise operators on them. 
The bitwise AND compares the two bits and returns 1 if both bits are one, or 0 otherwise. 
The symbol Java uses for bitwise AND is '&'. So once the map reaches the 64th tile, which is out of our map range, 
it is "ANDED" to 63 (63 & 64):

0011 1111
0100 0000
---------------
0000 0000

That means when x or y has a value of 64, it is ANDED with 63 and returns 0, resetting the x or y to 0.

And for negative values, which are a bit more complicated in binary, the same thing. Example 63 & -1:

0000 0000 0011 1111
1111 1111 1111 1111
------------------------------
0000 0000 0011 1111

Which gives us 63, because tiles 63 and 0 are right next to each other.

This will keep happening until the actual value of x or y is at the maximum number an integer can be, 2^32 - 1, or 2147483647. 
That means this practically makes the map infinite, unless the player travels 2147483647 tiles in any direction.
 * */
