package com.mime.minefront;

public class HealthRecoveryThread extends Thread {//extends Thread  implements Runnable

    public final Object lock = this;
    public boolean pause = false;
    public double health;

    // Press H to gradually increase health
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
        recoverHealth();
    }
}
