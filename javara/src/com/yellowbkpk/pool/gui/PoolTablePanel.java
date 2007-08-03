package com.yellowbkpk.pool.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.yellowbkpk.pool.game.PoolGame;
import com.yellowbkpk.util.anim.AnimatedPanel;

public class PoolTablePanel extends JPanel {

    private static final Dimension MIN_DIMENSION = new Dimension(300,200);
    private PoolGame game;
    private FeltPanel felt;

    public PoolTablePanel(PoolGame g) {
        game = g;
        felt = new FeltPanel(game, MIN_DIMENSION);
        setMinimumSize(MIN_DIMENSION);
        setPreferredSize(MIN_DIMENSION);
        add(felt);
    }

}
