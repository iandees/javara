package com.yellowbkpk.maps.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.yellowbkpk.maps.map.Map;

public class MapDisplayPanel extends JPanel implements Runnable {

    private static final long TIME_PER_FRAME = 10;
    private static final Dimension SIZE = new Dimension(800, 600);
    private Map map;
    private Image dbImage;
    private Thread animator;
    private boolean running;
    private Graphics dbg;
    private Point c;
    protected Point mousedownPoint;
    protected Point originalC;

    public MapDisplayPanel(Map m) {
        setPreferredSize(new Dimension(800,600));
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mousedownPoint = e.getPoint();
                originalC = c;
            }

            public void mouseReleased(MouseEvent e) {
                int dx = e.getX() - mousedownPoint.x;
                int dy = e.getY() - mousedownPoint.y;
                applyDrag(dx,dy);
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousedownPoint.x;
                int dy = e.getY() - mousedownPoint.y;
                Point newPoint = new Point(originalC.x + dx, originalC.y + dy);
                dragTo(newPoint);
            }
        });
        
        c = new Point(50,50);
        map = m;
    }

    protected void applyDrag(int dx, int dy) {
        // TODO Auto-generated method stub
        
    }

    protected void dragTo(Point p) {
        c = p;
    }

    public void addNotify() {
        super.addNotify();
        startAnimation();
    }

    private void startAnimation() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void stopAnimation() {
        running = false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    public void run() {
        running = true;

        while (running) {
            mapUpdate();
            mapRender();
            repaint();

            try {
                Thread.sleep(TIME_PER_FRAME);
            } catch (InterruptedException e) {
            }
        }
    }

    private void mapRender() {
        if (dbImage == null) {
            dbImage = createImage(SIZE);
        }
        
        dbg = dbImage.getGraphics();

        dbg.setColor(Color.gray);
        dbg.fillRect(0, 0, SIZE.width, SIZE.height);
        
        drawField(dbg);
    }

    private void drawField(Graphics dbg2) {
        dbg2.setColor(Color.red);
        dbg2.drawOval(c.x, c.y, 2, 2);
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }

    private void mapUpdate() {
        map.update();
    }
}
