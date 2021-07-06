package com.mime.minefront.graphics;

import com.mime.minefront.Game;

public class Render3D extends Render{
	
	public double[] zBuffer;
	private double renderDistance = 5000;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width*height];
	}
	
//	public void floor() {
//		
//		for (int y=0; y<HEIGHT; y++) {
//			double yDepthCeiling = y - HEIGHT /2.4 ;
//			double z = 100.0 / yDepthCeiling;
//				
//				for (int x=0; x<WIDTH; x++) {
//					double xDepth = x - WIDTH /2;
//					xDepth *=z;
//					int xx = (int) (xDepth) & 5;//>>2;//<< 2;//15
//					PIXELS[x+y*WIDTH] = xx * 128;		
//				}	
//			}
//	}
	
//	double time = 0;
//	public void floor() {
//		
//		for (int y=0; y<HEIGHT; y++) {
//			double yDepthCeiling = (y - HEIGHT /2.0) / HEIGHT ;
//			
//			if (yDepthCeiling < 0) {
//				yDepthCeiling = -yDepthCeiling;
//			}
//			
//			double z = 8 / yDepthCeiling;
//				
//			time += 0.0005;
//			
//				for (int x=0; x<WIDTH; x++) {
//					double xDepth = (x - WIDTH /2.0) / HEIGHT;
//					xDepth *=z;
//					int xx = (int) (xDepth) & 15;//-+* time front diagonal move both xx yy etc
//					int yy = (int) (z) & 15;//+time
//					PIXELS[x+y*WIDTH] = (xx * 16) | (yy * 16) << 8;		
//					//System.out.println(yy);
//				}	
//			}
//	}
	
//	public void floor(Game game) {
//		
//		double forward = game.time/10.0;
//		double right = game.time/10.0;//-10 - + - = +
//		double floorPosition = 8;
//		double ceilPosition = 8;//render distance will make black if high now its shows infinitely
//		double rotation = game.time/100.0;//0; //game.time/100.0;//animated because time changes
//		double cosine = Math.cos(rotation);
//		double sine = Math.sin(rotation);
//		
//		for (int y=0; y<HEIGHT; y++) {
//			double yDepthCeiling = (y - HEIGHT /2.0) / HEIGHT ;
//			
//			double z = floorPosition / yDepthCeiling;
//			
//			if (yDepthCeiling < 0) {
//				z = ceilPosition /-yDepthCeiling;//z = -yDepthCeiling wipes ceiling
//			}
//			
//				for (int x=0; x<WIDTH; x++) {
//					double xDepth = (x - WIDTH /2.0) / HEIGHT;
//					xDepth *=z;
//					double xx =  xDepth * cosine + z * sine; //+ right;
//					double yy = z * cosine - xDepth * sine; //+ forward ;
//					int xPix = (int) (xx);
//					int yPix = (int) (yy);
//					PIXELS[x+y*WIDTH] = ((xPix & 15)* 16) | ((yPix & 15)* 16) << 8;	
//					//System.out.println(game.time);
//				}	
//			}
//	}
	
public void floor(Game game) {
		
		double forward = game.controls.z;
		double right = game.controls.x;
		double floorPosition = 8;
		double ceilPosition = 8;
		double rotation = game.controls.rotation;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);
		
		for (int y=0; y<HEIGHT; y++) {
			double yDepthCeiling = (y - HEIGHT /2.0) / HEIGHT ;
			
			double z = floorPosition / yDepthCeiling;
			
			if (yDepthCeiling < 0) {
				z = ceilPosition /-yDepthCeiling;//z = -yDepthCeiling wipes ceiling
			}
			
				for (int x=0; x<WIDTH; x++) {
					double xDepth = (x - WIDTH /2.0) / HEIGHT;
					xDepth *=z;
					double xx =  xDepth * cosine + z * sine; 
					double yy = z * cosine - xDepth * sine; 
					int xPix = (int) (xx + right);
					int yPix = (int) (yy + forward);
					zBuffer[x+y*WIDTH] = z;
					PIXELS[x+y*WIDTH] = ((xPix & 15)<<4) | ((yPix & 15)<<4) << 8;	
					
					if(z > 400) {//x,y <,> etc 50 100
						PIXELS[x+y*WIDTH] = 0;
					}
				}	
			}
	}

//pixels further gets less brightness
	public void renderDistanceLimiter() {
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			int color = PIXELS[i];
			int brightness = (int) (renderDistance / (zBuffer[i])); //System.out.println(brightness);
			
			if (brightness < 0) {//0-255 brightness e.g. photoshop or .net paint
				brightness = 0;
			}
			if (brightness > 255) {
				brightness = 255;
			}
			
			int r = (color >> 16) & 0xff;// >> is "/" , << is "*"
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;
			
			r = r*brightness >>> 8; // / 255;
			g = g*brightness >>> 8; // / 255;
			b = b*brightness >>> 8; // / 255;
			
			//modified pixels basically brightness adjusted below put them
			PIXELS[i] = r << 16 |  g << 8 | b;// << is * 2^x ==255? e.g. is 8.. 2^x==? 255!
		}
}
	
//cos sin wave make a rotation O istead of C or half O they are opossite null
}
