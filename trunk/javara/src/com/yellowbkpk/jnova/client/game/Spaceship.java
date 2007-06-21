package com.yellowbkpk.jnova.client.game;



public class Spaceship extends AbstractDrawable {

    private static final Vector2D ORIGIN = new Vector2D(0f, 0f);
    private final static float ROT_CONSTANT = 0.2f;
    private static final float ACC_CONSTANT = 0.2f;
    private Vector2D shipAimDirection;
    
    public Spaceship(Vector2D initialLocation) {
        super(initialLocation);
        shipAimDirection = new Vector2D(1f, 0f);
    }

    public void rotateLeft() {
        acceleration = acceleration.add(acceleration.y*ROT_CONSTANT, -acceleration.x*ROT_CONSTANT);
    }

    public void accelerateForward() {
        acceleration = acceleration.add(shipAimDirection.multiply(ACC_CONSTANT, ACC_CONSTANT));
    }

    public void rotateRight() {
        acceleration = acceleration.add(-acceleration.y*ROT_CONSTANT, acceleration.x*ROT_CONSTANT);
    }

    public void accelerateBackward() {
        acceleration = acceleration.subtract(shipAimDirection.multiply(ACC_CONSTANT, ACC_CONSTANT));
    }

}
