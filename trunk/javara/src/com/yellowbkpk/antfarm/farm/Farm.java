package com.yellowbkpk.antfarm.farm;

import java.util.ArrayList;

import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import com.yellowbkpk.antfarm.creatures.Ant;
import com.yellowbkpk.antfarm.creatures.scent.Scent;

public class Farm {
    /** The size of the farm. */
    private Vector3d size;
    
    private ArrayList<Ant> ants;

    private ArrayList<Scent> scents;
    
    public Farm() {
        this(100,100,1);
    }
    
    public Farm(int x, int y, int z) {
        this(new Vector3d(x,y,z));
    }

    public Farm(Vector3d vector3d) {
        size = vector3d;
        ants = new ArrayList<Ant>();
        scents = new ArrayList<Scent>();
    }

    public void addAnt(Ant ant) {
        ants.add(ant);
        System.err.println("Added ant " + ant);
    }
    
    public Vector3d getSize() {
        return size;
    }

    public void tick() {
        for (Ant ant : ants) {
            ant.tick();
        }
        
        for (Scent scent : scents) {
            scent.tick();
        }
        
        for (int i = 0; i < ants.size(); i++) {
            if(ants.get(i).getHealth() < 1) {
                ants.remove(i);
            }
        }
        
        for (int i = 0; i < scents.size(); i++) {
            if(scents.get(i).getStrength() < 1) {
                scents.remove(i);
            }
        }
    }
    
    public ArrayList<Ant> getAnts() {
        return ants;
    }

    public ArrayList<Scent> getScents() {
        return scents;
    }

    public void dropScent(Ant ant, Scent sc) {
        scents.add(sc);
    }

    public Scent getNearestScentTo(Ant ant) {
        double antDistFromOrigin = ant.getLocation().length();
        double shortestDistance = 100.0;
        Scent closestScent = null;
        
        for (Scent scent : scents) {
            double distance = Math.abs(antDistFromOrigin - scent.getLocation().length());
            
            if(distance < shortestDistance) {
                closestScent = scent;
            }
        }
        
        return closestScent;
    }

    /**
     * @param loc
     * @return True if the location is inside of this farm's bounds.
     */
    public boolean checkBounds(Tuple3d loc) {
        return (loc.x >= 0 && loc.x <= size.x) 
        && (loc.y >= 0 && loc.y <= size.y) 
        && (loc.z >= 0 && loc.z <= size.z);
    }
}
