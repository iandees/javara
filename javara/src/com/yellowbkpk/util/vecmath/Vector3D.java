package com.yellowbkpk.util.vecmath;

public class Vector3D {

    private static final Vector3D ORIGIN = new Vector3D(0f, 0f, 0f);
    public float x;
    public float y;
    public float z;

    public Vector3D(float xComp, float yComp, float zComp) {
        x = xComp;
        y = yComp;
        z = zComp;
    }

    public Vector3D add(float xComp, float yComp, float zComp) {
        return new Vector3D(x + xComp, y + yComp, z + zComp);
    }
    
    public Vector3D subtract(float xComp, float yComp, float zComp) {
        return new Vector3D(x - xComp, y - yComp, z - zComp);
    }

    public String toString() {
        return "("+x+","+y+","+z+")";
    }

    public Vector3D multiply(float xComp, float yComp, float zComp) {
        return new Vector3D(x * xComp, y * yComp, z * zComp);
    }

    public Vector3D scale(float scaleValue) {
        return new Vector3D(x * scaleValue, y * scaleValue, z * scaleValue);
    }
    
    public float length() {
        return (float) Math.sqrt((x*x)+(y*y)+(z*z));
    }

    public Vector3D normalize() {
        float len = length();
        return new Vector3D(x / len, y / len, z / len);
    }

    public Vector3D multiply(Vector3D other) {
        return this.multiply(other.x, other.y, other.z);
    }

    public Vector3D add(Vector3D other) {
        return this.add(other.x, other.y, other.z);
    }

    public Vector3D subtract(Vector3D other) {
        return this.subtract(other.x, other.y, other.z);
    }

    public Vector3D cross(Vector3D other) {
        float newX = y * other.z - other.y * z;
        float newY = z * other.x - other.z * x;
        float newZ = x * other.y - other.x * y;
        return new Vector3D(newX, newY, newZ);
    }

    public float dot(Vector3D other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector3D negative() {
        return new Vector3D(-x, -y, -z);
    }

}
