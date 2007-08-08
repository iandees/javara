package com.yellowbkpk.pool.game;

import java.awt.Color;
import java.awt.Graphics;

import com.yellowbkpk.util.anim.AbstractDrawable;
import com.yellowbkpk.util.anim.AnimatedPanel;
import com.yellowbkpk.util.vecmath.Vector3D;

public class Ball extends AbstractDrawable {

    private static final float MAX_SPEED = 5;
    private static final float DAMP = 0.95f;
    
    private static final int MV_STATIONARY = 0;
    private static final int MV_ROLLING = 1;
    private static final int MV_SLIDING = 2;
    
    private int radius;
    private int diam;
    private Vector3D angularVelocity;
    private int movement;
    private boolean inGame;
    
    public Ball() {
        this(0,0);
    }
    
    public Ball(int xLoc, int yLoc) {
        this(xLoc, yLoc, 0, 6);
    }

    public Ball(int xLoc, int yLoc, int zLoc, int rad) {
        this(new Vector3D(xLoc, yLoc, zLoc), new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), rad);
    }
    
    public Ball(Vector3D location, Vector3D velocity, Vector3D acceleration, int rad) {
        super(location, velocity, acceleration);
        radius = rad;
        diam = rad * 2;
        angularVelocity = new Vector3D(0,0,0);
        inGame = true;
    }

    public void paint(Graphics dbg) {
        if (isSliding()) {
            dbg.setColor(Color.orange);
        } else if(isRolling()) {
            dbg.setColor(Color.red);
        }
        dbg.fillOval((int) center.x - radius, (int) center.y - radius, diam, diam);
    }

    public boolean isRolling() {
        return movement == MV_ROLLING;
    }

    public void step(float delta) {
        float scaleRelToTimeDelta = delta / AnimatedPanel.TIME_PER_FRAME;

        linearVelocity = linearVelocity.add(acceleration.scale(scaleRelToTimeDelta));
        
        if(linearVelocity.length() > MAX_SPEED) {
            linearVelocity = linearVelocity.scale(0.9f);
        }
        
        center = center.add(linearVelocity.scale(scaleRelToTimeDelta));
        linearVelocity = linearVelocity.scale(DAMP);
        acceleration = acceleration.scale(DAMP);
    }

    public boolean isMoving() {
        return movement > MV_STATIONARY;
    }

    public boolean isAlive() {
        return inGame;
    }

    public boolean isSliding() {
        return movement == MV_SLIDING;
    }

    public Vector3D getAngularVelocity() {
        return angularVelocity;
    }

    public float getRadius() {
        return radius;
    }

    public void setAngularVelocity(Vector3D angular) {
        angularVelocity = angular;
    }

    public void setLinearVelocity(Vector3D linear) {
        linearVelocity = linear;
    }

    public void setIsRolling() {
        movement = MV_ROLLING;
    }

    public boolean isStationary() {
        return movement == MV_STATIONARY;
    }

    public void setPosition(Vector3D vector3D) {
        center = vector3D;
    }

    public void setIsStationary() {
        movement = MV_STATIONARY;
    }

    public void setIsSliding() {
        movement = MV_SLIDING;
    }

    public void setIsDead() {
        inGame = false;
    }

}
