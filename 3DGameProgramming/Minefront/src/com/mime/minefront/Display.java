package com.mime.minefront;

import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.gui.Launcher;
import com.mime.minefront.input.InputHandler;
import com.mime.minefront.input.PlayerController;

import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serial;

public class Display extends Canvas implements Runnable {
    public static final String TITLE = "Minefront Pre-Alpha 0.05";
    @Serial
    private static final long serialVersionUID = 1L;
    public static int selection = 1;
    public static int WIDTH = 800, HEIGHT = 600;
    public static Point WindowLocation;
    public static int mouseSpeed;
    public static int MouseSpeed;
    public static Graphics g;
    public static boolean onceDid = false;
    static Launcher launcher = null;
    private final Screen screen;
    private final BufferedImage img;
    private final Game game;
    private final int[] pixels;
    public InputHandler input;
    protected JFrame frame;
    Robot robot;
    private Thread thread;
    private boolean running = false;
    private Render render;
    private int oldX = 0;
    private int oldY = 0;
    private int fpsInnerText;

    /**
     * <p>Main Constructor Set ups Window & Data Buffer with event Listeners</p>
     */
    public Display() {
        Dimension size = new Dimension(getGameWidth(), getGameHeight());
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        screen = new Screen(getGameWidth(), getGameHeight());
        input = new InputHandler();
        game = new Game(input);
        img = new BufferedImage(getGameWidth(), getGameHeight(), BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();


        addKeyListener(input);
        addFocusListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);

    }

    /**
     * <p>Used with multi-threads Get Instance or create a new one With Fixed Constructor</p>
     *
     * @return The "Launcher" instance from static method
     */
    public static Launcher getLauncherInstance() {
        if (launcher == null) {
            launcher = new Launcher(0);
        }
        return launcher;//start the launcher from here !
    }

    /**
     * <p>Creates a "Launcher" Instance</p>
     *
     * @param launcher New Instance Launcher with a chosen constructor
     */
    public static void setLauncherInstance(Launcher launcher) {
        Display.launcher = launcher;
    }

    /*? used anywhere really?*/
    public static Display getGameInstance(Display display) {
        assert display != null;
        return display;
    }

    // returns Window Max Width that has been set
    public static int getGameWidth() {
        return WIDTH;
    }

    // returns Window Max Height that has been set
    public static int getGameHeight() {
        return HEIGHT;
    }

    // Driver Class Launching a "Launcher" Instance
    public static void main(String[] args) {
        getLauncherInstance();
    }

