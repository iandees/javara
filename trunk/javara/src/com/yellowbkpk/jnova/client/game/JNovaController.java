package com.yellowbkpk.jnova.client.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.yellowbkpk.util.anim.ControllerIF;
import com.yellowbkpk.util.anim.Drawable;
import com.yellowbkpk.util.vecmath.Vector2D;

public class JNovaController extends Observable implements ControllerIF {

    private static final Vector2D CENTER = new Vector2D(100f, 100f);
    private List<Drawable> drawables;
    private Spaceship spaceship;
    private long prevTime;
    
    private JNovaClientThread networkClient;
    private ConcurrentLinkedQueue<ServerClientMessage> pipe;
    
    public JNovaController() {
        drawables = new ArrayList<Drawable>();
        spaceship = new Spaceship(CENTER);
        drawables.add(spaceship);
        pipe = new ConcurrentLinkedQueue<ServerClientMessage>();
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
    
    public void connectTo(String address) {
        networkClient = new JNovaClientThread(address, pipe);
        new Thread(networkClient).start();
    }

    public void disconnectFromServer() {
        networkClient.shutdown();
    }

}
