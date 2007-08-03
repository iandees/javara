package com.yellowbkpk.pool.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.yellowbkpk.pool.game.Ball;
import com.yellowbkpk.pool.game.PoolGame;
import com.yellowbkpk.util.anim.AnimatedPanel;
import com.yellowbkpk.util.vecmath.Vector2D;

public class FeltPanel extends AnimatedPanel {

    protected Random r;

    public FeltPanel(final PoolGame c, Dimension dimension) {
        super(c, dimension);
        
        r = new Random();
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Vector2D loc = new Vector2D(e.getX(), e.getY());
                Vector2D vel = new Vector2D(r.nextInt(4) - 2, r.nextInt(4) - 2);
                Vector2D acc = new Vector2D(r.nextInt(4) - 2, r.nextInt(4) - 2);
                c.addBall(new Ball(loc, vel, acc, 5));
            }
        });
    }
    
}
