package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class InputCircuit extends BaseCircuit {

    private Point aa;
    private Point ab;
    private Point ac;

    public InputCircuit(Point c) {
        super(CircuitsEnum.INPUT, c, "", 0);

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
        g.drawString(getValue()+"", center.x - (RADIUS / 3) + 2, center.y + (RADIUS / 8));
    }

    public Rectangle getBounds() {
        return new Rectangle(aa.x, aa.y, ab.x - aa.x, ac.y - aa.y);
    }

    public void setValue(double d) {
        value = d;
    }

    public Point getOutputPoint() {
        return ab;
    }

	public double getUpdatedValue() {
		return value;
	}
    
}
