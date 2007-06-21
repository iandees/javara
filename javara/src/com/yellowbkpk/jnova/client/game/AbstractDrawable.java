package com.yellowbkpk.jnova.client.game;

import com.yellowbkpk.jnova.client.gui.AnimatedPanel;



public abstract class AbstractDrawable implements Drawable {

    private static final float DAMP = 0.95f;
    private static final float MAX_SPEED = 5;
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
        float scaleRelToTimeDelta = delta / AnimatedPanel.TIME_PER_FRAME;

        velocity = velocity.add(acceleration.scale(scaleRelToTimeDelta));
        
        if(velocity.length() > MAX_SPEED) {
            velocity = velocity.scale(0.9f);
        }
        
        center = center.add(velocity.scale(scaleRelToTimeDelta));
        velocity = velocity.scale(DAMP);
        acceleration = acceleration.scale(DAMP);
    }

    public String toString() {
        return center.toString()+velocity.toString()+acceleration.toString();
    }
    
}
