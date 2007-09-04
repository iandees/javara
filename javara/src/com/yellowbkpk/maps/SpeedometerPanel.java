package com.yellowbkpk.maps;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Ian Dees
 *
 */
public class SpeedometerPanel extends JPanel {
    
    private double speed;
    private double maxSpeed = 100.0;

    public SpeedometerPanel() {
        speed = 0.0;
        
        setPreferredSize(new Dimension(200,200));
    }
    
    protected void paintComponent(Graphics g) {
        g.drawArc(getWidth()/2, getHeight()/2, getWidth()-10, getHeight()-10, 0, 120);
    }

    /**
     * @param newSpeed
     */
    public void setSpeed(double newSpeed) {
        speed = newSpeed;
        repaint();
    }

}
