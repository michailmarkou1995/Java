package com.mime.minefront.entity.mob;

import com.mime.minefront.entity.Entity;
import com.mime.minefront.input.PlayerController;

public class Mob extends Entity{
	
	//move "player"
	public void move(double xa, double za, double rot, double walkSpeed) {//e.g. what Enitity Has not like Rock wont move !
		if (xa != 0 && za != 0) {
			move(xa, 0, rot, walkSpeed);
			move(0, za, rot, walkSpeed);
			return;
		}
		
		/*VECTOR CALCS for Direction of movement*/
		System.out.println("rot: " + rot);//rads
		//System.out.println(rot *180/Math.PI);//degrees
		//System.out.println(xa*Math.sin(rot));//x axis with D goes in x "forward" while radius
		//System.out.println(xa*Math.cos(rot)); //same as above result
		//System.out.println(xa*Math.cos(rot) + za*Math.sin(rot));
		//System.out.println(xa*Math.sin(rot) + za*Math.cos(rot));
		//System.out.println( za*Math.cos(rot));
		//System.out.println( xa*Math.sin(rot) + za*Math.sin(rot)); //0 so x doesnt move based on ROT 
		//System.out.println((za*Math.cos(rot) - xa*Math.sin(rot)));
		//System.out.println((za*Math.sin(rot)));
		
		//on rads only here calc
		double nx = (xa*Math.cos(rot) + za*Math.sin(rot)) * walkSpeed;//vectors to ensure correct way
		double nz = (za*Math.cos(rot) - xa*Math.sin(rot)) *  walkSpeed; //0;
		
		//mine working
//		PlayerController.xa=nx;
//		PlayerController.za=nz;
//		
//		x += PlayerController.xa;
//		z += PlayerController.za;
//		PlayerController.xa *= 0.1;
//		PlayerController.za *= 0.1;
		
		//cherno working
		x+=nx;
		z+=nz;
		xa*=0.1;
		za*=0.1;
		
		/**********/
		
//		nx *= 0.1;
//		nz *= 0.1;
//		xa=nx;
//		za=nz;
//		x += xa;
//		z += za;
		
		
//		nx *= 0.1;
//		nz *= 0.1;
//		xa=nx;
//		za=nz;

	}

}
