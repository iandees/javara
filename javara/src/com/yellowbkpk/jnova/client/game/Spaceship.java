package com.yellowbkpk.jnova.client.game;



public class Spaceship extends AbstractDrawable {

    private static final Vector2D ORIGIN = new Vector2D(0f, 0f);
    private final static float ROT_CONSTANT = 0.2f;
    private static final float ACC_CONSTANT = 2.2f;
    
    public Spaceship(Vector2D initialLocation) {
        super(initialLocation);
    }

    public void rotateLeft() {
        acceleration.rotateAround(ORIGIN, -ROT_CONSTANT);
    }

    public void accelerateForward() {
        acceleration.scale(ACC_CONSTANT);
    }

    public void rotateRight() {
        acceleration.rotateAround(ORIGIN, ROT_CONSTANT);
    }

    public void accelerateBackward() {
        acceleration.scale(-ACC_CONSTANT);
    }

}
