package com.yellowbkpk.antfarm.creatures.scent;

import javax.vecmath.Vector3d;

public class Scent {
    private static final int MAX_STRENGTH = 100;
    private double strength;
    private Vector3d location;
    private ScentType type;
    
    public Scent(double str, Vector3d loc, ScentType typ) {
        strength = str;
        location = loc;
        type = typ;
    }
    
    public Scent(Vector3d loc, ScentType typ) {
        this(MAX_STRENGTH, loc, typ);
    }
    
    public double getStrength() {
        return strength;
    }
    
    public Vector3d getLocation() {
        return location;
    }
    
    public ScentType getType() {
        return type;
    }

    public void tick() {
        strength *= ScentSustainMap.getSustain(type);
    }
}
