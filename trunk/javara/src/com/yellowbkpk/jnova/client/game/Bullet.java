package com.yellowbkpk.jnova.client.game;

import java.awt.Graphics;

import com.yellowbkpk.util.anim.AbstractDrawable;
import com.yellowbkpk.util.vecmath.Vector3D;

public class Bullet extends AbstractDrawable {

    private long age;
    private boolean dead;

    public Bullet(Vector3D center, Vector3D dir, float speed) {
        super(center, dir, new Vector3D(dir.x*speed, dir.y*speed, 0));
        age = 0;
        dead = false;
    }

    public void paint(Graphics dbg) {
        dbg.drawOval((int) (center.x-1), (int) (center.y-1), 2, 2);
    }

    public void step(float delta) {
        super.step(delta);
        age += delta;
        
        if(linearVelocity.length() < 0.5) {
            dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }

}
