package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;

public class Screen extends Render{
	
	private Render test;
	private Render3D render3d;

	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render3d = new Render3D(width, height);
		//test = new Render(width,height);//256, 256
		test = new Render(256,256);//256, 256
		//for (int i = 0; i < (width*height); i++) {//256,256
		for (int i = 0; i < (256*256); i++) {//256,256
			test.PIXELS[i] = random.nextInt() * (random.nextInt(5)/4);//transparency like
		}
	}
	
//	public void render(Game game) {
//		
//		for (int i = 0; i < WIDTH*HEIGHT; i++) {
//			PIXELS[i]=0;
//		}
//		
////		for (int i = 0; i < 200; i++) {//1000 or 500 may cause LAG implement it in SIN COS below i * number
////		int anim = (int) (Math.sin((System.currentTimeMillis() + i *5 ) %3000.0/3000*Math.PI*2)*200);
////		int anim2 = (int) (Math.cos((System.currentTimeMillis() + i *5/*5x200=1000 for loop*/) %3000.0/3000*Math.PI*2)*200);
////
////		draw(test, (WIDTH-256)/2 + anim, (HEIGHT-256) /2 + anim2);
////		}
//		
//		for (int i = 0; i < 50; i++) {
//		//int anim = (int) (Math.sin(System.currentTimeMillis() + i %3000.0/3000*Math.PI*2)*200);//spinning box
//		//int anim = (int) (Math.sin((System.currentTimeMillis() + i * 8) %3000.0/3000*Math.PI*2)*200);
//		//int anim2 = (int) (Math.cos((System.currentTimeMillis() + i * 8) %3000.0/3000*Math.PI*2)*200);
//
//		int anim0 = (int) (Math.sin((System.currentTimeMillis() + i * 8) %3000.0/3000*Math.PI*2)*200);
//		int anim = (int) (Math.sin((game.time + i * 2) % 1000.0/100) * 100);//* i
//		int anim2 = (int) (Math.cos((game.time + i * 2) % 1000.0/100) * 100);//* i
//
//		
//		 draw(test, (WIDTH-256)/2 + anim0, (HEIGHT-256) /2 +anim2);//draws 256x256 x 200 pixels
//
//		//draw(test, (WIDTH-256)/2 + anim, (HEIGHT-256) /2 + anim2);//draws 256x256 x 200 pixels
//		//draw(test, (WIDTH-256)/2, (HEIGHT-256) /2);
//		//draw(test,0,0);//live preview debug change 250 eg
//		}
//	}
	
