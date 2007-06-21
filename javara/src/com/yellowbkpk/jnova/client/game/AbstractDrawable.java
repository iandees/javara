package com.yellowbkpk.jnova.client.game;

import java.awt.Color;
import java.awt.Graphics;


public class AbstractDrawable implements Drawable {

    private static final float DAMP = 0.9f;
    private static final long TIME_PER_FRAME = 10;
    protected Vector2D center;
    protected Vector2D velocity;
    protected Vector2D acceleration;

    public AbstractDrawable(Vector2D ctr, Vector2D vel, Vector2D accel) {
        center = ctr;
        velocity = vel;
        acceleration = accel;
    }
    
    public AbstractDrawable(Vector2D point) {
        this(point, new Vector2D(0f, 0f), new Vector2D(0f, 0f));
    }

    public void step(long delta) {
        float scaleRelToTimeDelta = delta / TIME_PER_FRAME;

        velocity = velocity.add(acceleration.scale(scaleRelToTimeDelta));
        center = center.add(velocity.scale(scaleRelToTimeDelta));
        velocity = velocity.scale(DAMP);
    }

    public void paint(Graphics dbg) {
        dbg.setColor(Color.black);
        dbg.fillOval((int) center.x, (int) center.y, 16, 16);
        dbg.drawLine((int) center.x+8, (int) center.y+8, (int) (center.x+8+(velocity.x*10)), (int) (center.y+8+(velocity.y*10)));
    }

    public String toString() {
        return center.toString()+velocity.toString()+acceleration.toString();
    }
    
}
