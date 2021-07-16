package com.mime.minefront.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {

	public static Render floor = loadBitmap("/textures/floor1.png");
	public static Render blocks = loadBitmap("/textures/blocks.png");//floor1

	
	public static Render loadBitmap(String fileName) {
		try {
			BufferedImage image = ImageIO.read(Texture.class.getResource(fileName));
			int width = image.getWidth();//8 //gets Image and puts it in Array of Integers
			int height = image.getHeight();//8
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.PIXELS,0, width);
			return result;
		} catch (Exception e){
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
