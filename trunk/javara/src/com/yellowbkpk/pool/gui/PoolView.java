package com.yellowbkpk.pool.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.yellowbkpk.pool.game.PoolGame;

public class PoolView {

    private PoolGame game;
    private JFrame frame;

    public PoolView(PoolGame g) {
        game = g;
        
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("Pool");
        JPanel content = new JPanel(new BorderLayout());
        
        PoolTablePanel tableView = new PoolTablePanel(game);
        content.add(tableView);
        
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    public void start() {
        frame.setVisible(true);
    }

}