public void render(Game game) {
		
		for (int i = 0; i < WIDTH*HEIGHT; i++) {
			PIXELS[i]=0;
		}

		render3d.floor(game);
		//render3d.wallCrap(); //renderDistanceLimiter Filter does apply if rendered here
//		render3d.renderWall(0, 0.5, 1.5, 1.5, 0);//back (0, 0.5, 1.5, 1.5, 0); 1, 0.5, 0.5, 0.5, 0
//		render3d.renderWall(0, 0, 1, 0.5, 0);//sideL
//		render3d.renderWall(0, 0.5, 1, 1, 0);//front render3d.renderWall(0, 0.5, 1, 1, 0);
//		render3d.renderWall(0.5, 0.5, 1, 1.5, 0);//sideR
/*		render3d.renderWall(0, 0.5, 1.5, 1.5, 0);//back (0, 0.5, 1.5, 1.5, 0); 1, 0.5, 0.5, 0.5, 0
		render3d.renderWall(0, 0, 1, 1.5, 0);//sideL
		render3d.renderWall(0, 0.5, 1, 1, 0);//front render3d.renderWall(0, 0.5, 1, 1, 0);
		render3d.renderWall(0.5, 0.5, 1, 1.5, 0);//sideR */
//		render3d.renderWallInverse(-0.5, 0, 1, 1, 0);//front render3d.renderWall(0, 0.5, 1, 1, 0);
		{
/*		render3d.renderWall(1.5, 2, 1, 1, 0);
		render3d.renderWallInverse(-2, -1.5, 1, 1, 0);
		render3d.renderWallDoubleSide(1, 1.5, 1, 1, 0);//Front//auto megalytero 0.5 se arnitiko se thetiko mikrotero to xLeft
		render3d.renderWallDoubleSide(1, 1.5, 1.5, 1.5, 0);
		render3d.renderWallDoubleSide(1.5, 2, 1, 1, 0); */
		//-4.5, -4, 1, 1, 0 // if < 0 xLeft should < xRight else xLeft > xRight
		/*e.g.  (1.0, 1.5, 1, 1, 0) or  (-1.5, -1.0, 1, 1, 0)*/ 
		/* val swap for Inverse(back render) -(xLeft) , -(xRight) for front -(xRight), (-xLeft)*/
		}
		//render3d.renderWallDoubleSide(-4.5, -4, 1, 1, 0);//front render3d.renderWall(0, 0.5, 1, 1, 0);
//		render3d.renderDWall(-2, -1, 2, 2, 0);
//		render3d.renderDWall(0, -1, 3, 3, 0);
//		render3d.renderDWall(0, 1, 1, 1, 0);
///////		render3d.renderDWall(-2, -1, 2, 2, 0);
//		render3d.renderDWall(-1, -2, 5, 5, 0);
///////		render3d.renderDWall(0, 1, 1, 1, 0);
////////	render3d.renderDWall(5, 6, 2, 2, 0);
//		render3d.renderDWall(6, 5, 5, 5, 0);
//		render3d.renderDWall(0, 1, 1, 1, 0);
//		render3d.renderDWall(5, 6, 6, 6, 0);
		
/*		render3d.renderDWall(0, 0.5, 1.5, 1.5, 0);//back (0, 0.5, 1.5, 1.5, 0); 1, 0.5, 0.5, 0.5, 0
		render3d.renderDWall(0, 0, 1, 0.5, 0);//sideL
		render3d.renderDWall(0, 0.5, 1, 1, 0);//front render3d.renderWall(0, 0.5, 1, 1, 0);
		render3d.renderDWall(0.5, 0.5, 1, 1.5, 0);//sideR */
		//render3d.renderDWall(0, 1, 2, 2, 0);//back (0, 0.5, 1.5, 1.5, 0); 1, 0.5, 0.5, 0.5, 0
		//render3d.renderDWall(0, 0, 2, 1, 0);//sideL
//		render3d.renderDWall(0, 0, 0, -1, 0);//sideL
		//render3d.renderDWall(0, 1, 1, 1, 0);//front render3d.renderWall(0, 0.5, 1, 1, 0);
		//render3d.renderDWall(1, 1, 1, 2, 0);//sideR
		

//		render3d.renderDWall(0, 0, 0, +1, 0);//sideL //must say collision if +1 diff than negative numbers too many calculations
//		render3d.renderDWall(5, 6, 2, 2, 0);
//		render3d.renderDWall(0, 1, 1, 1, 0);
//		render3d.renderDWall(-2, -1, 2, 2, 0);
//		render3d.renderDWall(0, 0, 0, -1, 0);//sideL //if i put it before others collision wont work why?
		
		render3d.renderDWall(-22, -22, 0, +1, 0);
		render3d.renderDWall(-25, -26, 2, 2, 0);
		render3d.renderDWall(-22, -21, 1, 1, 0);
		render3d.renderDWall(-24, -23, 2, 2, 0);
		render3d.renderDWall(-22, -22, 0, -1, 0);
		render3d.renderEnemy(1, 1, 0, 0);
		render3d.renderDistanceLimiter();
//		render3d.collisionDetection(-2, -1, 2, 2);
//		render3d.collisionDetection(0, 1, 1, 1);
//		render3d.collisionDetection(5, 6, 2, 2);
		//render3d.wallCrap(); //renderDistanceLimiter Filter does NOT apply if rendered here
		//render3d.renderWall(0, 0.5, 1.5, 0);
		draw(render3d,0,0);
	}

}
