package com.mime.minefront;

import com.mime.minefront.gui.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

// Main JFrame here for main method to be called
public class RunMiniGame {

    static Color game;

    public RunMiniGame() {
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
        game = new Color(640, 390);

        game.frame = new JFrame();
        game.frame.add(game);
        game.frame.setResizable(false);
        game.frame.setTitle("Color");

        game.frame.setSize(game.getGameWidth(), game.getGameHeight());

        game.frame.getContentPane().setCursor(blank);
        game.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                //System.exit(0);
                Display.setLauncherInstance(new Launcher(0));
            }
        });
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        Launcher.getDispose();
        game.start();
        stopMenuThread();
    }

    private void stopMenuThread() {
        Display.getLauncherInstance().stopMenu();
    }
}
