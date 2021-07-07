package com.michailkalinx.rain.graphics;

public class Sprite {
	
	public final int SIZE;//monster larger than tiles or combine
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16,0,0, SpriteSheet.tiles);//null
	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);//16
	
	//4 corners load method
	public static Sprite player0 = new Sprite(16, 0, 8, SpriteSheet.tiles);
	public static Sprite player1 = new Sprite(16, 1, 8, SpriteSheet.tiles);
	public static Sprite player2 = new Sprite(16, 0, 9, SpriteSheet.tiles);
	public static Sprite player3 = new Sprite(16, 1, 9, SpriteSheet.tiles);

	//1 load to go of 4 quads
	public static Sprite player_forward = new Sprite(32, 0, 4, SpriteSheet.tiles );//half it
	public static Sprite player_back = new Sprite(32, 0, 5, SpriteSheet.tiles );
	public static Sprite player_left = new Sprite(32, 0, 6, SpriteSheet.tiles );
	public static Sprite player_right = new Sprite(32, 0, 7, SpriteSheet.tiles );

	public static Sprite player_forward1 = new Sprite(32, 1, 4, SpriteSheet.tiles);
	public static Sprite player_forward2= new Sprite(32, 2, 4, SpriteSheet.tiles);
	public static Sprite player_back1 = new Sprite(32, 1, 5, SpriteSheet.tiles);
	public static Sprite player_back2= new Sprite(32, 2, 5, SpriteSheet.tiles);
	public static Sprite player_right1 = new Sprite(32, 1, 7, SpriteSheet.tiles);
	public static Sprite player_right2= new Sprite(32, 2, 7, SpriteSheet.tiles);
	public static Sprite player_left1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
	public static Sprite player_left2= new Sprite(32, 2, 6, SpriteSheet.tiles);

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
