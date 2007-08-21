package com.yellowbkpk.antfarm.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.yellowbkpk.antfarm.game.AntGame;
import com.yellowbkpk.antfarm.game.TickListener;

public class GameDisplay2D implements GameDisplay, TickListener {
    private AntGame game;
    private JFrame window;
    private AntDrawPanel antDrawer;
    
    public GameDisplay2D(AntGame g) {
        game = g;
        game.addTickListener(this);
        
        init();
    }

    private void init() {
        window = new JFrame("Ant Farm");
        window.setSize(new Dimension(500,400));
        window.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                stop();
            }
            
        });
        
        Container c = window.getContentPane();
        antDrawer = new AntDrawPanel(game);
        c.add(antDrawer);
    }

    public void start() {
        game.start();
        window.setVisible(true);
    }

    public void stop() {
        game.stop();
        window.setVisible(false);
        System.exit(0);
    }

    public void tickHappened() {
        antDrawer.tick();
    }
}
