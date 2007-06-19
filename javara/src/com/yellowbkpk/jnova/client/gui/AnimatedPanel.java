package com.yellowbkpk.jnova.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import com.yellowbkpk.jnova.client.game.Drawable;
import com.yellowbkpk.jnova.client.game.JNovaController;

public class AnimatedPanel extends JPanel implements Runnable {

    private Dimension size;

    private Thread animator;

    private boolean running;

    private Image dbImage;

    private Graphics dbg;

    private JNovaController controller;

    public AnimatedPanel(JNovaController c, Dimension dimension) {
        controller = c;
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(size);
        }
        
        dbg = dbImage.getGraphics();

        dbg.setColor(Color.white);
        dbg.fillRect(0, 0, size.width, size.height);
        
        drawField(dbg);
    }

    private void drawField(Graphics dbg) {
        List<Drawable> drawables = controller.getDrawables();
        
        for (Drawable drawable : drawables) {
            drawable.paint(dbg);
        }
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }

    private void gameUpdate() {
        controller.update();
    }

}
