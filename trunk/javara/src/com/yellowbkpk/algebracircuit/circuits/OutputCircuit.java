package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class OutputCircuit extends BaseCircuit {

    public OutputCircuit(Point c) {
        super(CircuitsEnum.OUTPUT, c, "", 1);
    }

    public void draw(Graphics g) {
        g.setColor(getBackgroundColor());
        g.fillRect(center.x - (RADIUS / 2), center.y - (RADIUS / 2), (RADIUS / 2) * 2, (RADIUS / 2) * 2);
        g.setColor(getForegroundColor());
        g.drawRect(center.x - (RADIUS / 2), center.y - (RADIUS / 2), (RADIUS / 2) * 2, (RADIUS / 2) * 2);
        g.setColor(getTextColor());
        if (inputs[0] == null) {

        } else {
            g.drawLine(center.x, center.y, inputs[0].getCenter().x, inputs[0].getCenter().y);
            g.drawString(getValue() + "", center.x, center.y);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(center.x - (RADIUS / 2), center.y - (RADIUS / 2), RADIUS, RADIUS);
    }

    public double getValue() {
        return inputs[0].getValue();
    }

}
