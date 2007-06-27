package com.yellowbkpk.jnova.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.yellowbkpk.jnova.client.game.JNovaController;

public class JNovaGUI extends JPanel implements Observer {

    private static final Dimension WINDOW_SIZE = new Dimension(800, 600);
    private JNovaController controller;
    private AnimatedPanel animatedPanel;
    private KeyStateHolder keyboardState;
    private Timer keyboardCheckerTimer;

    public JNovaGUI(JNovaController c) {
        super(new BorderLayout());
        controller = c;
        controller.addObserver(this);
        
        keyboardState = new KeyStateHolder();
        keyboardCheckerTimer = new Timer();
        TimerTask task  = new TimerTask() {
            public void run() {
                if (keyboardState.isKeyPressed(KeyEvent.VK_W)) {
                    controller.getShip().accelerateForward();
                } else if (keyboardState.isKeyPressed(KeyEvent.VK_S)) {
                    controller.getShip().accelerateBackward();
                }
                
                if (keyboardState.isKeyPressed(KeyEvent.VK_A)) {
                    controller.getShip().rotateLeft();
                } else if (keyboardState.isKeyPressed(KeyEvent.VK_D)) {
                    controller.getShip().rotateRight();
                }
                
                if (keyboardState.isKeyPressed(KeyEvent.VK_SPACE)) {
                    controller.recenterShip();
                }
            }
        };
        keyboardCheckerTimer.scheduleAtFixedRate(task, 0, 50);
        
        initGUI();
    }

    private void initGUI() {
        setPreferredSize(WINDOW_SIZE);
        setFocusable(true);
        
        animatedPanel = new AnimatedPanel(controller, WINDOW_SIZE);
        add(animatedPanel, BorderLayout.CENTER);

        addKeyListener(keyboardState);
    }

    public void update(Observable o, Object arg) {
        controller.update();
    }

    public void start() {
        setVisible(true);
    }

}
