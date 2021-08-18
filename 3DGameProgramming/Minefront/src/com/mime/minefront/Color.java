package com.mime.minefront;

import com.mime.minefront.graphics.ResourceMini;
import com.mime.minefront.input.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.Serial;

public class Color extends Canvas implements Runnable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int width;
    private final int height;
    protected JFrame frame;
    double x = 100, y = 100;
    InputHandler input;
    ResourceMini resource;
    private Thread thread;
    private boolean running = false;

    public Color(int width, int height) {
        this.width = width;
        this.height = height;

        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        resource = new ResourceMini();
        input = new InputHandler();
        addKeyListener(input);
    }

    public int getGameWidth() {
        return width;
    }

    public int getGameHeight() {
        return height;
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "MiniGame");
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void run() {
        requestFocus();
        while (running) {
            update();
            render();
        }
    }

    public void update() {
        if (input.key[KeyEvent.VK_UP]) {
            y--;//y=-0.2; if double if int not same speed
        }
        if (input.key[KeyEvent.VK_DOWN]) {
            y++;
        }
        if (input.key[KeyEvent.VK_LEFT]) {
            x--;
        }
        if (input.key[KeyEvent.VK_RIGHT]) {
            x++;
        }
        //System.out.println("Color value: "+ resource.getColorHex((int)x,(int)y));
    }


    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, 800, 400);
        g.drawImage(resource.loadImage("/textures/mini_game.png"), 0, 0, resource.getWidth(), resource.getHeight(), null);
        g.fillRect((int) x, (int) y, 32, 32);
        g.setFont(new Font("Verdana", 0, 50));
        if (resource.getColorHex((int) x, (int) y) == 0xFF6A00) {
            int xx = (int) x;
            int yy = (int) y;
            //text do not go out of bounds
            if (xx < 20) xx = 20;
            if (xx > 405) xx = 405;
            if (yy < 45) yy = 45;
            if (yy > 350) yy = 350;
            g.drawString("Collision!", xx, yy); //(int)y-10
        }
        if (resource.getColorHex((int) x, (int) y) == 0x0026FF) {
            g.drawString("Blue!", 250, 150);
        }
        g.dispose();
        bs.show();
    }


}
