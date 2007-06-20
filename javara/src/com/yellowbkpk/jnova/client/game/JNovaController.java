package com.yellowbkpk.jnova.client.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class JNovaController extends Observable {

    private List<Drawable> drawables;
    private Spaceship spaceship;
    
    public JNovaController() {
        drawables = new ArrayList<Drawable>();
        spaceship = new Spaceship(new Vector2D(100f, 100f));
        drawables.add(spaceship);
    }
    
    public synchronized void addItem(Point point) {
        Vector2D loc = new Vector2D(point.x, point.y);
        drawables.add(new AbstractDrawable(loc, new Vector2D(3, 3), new Vector2D(2, 2)));
    }

    public synchronized void update() {
        for (Drawable drawable : drawables) {
            drawable.step();
        }
    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public Spaceship getShip() {
        return spaceship;
    }

}
