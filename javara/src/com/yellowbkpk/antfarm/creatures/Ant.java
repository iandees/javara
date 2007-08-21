package com.yellowbkpk.antfarm.creatures;


import java.util.Random;

import javax.vecmath.Vector3d;

import com.yellowbkpk.antfarm.creatures.scent.Scent;
import com.yellowbkpk.antfarm.creatures.scent.ScentType;
import com.yellowbkpk.antfarm.farm.Farm;

public class Ant {
    /** The maximum health value for the ant. */
    private static final int MAX_HEALTH = 100;

    private static final double MOVEMENT_HEALTH_CONSTANT = 0.98;

    private static final double HALF_PI = Math.PI / 2.0;

    /** The ant's location in 3D space. */
    private Vector3d location;
    
    /** The ant's current speed and direction. */
    private Vector3d velocity;
    
    /** The ant's health points. */
    private double health;

    private Farm farm;
    
    /**
     * Complete Constructor.
     * 
     * @param loc The initial location of the ant.
     * @param vel The initial velocity (speed and direction) of the ant.
     * @param healthVal The initial health value for the ant.
     */
    public Ant(Farm f, Vector3d loc, Vector3d vel, int healthVal) {
        farm = f;
        location = loc;
        velocity = vel;
        health = healthVal;
    }
    
    /**
     * Blank constructor.
     * 
     * Creates a default ant that doesn't move and starts out at the origin.
     */
    public Ant(Farm f) {
        this(f, new Vector3d(0,0,0), new Vector3d(0,0,0), MAX_HEALTH);
    }
    
    /**
     * Incomplete constructor.
     * 
     * Creates an ant at the specified location.
     * 
     * @param location The location for the ant to start at.
     */
    public Ant(Farm f, Vector3d location) {
        this(f, location, new Vector3d(0,0,0), MAX_HEALTH);
    }

    public double getHealth() {
        return health;
    }

    public Vector3d getLocation() {
        return location;
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public void tick() {
        reaim();
        dropScent();
        move();
    }

    private void dropScent() {
        farm.dropScent(this, new Scent(new Vector3d(getLocation()), ScentType.TRAIL));
    }

    private void reaim() {
        Random r = new Random();
        Scent nearest = farm.getNearestScentTo(this);
        if(nearest == null) {
            velocity = new Vector3d(2.0 * (r.nextDouble()) - 1.0, 2.0 * (r.nextDouble()) - 1.0, 0);
            return;
        }
        
        Vector3d closestScentLoc = nearest.getLocation();
        Vector3d newVelocity = null;
        Vector3d newLocation = new Vector3d();
        double angle = 0;
        boolean angleIsBad = true;
        boolean boundsAreBad = true;

        do {
            angleIsBad = true;
            boundsAreBad = true;
            newLocation = new Vector3d();

            Vector3d diff = new Vector3d();
            diff.sub(closestScentLoc, getLocation());

            newVelocity = new Vector3d(2.0 * (r.nextDouble()) - 1.0, 2.0 * (r.nextDouble()) - 1.0, 0);
            if(diff.length() == 0) {
                break;
            }
            angle = diff.angle(newVelocity);
            newLocation.add(getLocation(), newVelocity);

            if(angle > (HALF_PI)) {
                angleIsBad = false;
            }

            if(farm.checkBounds(newLocation)) {
                boundsAreBad = false;
            }

            //System.err.println(newLocation + "\t" + Math.toDegrees(angle) + "\tB: " + boundsAreBad + "\tA: " + angleIsBad);
        } while (angleIsBad || boundsAreBad);

        velocity = newVelocity;
    }

    private void move() {
        location.add(velocity);
        health *= MOVEMENT_HEALTH_CONSTANT;
    }
}
