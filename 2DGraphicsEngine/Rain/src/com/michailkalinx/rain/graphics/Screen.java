package com.michailkalinx.rain.graphics;

import java.util.Random;

import com.michailkalinx.rain.entity.mob.Player;
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
		
		//down for loop below that with moving sprite repeated
//		for (int y = 0; y < height; y++) {
//			int yy=y+yOffset;
//			int yp=y+yOffset;//-
//			//if (yy < 0 || yy >= height)continue;
//			if(yp<0 || yp>= height) yp=0;
//			for (int x = 0; x < width; x++) {
//				int xx=x+xOffset;
//				int xp=x+xOffset;//-
//		
//				//group below
////				{
////								//pixels to tiles
////								//colourful
////								//int tileIndexMove = ((xx/8)&15)+(( yy/8)&15)	 * 8	;//ana 16 same loop tiles
////				
////								//tile width 160 x here
////								int tileIndexMove = ((xx/160)&15)+(( yy/16)&15)	 * 16	;//ana 16 same loop tiles
////								
////								int tileIndex = (x/16)+( y/16)	 * 16	;
////								pixels[x+ y * width] = tiles[tileIndexMove];//mporis kai san var kai oxi edw parastasi logo LOOP continual
////				}
//				
//				//pixels to tiles
//				//spritesheet sprite
//				//pixels[x+ y * width] = Sprite.grass.pixels[(xx&15) +(yy&15)*Sprite.grass.SIZE];
//				//pixels[x+ y * width] = Sprite.grass.pixels[(xx&63) +(yy&63)*Sprite.grass.SIZE];
//			}
//		}
		
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
				
				/*not smooth scrolling map*/
				//int tileIndex = ((xx >> 4)+ xOffset & MAP_SIZE_MASK)+(((yy>>4)&MAP_SIZE_MASK) * MAP_SIZE);
				
				//TILESIZE 32 is from this -> (2*6) or x/32 or xx>>6 
				int tileIndex = ((xx >> 6)& MAP_SIZE_MASK)+(((yy>>6)&MAP_SIZE_MASK) * MAP_SIZE);		
				//pixels[x + y * width] = Sprite.grass.pixels[(xx&31) +(yy&31)*Sprite.grass.SIZE];//tiles[tileIndex]; // moves tiles wrapping
				if(xp<0 || xp>= width) continue;//xp=0;&xp?
				
				//pixels to tiles
				//pixels[xp+ yp * width] = Sprite.grass.pixels[(x&63) +(y&63)*Sprite.grass.SIZE];
				//pixels[xp+ yp * width] = Sprite.grass.pixels[((x/16)&15) +(y&15)*Sprite.grass.SIZE];
				
				//why no division here for tiles(below)? Answer: because you dont create pixels(form to tile) but you read
				//already predifined input image array set as 16 x 16 of 256 res and you just loop it... loop the loaded sprite otherwise crash out fo bounds
				//tiles to pixels? 
				//FIRST yp*width then add with xp xp is x yp is y coordinate width is just filling up proper tile to pixel
				//paei grammika apo top left to right then down wrapping second line scanning
				pixels[xp+ yp * width] = Sprite.grass.pixels[(x&15) +(y&15)*Sprite.grass.SIZE];
				/*(x/16 is >> 4) log2(16) == 4 or 2^x == 16? is 2^4==16*/
				/*
					log_2 (x) = log_b (x) / log_b (2)
					log( 16 )   ÷   log( 2 ) <-windows calc
				*/
			}
		}
	}
	
	//offset based position eg move 2 px right the player then all tiles to left 2 px
	//based on tile here.. you could do also sprite tile is more abtract and easier?
	//render mobs and light will have diff methods here only Tiles
	//relative positions take one place as reference(eg sprite) and moves all others based on this like Parent Child relationship
	//absolute only one sprite moves it accurate in world position
	//absolute is relative to entire world(world position?)
	//relative position is relative to given object
	public void renderTile(int xp, int yp, Tile tile) {//Sprite sprite instead of Tile tile
		xp -=xOffset;//dont move the map the wrong way?(like Player walks opposite direction) move the correct way
		yp -= yOffset;
		for (int y=0; y< tile.sprite.SIZE; y++) {//sprite.SIZE or tile.SIZE if Sprite sprite insteadl of Tile tile
			//ya is absolute
			int ya = y + yp;//<- y absolute and yp offset based on absolute == ya ABSOLUTE
			for (int x=0; x< tile.sprite.SIZE; x++) {
				//xa is absolute
				int xa = x +xp;
				//render what we see in below line eg our map is almost infinite
				//render queue black goes behind in canvas and appear in front
				//procedural rendering fix below from < 0 to -tile... and if xa < 0 to eliminate black bars
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;// ya>=height instead of width
				if (xa < 0) xa=0;//eg if its -10 make it 0
				//if (xa < 0 || xa >= width || ya < 0 || ya >= width) break;//render what on your screen
				//pixels[xa+ya*width] == where sprite on screen gets rendered e.g. which pixels on screen
				//tile.sprite.pixels[x+y*tile.sprite.SIZE]; == which pixels of the sprite gets rendered 
				//no offsets because the location on the screen does e.g. pixels[xa+ya*width] and not the image of the tile
				pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];//offset changes on the screen but not the tile
				
			}
		}
	}
	
	public void renderPlayer(int xp, int yp, Sprite player) {
		xp -=xOffset;
		yp -= yOffset;
		for (int y=0; y< 16; y++) {
			int ya = y + yp;
			for (int x=0; x< 16; x++) {
				int xa = x +xp;

				if (xa < -16 || xa >= width || ya < 0 || ya >= height) break;// ya>=height instead of width
				if (xa < 0) xa=0;
				int col = player.pixels[x+y*16];
				if (col != 0xff4CFF00) pixels[xa+ya*width] = col;
				
			}
		}
	}
	
	//takes account player movement? in these 2 vars in above(renderTile method)
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
