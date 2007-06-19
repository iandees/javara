package com.yellowbkpk.jnova.client.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class JNovaController extends Observable {

    private List<Drawable> drawables;
    
    public JNovaController() {
        drawables = new ArrayList<Drawable>();
    }
    
    public void addItem(Point point) {
        Vector2D loc = new Vector2D(point.x, point.y);
        drawables.add(new AbstractDrawable(loc, new Vector2D(3, 3), new Vector2D(2, 2)));
    }

    public void update() {
        for (Drawable drawable : drawables) {
            drawable.step();
        }
    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

}
