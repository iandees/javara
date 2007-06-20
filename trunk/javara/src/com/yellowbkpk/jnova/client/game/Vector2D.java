package com.yellowbkpk.jnova.client.game;

public class Vector2D {

    private static final Vector2D ORIGIN = new Vector2D(0f, 0f);
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

    public void rotateAround(Vector2D origin, float deg) {
        Vector2D shifted = new Vector2D(this.x - origin.x, this.y - origin.y);
        x = (float) (Math.cos(deg) * shifted.x - Math.sin(deg) * shifted.y);
        y = (float) (Math.cos(deg) * shifted.y + Math.sin(deg) * shifted.x);
    }

    public void scale(float scaleValue) {
        x *= scaleValue;
        y *= scaleValue;
    }
    
    public float length() {
        return (float) Math.sqrt((x*x)+(y*y));
    }

    public void normalize() {
        float l = length();
        x = x / l;
        y = y / l;
    }

    public Vector2D multiply(Vector2D other) {
        return this.multiply(other.x, other.y);
    }

    public Vector2D add(Vector2D other) {
        return this.add(other.x, other.y);
    }

}
