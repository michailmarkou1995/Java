package com.mime.minefront;

import java.awt.event.KeyEvent;

public class RunnableTest implements Runnable {//  extends Thread
    public static boolean keyp1;
    public final Object lock = this;
    public boolean[] key;
    public volatile boolean keyp;
    public boolean keyE;
    public boolean pause = false;
    public double health;

    public RunnableTest(boolean[] key) {
        this.key = key;
    }

    public RunnableTest(boolean keyE) {
        this.keyp = keyE;
    }

    public void recoverHealth() {
        for (double i = 0; i <= 100; i = i + 0.1) {
            health += i;
            if (i % 20 > 10)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            System.out.println(i);
        }
        System.out.println("done");
    }

    public void pause() {
        pause = true;
    }

    public void continueT() {
        pause = false;
    }

    public void notifyA() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private void pauseThread() {
        System.out.println("pause");
        synchronized (lock) {
            if (pause)
                try {
                    lock.wait();
                    //Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }


    public void run() {
        this.keyp = startedT(this.key);
        keyp1 = keyp;
    }

    public boolean startedT(boolean[] key) {
        for (int i = 0; i <= 1; i++)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return key[KeyEvent.VK_SPACE];
    }
}
