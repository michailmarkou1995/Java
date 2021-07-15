package com.mime.minefront;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import com.mime.minefront.input.Controller;
import com.mime.minefront.level.Level;

public class Game {

	public int time;
	public Controller controls;
	public Level level;
	public boolean jumpIf;
	ThreadCheck threadCheck;
	ThreadTest tt1;
	Thread t1;
	
	public Game() {
		controls = new Controller();
		level = new Level(40, 40);
		threadCheck = new ThreadCheck();
		//t1 = new Thread(threadCheck, "T1");
		//t1.start();
		tt1 = new ThreadTest();
	}
	
	public void tick(boolean[] key) {
		for (int i =0; i < key.length; i++) {
			if(key[i] == true) {
				//System.out.println(i);
				//System.out.println(Arrays.toString(key));
			}
		}
		//System.out.println((key[KeyEvent.VK_H]));
		time++;
		//get the array boolean[] if its true or false from method calls inside inputHandler
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean right = key[KeyEvent.VK_D];
		boolean left = key[KeyEvent.VK_A];
		boolean turnLeft = key[KeyEvent.VK_LEFT];
		boolean turnRight = key[KeyEvent.VK_RIGHT];
		boolean crouch = key[KeyEvent.VK_CONTROL];
		boolean sprint = key[KeyEvent.VK_SHIFT];
		boolean regenH = key[KeyEvent.VK_H];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean esc = key[KeyEvent.VK_ESCAPE];
		
		{
		//Thread creation name gone wrong ++ down
//		RunnableTest r = new RunnableTest(key);
//		Thread thread = new Thread(r); 
//		System.out.println(thread.getName());
//		thread.start();//new Thread(r).start();
//		//try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}
//		jumpIf = RunnableTest.keyp1;
//		System.out.println( RunnableTest.keyp1);//key
		}
		//ThreadTest tt = new ThreadTest();
		//tt.getName();
//		for(int i = 0; i<5; i++) {
//			tt.recoverHealth();
//			tt.run();
//		}
		//tt.run();

		//Thread threadCh = new Thread(new ThreadCheck());//threadCheck
		//threadCh.start();
		//if (Controller.timeJ >= 1) {
			// jumpIf = threadCheck.startedT(key);
		//}else
		    // threadCheck.stopJ();
		//boolean jumpIf = threadCheck.start(key);
		//threadCheck.wait(100);
		
		//t1.getName();
		
//		 System.out.println(Thread.currentThread().getName()
//                 + " is stopping game thread");
//		 threadCheck.thread.stop();
		 
//		System.out.println(controls.y);
//		//if (controls.y==0 && Controller.jumpAgain)
//			jumpIf = key[KeyEvent.VK_SPACE];
//		//else jumpIf =false;
		
		//System.out.println(Controller.timeJ1);
//		if (Controller.timeJ1 > 2000) {
//			jumpIf = key[KeyEvent.VK_SPACE];
//			if(jumpIf == true)
//			Controller.timeJ1=0;
//		}else
//		jumpIf = key[KeyEvent.KEY_RELEASED];
//		System.out.println(jumpIf);

//		while (Controller.timeJ < 3000) {
//		 jumpIf = key[KeyEvent.VK_SPACE];
//		 try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}
		//jumpIf = Keyrelease?
		
//		if (Controller.jumped==false) jump = key[KeyEvent.VK_SPACE];
//		else jump=false;

		controls.tick(forward, back, left, right, turnLeft, turnRight, jump, crouch, 
				sprint, regenH, esc);
	}
}
