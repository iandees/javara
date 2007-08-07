package com.yellowbkpk.pool.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.yellowbkpk.pool.game.Ball;
import com.yellowbkpk.pool.game.PoolGame;
import com.yellowbkpk.util.anim.AnimatedPanel;
import com.yellowbkpk.util.vecmath.Vector3D;

public class FeltPanel extends AnimatedPanel {

    protected Random r;

    public FeltPanel(final PoolGame c, Dimension dimension) {
        super(c, dimension);
        
        r = new Random();
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Vector3D loc = new Vector3D(e.getX(), e.getY(), 0);
                Vector3D vel = new Vector3D(r.nextInt(4) - 2, r.nextInt(4) - 2, 0);
                Vector3D acc = new Vector3D(r.nextInt(4) - 2, r.nextInt(4) - 2, 0);
                c.addBall(new Ball(loc, vel, acc, 5));
            }
        });
    }
    
}
