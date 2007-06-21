package com.yellowbkpk.jnova.client.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
        JPanel contentPane = new JPanel(new BorderLayout());
        
        contentPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
            }
        });
        
        frame.addKeyListener(new KeyAdapter() {
            
            public void keyPressed(final KeyEvent e) {

                if (KeyEvent.VK_W == e.getKeyCode()) {
                    controller.getShip().accelerateForward();
                } else if (KeyEvent.VK_S == e.getKeyCode()) {
                    controller.getShip().accelerateBackward();
                } else if (KeyEvent.VK_A == e.getKeyCode()) {
                    controller.getShip().rotateLeft();
                } else if (KeyEvent.VK_D == e.getKeyCode()) {
                    controller.getShip().rotateRight();
                }
            }

        });
        
        animatedPanel = new AnimatedPanel(controller, WINDOW_SIZE);
        contentPane.add(animatedPanel, BorderLayout.CENTER);
        
        frame.setContentPane(contentPane);
    }

    public void update(Observable o, Object arg) {
        controller.update();
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

}
