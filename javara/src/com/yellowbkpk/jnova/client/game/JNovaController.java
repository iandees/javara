package com.yellowbkpk.jnova.client.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.yellowbkpk.util.anim.AbstractDrawable;
import com.yellowbkpk.util.anim.ControllerIF;
import com.yellowbkpk.util.anim.DrawableIF;
import com.yellowbkpk.util.vecmath.Vector3D;

public class JNovaController extends Observable implements ControllerIF {

    private static final Vector3D CENTER = new Vector3D(100f, 100f, 0f);
    private List<AbstractDrawable> drawables;
    private Spaceship spaceship;
    private long prevTime;
    
    private JNovaClientThread networkClient;
    private ConcurrentLinkedQueue<ServerClientMessage> pipe;
    
    public JNovaController() {
        drawables = new ArrayList<AbstractDrawable>();
        spaceship = new Spaceship(CENTER);
        drawables.add(spaceship);
        pipe = new ConcurrentLinkedQueue<ServerClientMessage>();
        prevTime = System.currentTimeMillis();
    }
    
    public synchronized void addItem(Point point) {
    }

    public synchronized void update(float delta) {
        for (DrawableIF drawable : drawables) {
            drawable.step(delta);
        }
    }

    public List<AbstractDrawable> getDrawables() {
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
