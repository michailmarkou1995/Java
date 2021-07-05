package com.mime.minefront.graphics;

import com.mime.minefront.Display;

public class Render {

	public final int WIDTH;
	public final int HEIGHT;
	public final int[] PIXELS;
	
	//private Display display;
	
	public Render(int width, int height) {
		super();
		this.WIDTH = width;
		this.HEIGHT = height;
		PIXELS = new int[width*height];
	}
	
	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y<render.HEIGHT; y++) {
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= Display.HEIGHT) continue;//600//HEIGHT
			for (int x = 0; x<render.WIDTH; x++) {
				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= Display.WIDTH) continue;//800// or just WIDTH

				int alpha = render.PIXELS[x+y * render.WIDTH];
				try {
					
					//Screen CLASS random.nextInt() * (random.nextInt(5)/4);
					if(alpha > 0) //render if something but here issue with performance//allows transparency void pixels pixels with NO DATA
				PIXELS[xPix + yPix * WIDTH] = alpha;
				}catch(Exception e) {
					e.printStackTrace();
					System.exit(0);
					//continue;
				}
				//System.out.println("x: " + x  + " y: " + y);// makes pixel play slow poke LAG
			}
		}
	}
}
