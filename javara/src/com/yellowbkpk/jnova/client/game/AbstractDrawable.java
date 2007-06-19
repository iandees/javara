package com.yellowbkpk.jnova.client.game;

import java.awt.Color;
import java.awt.Graphics;


public class AbstractDrawable implements Drawable {

    private static final float DAMP = 0.9f;
    private Vector2D center;
    private Vector2D speed;
    private Vector2D acceleration;

    public AbstractDrawable(Vector2D point, Vector2D spd, Vector2D accel) {
        center = point;
        speed = spd;
        acceleration = accel;
    }
    
    public AbstractDrawable(Vector2D point) {
        this(point, new Vector2D(1f, 1f), new Vector2D(1f, 1f));
    }

    public void step() {
        center = center.add(speed.x, speed.y);
        speed = speed.multiply(acceleration.x, acceleration.y);
        acceleration = acceleration.multiply(DAMP,  DAMP);
    }

    public void paint(Graphics dbg) {
        dbg.setColor(Color.black);
        dbg.fillOval((int) center.x, (int) center.y, 5, 5);
    }

}
