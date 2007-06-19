package com.yellowbkpk.jnova.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class AnimatedPanel extends JPanel implements Runnable {

    private Dimension size;

    private Thread animator;

    private boolean running;

    private Image dbImage;

    private Graphics dbg;

    public AnimatedPanel(Dimension dimension) {
        size = dimension;
        setPreferredSize(size);
    }

    public void addNotify() {
        super.addNotify();
        startGame();
    }

    private void startGame() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void stopGame() {
        running = false;
    }

    public void run() {
        running = true;

        while (running) {
            gameUpdate();
            gameRender();
            repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }

    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(size);
        } else {
            dbg = dbImage.getGraphics();
        }

        dbg.setColor(Color.white);
        dbg.fillRect(0, 0, size.width, size.height);
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }

    private void gameUpdate() {

    }

}
