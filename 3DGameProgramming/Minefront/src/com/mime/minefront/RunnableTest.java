package com.mime.minefront;

import java.awt.event.KeyEvent;

public class RunnableTest implements Runnable{//  extends Thread
	public boolean[] key;
	public volatile boolean keyp;
	public static boolean keyp1;
	public boolean keyE;
	
	public RunnableTest(boolean[] key) {
		this.key = key;
	}
	
	public RunnableTest(boolean keyE) {
		this.keyp = keyE;
	}
	
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
	   //try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
	   		//System.out.println(startedT());
		//try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		this.keyp=startedT(this.key);
		keyp1=keyp;
		//System.out.println("WORKS");
		//keyp=false;
		//keyp1=false;
		//System.out.println(keyp);
	}
	
	public   boolean startedT(boolean[] key) {
		for(int i = 0; i <= 1; i++)
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		//System.out.println("WORKS");
			return key[KeyEvent.VK_SPACE];
	}
	
//	public   boolean startedT(boolean[] key) {//boolean[] key
//		System.out.println("WORKS");
//		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
//			return true;//true;//true; key[KeyEvent.VK_H];
//	}


}
