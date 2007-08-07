package com.yellowbkpk.util.anim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;


public class AnimatedPanel extends JPanel implements Runnable {

    private Dimension size;

    private Thread animator;

    private boolean running;

    private Image dbImage;

    private Graphics dbg;

    private ControllerIF controller;

    private long prevTime;

    public static final long TIME_PER_FRAME = 10;

    public AnimatedPanel(ControllerIF c, Dimension dimension) {
        controller = c;
        size = dimension;
        prevTime = System.currentTimeMillis();
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
                Thread.sleep(TIME_PER_FRAME);
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
        List<? extends AbstractDrawable> drawables = controller.getDrawables();
        
        for (DrawableIF drawable : drawables) {
            drawable.paint(dbg);
        }
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }

    private void gameUpdate() {
        long deltaTime = System.currentTimeMillis() - prevTime;
        controller.update(deltaTime / TIME_PER_FRAME);
        prevTime = System.currentTimeMillis();
    }

}
