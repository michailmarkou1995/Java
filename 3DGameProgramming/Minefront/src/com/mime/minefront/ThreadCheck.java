package com.mime.minefront;

import java.awt.event.KeyEvent;

import com.mime.minefront.input.Controller;

public class ThreadCheck implements Runnable{//     extends Thread
	
	public  Thread thread;
    private volatile boolean isStopped = false;
    public boolean[] key;
    
    public ThreadCheck (){
    	
    }

	//public ThreadCheck t = new ThreadCheck();
	
	public  synchronized boolean start1(boolean[] key) {//this is public in thread sharing res
		thread = new Thread();//this == new Game() , name of thread
		//thread = new Thread(t ,"keyEvents");
		thread.setName("keyEvents");
		thread.start();
		System.out.println(Thread.currentThread().getName());
		return  key[KeyEvent.VK_SPACE];
	}
	
	public  synchronized boolean start(boolean[] key) {//this is public in thread sharing res
		thread = new Thread();//this == new Game() , name of thread
		//thread = new Thread(t ,"keyEvents");
		thread.setName("keyEvents");
		thread.start();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println(Thread.currentThread().getName());
		return  key[KeyEvent.VK_SPACE];
	}
	
	public void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	@Override
	public void run() {
//    	thread = new Thread();//this == new Game() , name of thread
////		//thread = new Thread(t ,"keyEvents");
//		thread.setName("keyEvents");
//		thread.start();
		System.out.println(Thread.currentThread().getName());
		//startedT(key);
		   try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}

		} 
	public  synchronized boolean startedT(boolean[] key) {
	// try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
	//System.out.println(Thread.currentThread().getName());
		//try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}

		//if (Controller.timeJ>1) {
			//Controller.timeJ=0;
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			return  key[KeyEvent.VK_SPACE];
		//}
	//return  key[KeyEvent.KEY_RELEASED];
}
	
	public  synchronized void stopJ() {
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
}
	
//	@Override
//	public void run() {
//    	thread = new Thread();//this == new Game() , name of thread
////		//thread = new Thread(t ,"keyEvents");
//		thread.setName("keyEvents");
//		thread.start();
//		System.out.println(Thread.currentThread().getName());
//		while(!isStopped){ System.out.println("ThreadCheck thread is running....."); 
//		System.out.println("ThreadCheck thread is now going to pause"); 
//		try { Thread.sleep(200); }
//		catch (InterruptedException e) { e.printStackTrace(); } 
//		System.out.println("ThreadCheck thread is now resumed .."); } 
//		System.out.println("ThreadCheck thread is stopped...."); 
//		} 

	
//public void stop(){ isStopped = true; }

}
