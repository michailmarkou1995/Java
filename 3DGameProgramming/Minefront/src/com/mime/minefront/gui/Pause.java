package com.mime.minefront.gui;

import com.mime.minefront.Display;
import com.mime.minefront.RunGame;
import com.mime.minefront.input.InputHandler;
import com.mime.minefront.input.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serial;

public class Pause extends JFrame implements Runnable {

    @Serial
    private static final long serialVersionUID = 1L;
    public static Pause pausedThreadTry2 = null;
    protected JPanel window = new JPanel();
    boolean running = false;
    Thread thread;
    InputHandler input;

    public Pause() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        input = RunGame.getGameInstance().input;
        assert robot != null;
        robot.keyPress(KeyEvent.VK_0);
        robot.keyRelease(KeyEvent.VK_0);
        input.key[KeyEvent.VK_ESCAPE] = false;
        addKeyListener(input);
        startPauseMenu();
        RunGame.getGameInstance().paused();
        pausedThreadTry2 = this;//works only here after whole Construct Object initialized
    }

    @Override
    public void run() {
        requestFocus();
        while (running) {
            System.out.println("Pause Menu");
            try {
                inputKey(input.key);
            } catch (IllegalStateException e) {
                System.out.println("Catched");
                e.printStackTrace();
            }
        }
    }

    public synchronized void startPauseMenu() {
        running = true;
        thread = new Thread(this, "paused");
        thread.start();
    }

    private void inputKey(boolean[] keys) {
        boolean test = (keys[KeyEvent.VK_ESCAPE]);
        // ESC key Pause-UN-pause the Game
        if (input.key[KeyEvent.VK_ESCAPE]) {
            RunGame.getGameInstance().continued();
            PlayerController.not_paused = true;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // With Enter Exit Main Game back to Launcher
        if (input.key[KeyEvent.VK_ENTER]) {
            this.dispose();
            Display.setLauncherInstance(new Launcher(0));
            RunGame.getDispose();
            Display.getGameInstance(RunGame.getGameInstance()).stop();
        }
    }
}
