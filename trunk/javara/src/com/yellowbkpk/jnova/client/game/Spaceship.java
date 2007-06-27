package com.yellowbkpk.jnova.client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Spaceship extends AbstractDrawable {

    private static final int SHIP_DIAM = 16;
    private static final int SHIP_RADIUS = SHIP_DIAM / 2;
    private final static float ROT_CONSTANT = 0.2f;
    private static final float ACC_CONSTANT = 0.2f;
    private Vector2D shipAimDirection;
    private List<Bullet> bullets;
    
    public Spaceship(Vector2D initialLocation) {
        super(initialLocation);
        bullets = new ArrayList<Bullet>();
        shipAimDirection = new Vector2D(1f, 0f);
    }

    public void rotateLeft() {
        shipAimDirection = shipAimDirection.add(shipAimDirection.y*ROT_CONSTANT, -shipAimDirection.x*ROT_CONSTANT);
        shipAimDirection.normalize();
    }

    public void accelerateForward() {
        acceleration = acceleration.add(shipAimDirection.multiply(ACC_CONSTANT, ACC_CONSTANT));
    }

    public void rotateRight() {
        shipAimDirection = shipAimDirection.add(-shipAimDirection.y*ROT_CONSTANT, shipAimDirection.x*ROT_CONSTANT);
        shipAimDirection.normalize();
    }

    public void accelerateBackward() {
        acceleration = acceleration.subtract(shipAimDirection.multiply(ACC_CONSTANT, ACC_CONSTANT));
    }

    public void paint(Graphics dbg) {
        dbg.setColor(Color.white);
        dbg.fillOval((int) center.x-SHIP_RADIUS, (int) center.y-SHIP_RADIUS, SHIP_DIAM, SHIP_DIAM);
        dbg.setColor(Color.red);
        dbg.drawLine((int) center.x, (int) center.y, (int) (center.x+(shipAimDirection.x*10)), (int) (center.y+(shipAimDirection.y*10)));
        dbg.setColor(Color.blue);
        dbg.drawLine((int) center.x, (int) center.y, (int) (center.x+(velocity.x*10)), (int) (center.y+(velocity.y*10)));
        dbg.setColor(Color.green);
        dbg.drawLine((int) (center.x+(velocity.x*10)), (int) (center.y+(velocity.y*10)), (int) (center.x+(velocity.x*10)+(acceleration.x*10)), (int) (center.y+(velocity.y*10)+(acceleration.y*10)));
        
        synchronized(bullets) {
        for (Bullet b : bullets) {
            b.paint(dbg);
        }
        }
    }

    public void setCenter(Vector2D newCenter) {
        center = newCenter;
    }

    public void shoot() {
        
        synchronized (bullets) {
            Bullet b = new Bullet(center, shipAimDirection, 1.0f);
            bullets.add(b);
        }        
    }

    public void step(long delta) {
        super.step(delta);

        synchronized (bullets) {
            for(int i = 0; i < bullets.size(); i++) {
                if(bullets.get(i).isDead()) {
                    bullets.remove(i);
                }
            }
            
            for (Bullet b : bullets) {
                b.step(delta);
            }
        }
    }

}
