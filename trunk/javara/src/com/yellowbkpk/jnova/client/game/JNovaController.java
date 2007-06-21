package com.yellowbkpk.jnova.client.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class JNovaController extends Observable {

    private static final Vector2D CENTER = new Vector2D(100f, 100f);
    private List<Drawable> drawables;
    private Spaceship spaceship;
    private long prevTime;
    
    public JNovaController() {
        drawables = new ArrayList<Drawable>();
        spaceship = new Spaceship(CENTER);
        drawables.add(spaceship);
        prevTime = System.currentTimeMillis();
    }
    
    public synchronized void addItem(Point point) {
    }

    public synchronized void update() {
        for (Drawable drawable : drawables) {
            long delta = System.currentTimeMillis() - prevTime;
            drawable.step(delta);
        }
        
        prevTime = System.currentTimeMillis();
    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public Spaceship getShip() {
        return spaceship;
    }

    public void recenterShip() {
        spaceship.setCenter(CENTER);
    }

}
