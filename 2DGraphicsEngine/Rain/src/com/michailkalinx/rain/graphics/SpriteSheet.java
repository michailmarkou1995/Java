package com.michailkalinx.rain.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("resources/textures/spritesheetgeneral3.png", 256);//128
	
	public SpriteSheet(String path, int size) {
		this.path=path;
		SIZE=size;
		pixels = new int[SIZE * SIZE];
		load(this.path);
	}
	
	private void load(String path) {
		try {
			//BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			BufferedImage image = ImageIO.read(new File(path));
			int w=image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
