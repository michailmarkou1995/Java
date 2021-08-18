package com.mime.minefront;

import java.awt.event.KeyEvent;

public class ThreadCheck implements Runnable {//     extends Thread

    public Thread thread;
    public boolean[] key;
    private volatile boolean isStopped = false;

    public ThreadCheck() {

    }

    public synchronized boolean start1(boolean[] key) {//this is public in thread sharing res
        thread = new Thread();
        //thread = new Thread(t ,"keyEvents");
        thread.setName("keyEvents");
        thread.start();
        System.out.println(Thread.currentThread().getName());
        return key[KeyEvent.VK_SPACE];
    }

    public synchronized boolean start(boolean[] key) {//this is public in thread sharing res
        thread = new Thread();
        thread.setName("keyEvents");
        thread.start();
        System.out.println(Thread.currentThread().getName());
        return key[KeyEvent.VK_SPACE];
    }

    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized boolean startedT(boolean[] key) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return key[KeyEvent.VK_SPACE];
    }

    public synchronized void stopJ() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
