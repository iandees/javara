package com.yellowbkpk.jnova.client.game;

public class Vector2D {

    public float x;
    public float y;

    public Vector2D(float xComp, float yComp) {
        x = xComp;
        y = yComp;
    }

    public Vector2D add(float xComp, float yComp) {
        return new Vector2D(x + xComp, y + yComp);
    }
    
    public Vector2D subtract(float xComp, float yComp) {
        return new Vector2D(x - xComp, y - yComp);
    }

    public String toString() {
        return "("+x+","+y+")";
    }

    public Vector2D multiply(float xComp, float yComp) {
        return new Vector2D(x * xComp, y * yComp);
    }

}
