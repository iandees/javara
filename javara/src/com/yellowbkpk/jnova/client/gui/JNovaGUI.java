package com.yellowbkpk.jnova.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.yellowbkpk.jnova.client.game.JNovaController;

public class JNovaGUI extends JPanel implements Observer {

    private static final Dimension WINDOW_SIZE = new Dimension(800, 600);
    private JNovaController controller;
    private AnimatedPanel animatedPanel;

    public JNovaGUI(JNovaController c) {
        super(new BorderLayout());
        controller = c;
        controller.addObserver(this);
        
        initGUI();
    }

    private void initGUI() {
        setPreferredSize(WINDOW_SIZE);
        setFocusable(true);
        
        animatedPanel = new AnimatedPanel(controller, WINDOW_SIZE);
        add(animatedPanel, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(final KeyEvent e) {
                if (KeyEvent.VK_W == e.getKeyCode()) {
                    controller.getShip().accelerateForward();
                } else if (KeyEvent.VK_S == e.getKeyCode()) {
                    controller.getShip().accelerateBackward();
                } else if (KeyEvent.VK_A == e.getKeyCode()) {
                    controller.getShip().rotateLeft();
                } else if (KeyEvent.VK_D == e.getKeyCode()) {
                    controller.getShip().rotateRight();
                } else if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                    controller.recenterShip();
                }
                e.consume();
            }
        });
    }

    public void update(Observable o, Object arg) {
        controller.update();
    }

    public void start() {
        setVisible(true);
    }

}
