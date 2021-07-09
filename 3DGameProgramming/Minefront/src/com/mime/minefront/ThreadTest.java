package com.mime.minefront;

import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class ThreadTest extends Thread{//extends Thread  implements Runnable
	
	   public Object lock = this;
	    public boolean pause = false;
	    public double health;
	    
	public void recoverHealth() {
		for (double i = 0; i <= 100; i = i + 0.1) {
			health+=i;
			if (i % 20 > 10 )
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			System.out.println(i);
		}
		System.out.println("done");
	}
	
	  public void pause ()
	    {
	        pause = true;
	    }

	    public void continueT ()
	    {
	        pause = false;
	    }
	    
	    public void notifyA () {
	      synchronized (lock)
	        {
	            lock.notifyAll();
	        }
	    }
	    
	    private void pauseThread ()
	    {
	    	System.out.println("pause");
	        synchronized (lock)
	        {
	            if (pause)
					try {
						lock.wait();
						//Thread.sleep(1000000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} // Note that this can cause an InterruptedException
	        }
	    }
	    
	    
	public void run() {
//		try {
//		    Thread.sleep(1000);
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
//		pauseThread();
//		recoverHealth();
		//for(int i = 0; i<5; i++) {
	   //try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();} // <--THIS RUN ONLY on  INSTASIATE NOT ON EVERY ITERATION!!!
	   //if (health <=95)
		recoverHealth();

	   //else System.out.println("is FULL");
	   //try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();}

		//pause();
		//notifyA();
		//pauseThread();
		//continueT();
	//}
	}
	
//	public  synchronized boolean start(boolean[] key) {
//		// try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
//		//System.out.println(Thread.currentThread().getName());
//		return  key[KeyEvent.VK_SPACE];
//	}

}
