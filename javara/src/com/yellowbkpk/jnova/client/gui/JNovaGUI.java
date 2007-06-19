package com.yellowbkpk.jnova.client.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.yellowbkpk.jnova.client.game.JNovaController;

public class JNovaGUI implements Observer {

    private static final Dimension WINDOW_SIZE = new Dimension(800, 600);
    private JNovaController controller;
    private JFrame frame;
    private AnimatedPanel animatedPanel;

    public JNovaGUI(JNovaController c) {
        controller = c;
        controller.addObserver(this);
        
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("JNova");
        frame.setPreferredSize(WINDOW_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = new JPanel(new BorderLayout());
        
        animatedPanel = new AnimatedPanel(WINDOW_SIZE);
        contentPane.add(animatedPanel, BorderLayout.CENTER);
        
        frame.setContentPane(contentPane);
    }

    public void update(Observable o, Object arg) {
        
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

}
