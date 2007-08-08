package com.yellowbkpk.pool.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import com.yellowbkpk.pool.game.Ball;
import com.yellowbkpk.pool.game.PoolGame;
import com.yellowbkpk.util.anim.AnimatedPanel;
import com.yellowbkpk.util.vecmath.Vector3D;

public class FeltPanel extends AnimatedPanel {

    protected static final float SCALE_RATIO = 2f;
    protected static final float BALL_MASS = 0.5f;
    protected static final float IMPULSE_TIME = 0.005f;
    protected static final float SPRING_CONSTANT = 50f;
    protected Random r;

    public FeltPanel(final PoolGame c, Dimension dimension) {
        super(c, dimension);
        
        r = new Random();
        
        addMouseListener(new MouseAdapter() {
            private Vector3D loc;

            public void mousePressed(MouseEvent e) {
                loc = new Vector3D(e.getX(), e.getY(), 0);
            }

            public void mouseReleased(MouseEvent e) {
                Vector3D acc = new Vector3D(0,0,0);
                Vector3D vel = loc.subtract(new Vector3D(e.getX(), e.getY(), 0f)).scale(-SPRING_CONSTANT * IMPULSE_TIME / BALL_MASS / SCALE_RATIO).negative();
                c.addBall(new Ball(loc, vel , acc , 5));
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                
            }
        });
    }
    
}
