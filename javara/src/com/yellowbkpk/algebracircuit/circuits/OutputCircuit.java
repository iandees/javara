package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class OutputCircuit extends BaseCircuit {

    public OutputCircuit(Point c) {
        super(CircuitsEnum.OUTPUT, c, "", 1);
    }
    
    void recomputeShape() {
    }

    public void draw(Graphics g) {
        if (inputs[0] == null) {

        } else {
            g.drawLine(center.x - (RADIUS / 2), center.y, inputs[0].getOutputPoint().x, inputs[0].getOutputPoint().y);
        }
        
        g.setColor(getBackgroundColor());
        g.fillRect(center.x - (RADIUS / 2), center.y - (RADIUS / 2), (RADIUS / 2) * 2, (RADIUS / 2) * 2);
        g.setColor(getForegroundColor());
        g.drawRect(center.x - (RADIUS / 2), center.y - (RADIUS / 2), (RADIUS / 2) * 2, (RADIUS / 2) * 2);
        g.setColor(getTextColor());
        
        if(inputs[0] != null) {
            g.setColor(getTextColor());
            g.drawString(getValue() + "", center.x, center.y);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(center.x - (RADIUS / 2), center.y - (RADIUS / 2), RADIUS, RADIUS);
    }

    public Point getOutputPoint() {
        return new Point(center.x + (RADIUS / 2), center.y);
    }

	double getUpdatedValue() {
		if (inputs[0] == null) {
			return Double.NaN;
		} else {
			return inputs[0].getValue();
		}
	}

}
