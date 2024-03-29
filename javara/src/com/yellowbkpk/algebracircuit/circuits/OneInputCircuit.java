package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public abstract class OneInputCircuit extends BaseCircuit {

    private Point aa;
    private Point ab;
    private Point ac;

    OneInputCircuit(CircuitsEnum t, Point c, String l) {
        super(t, c, l, 1);

        recomputeShape();
    }

    void recomputeShape() {
        aa = new Point(center.x - (RADIUS / 3), center.y - (RADIUS / 2));
        ab = new Point(center.x + (2 * (RADIUS / 3)), center.y);
        ac = new Point(center.x - (RADIUS / 3), center.y + (RADIUS / 2));
    }

    public void draw(Graphics g) {
        Polygon pts = new Polygon();
        pts.addPoint(aa.x, aa.y);
        pts.addPoint(ab.x, ab.y);
        pts.addPoint(ac.x, ac.y);
        
        g.setColor(getBackgroundColor());
        g.fillPolygon(pts);
        g.setColor(getForegroundColor());
        g.drawPolygon(pts);
        g.setColor(getTextColor());
        g.drawString(getLabel(), center.x - (RADIUS / 3) + 2, center.y + (RADIUS / 8));
        
        if (inputs[0] != null) {
            g.drawLine(aa.x, center.y, inputs[0].getOutputPoint().x, inputs[0].getOutputPoint().y);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(aa.x, aa.y, ab.x-aa.x, ac.y-aa.y);
    }

    public Point getOutputPoint() {
        return ab;
    }
    
}
