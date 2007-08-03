package com.yellowbkpk.pool.game;

import java.awt.Color;
import java.awt.Graphics;

import com.yellowbkpk.util.anim.AnimatedPanel;
import com.yellowbkpk.util.anim.DrawableIF;
import com.yellowbkpk.util.vecmath.Vector2D;

public class Ball implements DrawableIF {

    private static final float MAX_SPEED = 5;
    private static final float DAMP = 0.95f;
    private int radius;
    private int diam;
    private Vector2D loc;
    private Vector2D vel;
    private Vector2D acc;
    
    public Ball(int xLoc, int yLoc) {
        this(xLoc, yLoc, 6);
    }

    public Ball(int xLoc, int yLoc, int rad) {
        this(new Vector2D(xLoc, yLoc), new Vector2D(0, 0), new Vector2D(0, 0), rad);
    }
    
    public Ball(Vector2D location, Vector2D velocity, Vector2D acceleration, int rad) {
        radius = rad;
        diam = rad * 2;
        loc = location;
        vel = velocity;
        acc = acceleration;
    }

    public void paint(Graphics dbg) {
        dbg.setColor(Color.black);
        dbg.drawOval((int) loc.x - radius, (int) loc.y - radius, diam, diam);
    }

    public void step(long delta) {
        float scaleRelToTimeDelta = delta / AnimatedPanel.TIME_PER_FRAME;

        vel = vel.add(acc.scale(scaleRelToTimeDelta));
        
        if(vel.length() > MAX_SPEED) {
            vel = vel.scale(0.9f);
        }
        
        loc = loc.add(vel.scale(scaleRelToTimeDelta));
        vel = vel.scale(DAMP);
        acc = acc.scale(DAMP);
    }

}
