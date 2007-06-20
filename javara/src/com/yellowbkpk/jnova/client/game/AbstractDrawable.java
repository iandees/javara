package com.yellowbkpk.jnova.client.game;

import java.awt.Color;
import java.awt.Graphics;


public class AbstractDrawable implements Drawable {

    private static final float DAMP = 0.9f;
    protected Vector2D center;
    protected Vector2D velocity;
    protected Vector2D acceleration;

    public AbstractDrawable(Vector2D ctr, Vector2D vel, Vector2D accel) {
        center = ctr;
        velocity = vel;
        velocity.normalize();
        acceleration = accel;
        acceleration.normalize();
    }
    
    public AbstractDrawable(Vector2D point) {
        this(point, new Vector2D(0f, 1f), new Vector2D(0f, 1f));
    }

    public void step() {
        center = center.add(velocity);
        velocity = velocity.multiply(acceleration);
        acceleration = acceleration.multiply(DAMP, DAMP);
        
        if(velocity.length() < 1.01) {
            velocity.normalize();
        }
        
        if(acceleration.length() < 1.01) {
            acceleration.normalize();
        }
    }

    public void paint(Graphics dbg) {
        dbg.setColor(Color.black);
        dbg.fillOval((int) center.x, (int) center.y, 16, 16);
        dbg.drawLine((int) center.x+8, (int) center.y+8, (int) (center.x+8+(velocity.x*20)), (int) (center.y+8+(velocity.y*10)));
    }

    public String toString() {
        return center.toString()+velocity.toString()+acceleration.toString();
    }
    
}
