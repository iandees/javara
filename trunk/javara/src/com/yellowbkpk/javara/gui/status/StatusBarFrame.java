package com.yellowbkpk.javara.gui.status;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StatusBarFrame extends JFrame {
    
    private JPanel contentOfUndecoratedFrame;
    protected Point dragStart;
    protected Point location;
    protected MouseEvent pressed;

    public StatusBarFrame() {
        super();
        setPreferredSize(new Dimension(640, 100));
        setLocation(0, 480);
        setUndecorated(true);
        
        contentOfUndecoratedFrame = new JPanel();
        contentOfUndecoratedFrame.setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent arg0) {
                pressed = arg0;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent arg0) {
                location = getLocation(location);
                int x = location.x - pressed.getX() + arg0.getX();
                int y = location.y - pressed.getY() + arg0.getY();
                setLocation(x, y);
            }
        });
        add(contentOfUndecoratedFrame);
    }

}
