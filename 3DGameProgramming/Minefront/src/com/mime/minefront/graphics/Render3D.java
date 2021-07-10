package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;
import com.mime.minefront.input.Controller;

public class Render3D extends Render{
	
	public double[] zBuffer;
	private double renderDistance = 5000;
	private double forwardGlobal;

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
	int change=0;
	for(int i =0; i < 1000; i++) {
		change+=i;
	}
		
	//double forward = game.time % 100 / 20.0;
		double forward = game.controls.z; // game.time % 100 / 20.0; //pop back and forth like movie clip
		forwardGlobal = forward;
		double right = game.controls.x;
		double floorPosition = 8;
		double ceilPosition = 16;//put biger than RenderDistanceLimiter e.g. 800 Ceil disappear
		//double ceilPosition = Math.sin(game.time / 10) + 10;
		double rotation = game.controls.rotation;
		double rotationUp = game.controls.rotationUp;
		double cosine1 = Math.cos(rotationUp);
		double sine1 = Math.sin(rotationUp);
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);
		//double up = Math.sin(game.time/10.0)* 2.0;//with /10 is lagging 10.0 must
		double up = game.controls.y;
		double walking = Math.sin(game.time / 6.0) * 0.4; //moving "camera" just floor and ceil
		
		if (Controller.jumpedStop) {
			 up = 0;
		}
		if (Controller.crouchWalk) {
			walking = Math.sin(game.time / 6.0) * 0.2;
		}
		if (Controller.runWalk) {
			walking = Math.sin(game.time / 6.0) * 0.7;
		}
		
		for (int y=0; y<HEIGHT; y++) {
			//double yDepthCeilingMoveYUpCamera = (y - WIDTH /1.0) / HEIGHT ;
			//double yDepthCeilingMoveYDownCamera = (y + HEIGHT /2.0) / HEIGHT ;
			//double yDepthCeiling = (y - HEIGHT /2.0) / HEIGHT ;
			//double yDepthCeiling;

			double yDepthCeiling = (y - HEIGHT /2.0 * rotationUp) / HEIGHT ;
			//System.out.println(rotationUp);
			
//			if(Controller.turnUpM)
//				yDepthCeiling = (y - HEIGHT /2.0 * rotationUp) / HEIGHT ;
//			if(Controller.turnDownM)
//				yDepthCeiling = (y - HEIGHT /2.0 + rotationUp) / HEIGHT ;
			
			double z = (floorPosition + up) / yDepthCeiling;//+wallking
			if (Controller.walk) {
				 z = (floorPosition + up + walking) / yDepthCeiling;//+wallking

			}
			
			if (yDepthCeiling < 0) {
				z = (ceilPosition - up) /-yDepthCeiling;//z = -yDepthCeiling wipes ceiling
				if (Controller.walk) {
					 z = (ceilPosition - up - walking) / -yDepthCeiling;//+wallking
				}
			}
			//System.out.println(up);
				for (int x=0; x<WIDTH; x++) {
					double xDepth = (x - WIDTH /2.0) / HEIGHT;
					xDepth *=z;
					double xx =  (xDepth * cosine + z * sine) * 1; //change
					double yy = (z * cosine - xDepth * sine) * 1; 
					int xPix = (int) (xx + right);
					int yPix = (int) (yy + forward);
					zBuffer[x+y*WIDTH] = z;
					//PIXELS[x+y*WIDTH] = ((xPix & 15)<<4) | ((yPix & 15)<<4) << 8;	
					PIXELS[x+y*WIDTH] = Texture.floor.PIXELS[(xPix & 7) + (yPix & 7) * 8];	
					
					//limit Render Distance and the renderDistanceLimiter() is just smoothing the brigthness
					if(z > 400) {//x,y <,> etc 50 100
						PIXELS[x+y*WIDTH] = 0;
					}
				}	
			}
		//wall();
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
	
	public void wall() {
		Random random = new Random(100);//if in beg of file its go crazy

		for (int i = 0; i < 10000; i++) {//250000 more solid bigger the pixels
//The 2D objects   are called "billboards" ^^ Sprites are REALLY static, like the FPS counter
		double xx = random.nextDouble(); //-1; // +1;
		double yy = random.nextDouble(); //-1; // +1;
		double zz = 1.5 - forwardGlobal / 16;
		
		int xPixel = (int) (xx / zz * HEIGHT /2 + WIDTH /2);
		int yPixel = (int) (yy / zz * HEIGHT /2 + HEIGHT /2);
		if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
			PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
		}
		}
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble(); 
			double yy = random.nextDouble() - 1; 
			
			double zz = 1.5 - forwardGlobal / 16;
			
			int xPixel = (int) (xx / zz * HEIGHT /2 + WIDTH /2);
			int yPixel = (int) (yy / zz * HEIGHT /2 + HEIGHT /2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
				PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
			}
			}
		for (int i = 0; i < 10000; i++) {//250000 more solid bigger the pixels
		double xx = random.nextDouble(); //-1; // +1;
		double yy = random.nextDouble(); //-1; // +1;
		double zz = 1.5 - forwardGlobal / 16;
		
		int xPixel = (int) (xx / zz * HEIGHT /2 + WIDTH /2);
		int yPixel = (int) (yy / zz * HEIGHT /2 + HEIGHT /2);
		if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
			PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
		}
		}
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble() - 1; 
			double yy = random.nextDouble() - 1; 
			
			double zz = 1.5 - forwardGlobal / 16;
			
			int xPixel = (int) (xx / zz * HEIGHT /2 + WIDTH /2);
			int yPixel = (int) (yy / zz * HEIGHT /2 + HEIGHT /2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
				PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
			}
			}
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble() - 1; 
			double yy = random.nextDouble(); 
			
			double zz = 1.5 - forwardGlobal / 16;
			
			int xPixel = (int) (xx / zz * HEIGHT /2 + WIDTH /2);
			int yPixel = (int) (yy / zz * HEIGHT /2 + HEIGHT /2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
				PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
			}
			}
		for (int i = 0; i < 10000; i++) {

			double xx = random.nextDouble(); 
			double yy = random.nextDouble(); 
			
			double zz = 2 - forwardGlobal / 16;
			
			int xPixel = (int) (xx / zz * HEIGHT /2 + WIDTH /2);
			int yPixel = (int) (yy / zz * HEIGHT /2 + HEIGHT /2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
				PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
			}
			}

	}
	
//cos sin wave make a rotation O istead of C or half O they are opossite null
}
