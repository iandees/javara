package com.yellowbkpk.jnova.client.game;

import java.awt.Color;
import java.awt.Graphics;



public class Spaceship extends AbstractDrawable {

    private static final int SHIP_DIAM = 16;
    private static final int SHIP_RADIUS = SHIP_DIAM / 2;
    private final static float ROT_CONSTANT = 0.2f;
    private static final float ACC_CONSTANT = 0.2f;
    private Vector2D shipAimDirection;
    
    public Spaceship(Vector2D initialLocation) {
        super(initialLocation);
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
        dbg.setColor(Color.black);
        dbg.fillOval((int) center.x-SHIP_RADIUS, (int) center.y-SHIP_RADIUS, SHIP_DIAM, SHIP_DIAM);
        dbg.setColor(Color.red);
        dbg.drawLine((int) center.x, (int) center.y, (int) (center.x+(shipAimDirection.x*10)), (int) (center.y+(shipAimDirection.y*10)));
        dbg.setColor(Color.blue);
        dbg.drawLine((int) center.x, (int) center.y, (int) (center.x+(velocity.x*10)), (int) (center.y+(velocity.y*10)));
    }

}
