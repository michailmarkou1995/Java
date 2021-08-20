package com.mime.minefront.gui;

import com.mime.minefront.Configuration;
import com.mime.minefront.input.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class Options extends JFrame {//extends Launcher


    @Serial
    private static final long serialVersionUID = 1L;
    protected int buttonWidth = 80, buttonHeight = 40;
    Configuration config = new Configuration();
    int w = 0, h = 0;
    JPanel window = new JPanel();
    private int width = 550, height = 450;
    private JButton OK;
    private JTextField twidth, theight;
    private JLabel lwidth, lheight;
    private Rectangle rOK, rresolution;
    private Choice resolution = new Choice();

    public Options() {
        setTitle("Options");
        setSize(new Dimension(width, height));
        add(window);
        setLocationRelativeTo(null);
        setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);//new BorderLayout()
        setVisible(true);

        drawButtons();
        window.repaint();
    }

    private void drawButtons() {
        OK = new JButton("OK");
        rOK = new Rectangle((width - 100), height - 70, buttonWidth, buttonHeight - 10);
        OK.setBounds(rOK);
        window.add(OK);

        rresolution = new Rectangle(50, 80, 80, 25);
        resolution.setBounds(rresolution);
        resolution.add("640, 480");
        resolution.add("800, 600");
        resolution.add("1024, 768");
        resolution.select(1);
        window.add(resolution);

        lwidth = new JLabel("Width: ");
        lwidth.setBounds(30, 150, 120, 20);
        //lwidth.setFont(new Font("Verdana", 2, 16));
        window.add(lwidth);

        lheight = new JLabel("Height: ");
        lheight.setBounds(30, 180, 120, 20);
        window.add(lheight);

        twidth = new JTextField();
        twidth.setBounds(80, 150, 60, 20);
        window.add(twidth);

        theight = new JTextField();
        theight.setBounds(80, 180, 60, 20);
        window.add(theight);

/*		OK.addActionListener(e->{

		});*/
        OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InputHandler.MouseButton = 0;
                dispose();
                config.saveConfiguration("width", parseWidth());
                config.saveConfiguration("height", parseHeight());
            }
        });
    }

    private void drop() {
        //load from "RAM"
        //Display.selection = resolution.getSelectedIndex();//which is 1 resolution.add("800, 600");

        //load from "DISK"
        int selection = resolution.getSelectedIndex();
        //int w=0, h=0;
        if (selection == 0) {
            w = 640;
            h = 400;
        }
        if (selection == 1 || selection == -1) {//-1 is nothing is selected
            w = 800;
            h = 600;
        }
        if (selection == 2) {
            w = 1024;
            h = 768;
        }
        //System.out.println(Display.selection);
    }

    //if not null gets Text Res and not Choice "dropbox" values
    public int parseWidth() {
        //what happens in try stays in try block
        try {
            int w = Integer.parseInt(twidth.getText());//else null
            return w;//this w is from int w not Global int w above in class
        } catch (NumberFormatException e) {
            drop();
            return w;//Global w from drop method
        }
    }

    private int parseHeight() {
        //what happens in try stays in try block
        try {
            return Integer.parseInt(theight.getText());
        } catch (NumberFormatException e) {
            drop();
            return h;//Global w from drop method
        }
    }

}