    // Start a Thread of this "game" Instance
    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "game");
        thread.start();
    }

    // stops "game" Instance Thread
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

    /*? used anywhere really?*/
    public synchronized void paused() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * <h2>Game Menu Pause Thread to Continue on "esc"</h2>
     */
    public synchronized void continued() {
        notify();
        input.key[KeyEvent.VK_ESCAPE] = false;
    }

    /**
     * <p>Main Logic of "game" Instance</p>
     */
    @Override
    public void run() {

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1_000_000_000.0 / 60.0;//60 times per second
        double delta = 0;
        int frames = 0;
        int updates = 0;

        requestFocus();
        //game loop
        while (running) { //cpu cycles

            //get now time number;
            long now = System.nanoTime();
            {
                // When run Game Paused then pressed "Enter" exits to Launcher
                // on Return Flickering paused menu-to-game eliminate
                for (int ii = 0; ii <= 1; ii++) {
                    for (int i = 0; i <= 20; i++) {
                        // Proper elimination of flickering "PAUSED"
                        if (onceDid) {
                            try {
                                robot = new Robot();
                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                            robot.keyPress(KeyEvent.VK_ESCAPE);
                            robot.keyRelease(KeyEvent.VK_ESCAPE);
                        }
                    }
                    onceDid = false;
                }
            }
            // re-assure not flickering on return after "enter" re-launch launcher -> go into game
            if (onceDid) {
                input.key[KeyEvent.VK_ESCAPE] = false;
                onceDid = false;
            }

            // FPS Render Logic & Update(tick) "Lock" Game Logic Counter
            // time difference in NanoSeconds (can't debug easily because "step in debug" is slower than nanoseconds)
            delta += (now - lastTime) / ns;
            lastTime = now; // get Time now since Last Initiation (seek difference in time pass)
            // Update and lock in 60 fps
            while (delta >= 1) {//only 60 times per second..delta >= ns ..1/60 if passed?
                tick();
                updates++;
                delta--;

            }
            // FPS render
            render();
            frames++;

            // do Every 1 sec (60 fps)
            if (System.currentTimeMillis() - timer > 1000) {
                PlayerController.timeJ1 = System.currentTimeMillis() - timer;
                timer += 1000;
                fpsInnerText = frames;
                frame.setTitle(TITLE + "    |    " + updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
                // jump allowed every 1 sec passed(60 fps)
                PlayerController.jumped = false;
            }
        }
    }

    public void render() {
        if (!PlayerController.not_paused) {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", Font.BOLD, 50));
            g.drawString("PAUSED", WIDTH / 2 - 100, HEIGHT / 2);
            g.dispose();
            bs.show();
        } else {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }

            screen.render(game);

            for (int i = 0; i < getGameWidth() * getGameHeight(); i++) {
                pixels[i] = screen.PIXELS[i];
            }

            g = bs.getDrawGraphics();
            g.drawImage(img, 0, 0, getGameWidth(), getGameHeight(), null);
            g.setFont(new Font("Verdana", Font.PLAIN, 50));
            g.setColor(Color.WHITE);
            g.drawString(fpsInnerText + " fps", 20, 50);
            g.dispose();
            bs.show();
        }
    }

    //update method
    private void tick() {
        input.tick();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < input.key.length; i++) {
            if (input.key[i]) {
                //System.out.println(i); System.out.println(Arrays.toString(input.key));
            }
        }
        game.tick();

        int newX = InputHandler.MouseX;
        int newY = InputHandler.MouseY;
        int winX = InputHandler.WindowX;
        int winY = InputHandler.WindowY;

        {
            //mouse Speed below calc
			/*
			 * Pythagore theorem
				The distance travelled by the mouse between 2 calls to the mouseMotionListener is

				squareRoot(deltaX + deltaY)

				where:
				deltaX is (oldX - newX) at the power of 2
				deltaY is (oldY - newY) at the power of 2

				your mouseMotionListener can also store the time in milli if you want a really exact "speed"
			 */
            double mouseSpeedX_temp = Math.abs((oldX - newX)); /*Vector X then SQRT this !*/
            double mouseSpeedY_temp = Math.abs((oldY - newY));/*Vector Y then SQRT this !*/
            mouseSpeed = (int) Math.sqrt(mouseSpeedX_temp * mouseSpeedX_temp + mouseSpeedY_temp * mouseSpeedY_temp);
        }

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        if (newX < 0 || newX > WIDTH) robot.mouseMove(winX + 500, winY + 500);
        if (newY < 0 || newY > HEIGHT) robot.mouseMove(winX + 500, winY + 500);
        if (newX < 0) robot.mouseMove(winX + getGameWidth() - 20, winY + newY);
        if (newX >= getGameWidth() - 20) robot.mouseMove(winX + 20, winY + newY);
        if (newY < 15) robot.mouseMove(winX + newX, winY + getGameHeight() - 20);
        if (newY >= getGameHeight() - 60) robot.mouseMove(winX + newX, winY + 40);

        if (newY < oldY && PlayerController.rotationUp <= 2.8) {
            PlayerController.turnUpM = true;
        }
        if (newY < oldY && PlayerController.rotationUp >= 2.8) {
            PlayerController.turnUpM = false;
        }
        if (newY == oldY) {
            PlayerController.turnUpM = false;
            PlayerController.turnDownM = false;
        }
        if (newY > oldY && PlayerController.rotationUp >= -0.8) {
            PlayerController.turnDownM = true;
        }
        if (newY > oldY && PlayerController.rotationUp <= -0.8) {
            PlayerController.turnDownM = false;
        }

        if (newX > oldX) {
            PlayerController.turnRightM = true;
        }
        if (newX == oldX) {
            PlayerController.turnLeftM = false;
            PlayerController.turnRightM = false;
        }
        if (newX < oldX) {
            PlayerController.turnLeftM = true;
        }

        double mouseSpeedX_temp = Math.abs((oldX - newX));
        double mouseSpeedY_temp = Math.abs((oldY - newY));
        MouseSpeed = (int) Math.sqrt(mouseSpeedX_temp * mouseSpeedX_temp + mouseSpeedY_temp * mouseSpeedY_temp);
        oldX = newX;
        oldY = newY;
    }
}
