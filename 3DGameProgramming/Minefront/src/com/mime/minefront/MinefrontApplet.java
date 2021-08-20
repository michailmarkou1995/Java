package com.mime.minefront;

import java.applet.Applet;
import java.awt.*;
import java.io.Serial;

/**
 * @deprecated Applet usage to run on browser plugin support
 */
public class MinefrontApplet extends Applet {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Display display = new Display();

    public void init() {
        setLayout(new BorderLayout());
        add(display);
    }

    public void start() {
        display.start();
    }

    public void stop() {
        display.stop();
    }
}
