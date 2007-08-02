package com.yellowbkpk.pool.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.yellowbkpk.pool.game.PoolGame;

public class PoolTablePanel extends JPanel {

    private static final Dimension MIN_DIMENSION = new Dimension(300,200);
    private PoolGame game;

    public PoolTablePanel(PoolGame g) {
        game = g;
        setMinimumSize(MIN_DIMENSION);
        setPreferredSize(MIN_DIMENSION);
    }

}
