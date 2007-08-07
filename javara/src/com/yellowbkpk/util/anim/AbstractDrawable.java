package com.yellowbkpk.util.anim;

import com.yellowbkpk.util.vecmath.Vector3D;

public abstract class AbstractDrawable implements DrawableIF {

    private static final float DAMP = 0.97f;
    private static final float MAX_SPEED = 5;
    protected Vector3D center;
    protected Vector3D linearVelocity;
    protected Vector3D acceleration;

    public AbstractDrawable(Vector3D ctr, Vector3D vel, Vector3D accel) {
        center = ctr;
        linearVelocity = vel;
        acceleration = accel;
    }
    
    public AbstractDrawable(Vector3D point) {
        this(point, new Vector3D(0f, 0f, 0f), new Vector3D(0f, 0f, 0f));
    }

    public void step(long delta) {
        float scaleRelToTimeDelta = delta / AnimatedPanel.TIME_PER_FRAME;

        linearVelocity = linearVelocity.add(acceleration.scale(scaleRelToTimeDelta));
        
        if(linearVelocity.length() > MAX_SPEED) {
            linearVelocity = linearVelocity.scale(0.9f);
        }
        
        center = center.add(linearVelocity.scale(scaleRelToTimeDelta));
        linearVelocity = linearVelocity.scale(DAMP);
        acceleration = acceleration.scale(DAMP);
    }

    public String toString() {
        return center.toString()+linearVelocity.toString()+acceleration.toString();
    }

    public Vector3D getLinearVelocity() {
        return linearVelocity;
    }
    
    public Vector3D getPosition() {
        return center;
    }
    
    public Vector3D getAcceleration() {
        return acceleration;
    }
    
}
