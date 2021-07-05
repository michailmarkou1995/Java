package com.mime.minefront.graphics;

public class Render3D extends Render{

	public Render3D(int width, int height) {
		super(width, height);
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
	
	double time = 0;
	public void floor() {
		
		for (int y=0; y<HEIGHT; y++) {
			double yDepthCeiling = (y - HEIGHT /2.0) / HEIGHT ;
			
			if (yDepthCeiling < 0) {
				yDepthCeiling = -yDepthCeiling;
			}
			
			double z = 8 / yDepthCeiling;
				
			time += 0.00005;
			
				for (int x=0; x<WIDTH; x++) {
					double xDepth = (x - WIDTH /2.0) / HEIGHT;
					xDepth *=z;
					double xx =  (xDepth);
					double yy = (z) + time ;
					int xPix = (int) (xx);
					int yPix = (int) (yy);
					PIXELS[x+y*WIDTH] = ((xPix & 15)* 16) | ((yPix & 15)* 16) << 8;		
				}	
			}
	}
	

}
