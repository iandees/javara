package com.yellowbkpk.jnova.client.game;

import java.awt.Graphics;

public class Bullet extends AbstractDrawable {

    private long age;
    private boolean dead;

    public Bullet(Vector2D center, Vector2D dir, float speed) {
        super(center, dir, new Vector2D(dir.x*speed, dir.y*speed));
        age = 0;
        dead = false;
    }

    public void paint(Graphics dbg) {
        dbg.drawOval((int) (center.x-1), (int) (center.y-1), 2, 2);
    }

    public void step(long delta) {
        super.step(delta);
        age += delta;
        
        if(velocity.length() < 0.5) {
            dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }

}
