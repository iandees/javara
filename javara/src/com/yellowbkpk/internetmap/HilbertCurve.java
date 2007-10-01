package com.yellowbkpk.internetmap;
import java.applet.Applet;
import java.awt.Graphics;

public class HilbertCurve extends Applet {
    private SimpleGraphics sg = null;
    private int level = 4;
    private int dist0 = 512;
    private int dist = dist0;

    public void init() {
        sg = new SimpleGraphics(getGraphics());
        resize(dist0, dist0);
    }

    public void paint(Graphics g) {
        dist = dist0;
        for (int i = level; i > 0; i--) {
            dist /= 2;
        }
        sg.goToXY(dist / 2, dist / 2);
        HilbertA(level); // start recursion
    }

    private void HilbertA(int level) {
        if (level > 0) {
            HilbertB(level - 1);
            sg.lineRel(0, dist);
            HilbertA(level - 1);
            sg.lineRel(dist, 0);
            HilbertA(level - 1);
            sg.lineRel(0, -dist);
            HilbertC(level - 1);
        }
    }

    private void HilbertB(int level) {
        if (level > 0) {
            HilbertA(level - 1);
            sg.lineRel(dist, 0);
            HilbertB(level - 1);
            sg.lineRel(0, dist);
            HilbertB(level - 1);
            sg.lineRel(-dist, 0);
            HilbertD(level - 1);
        }
    }

    private void HilbertC(int level) {
        if (level > 0) {
            HilbertD(level - 1);
            sg.lineRel(-dist, 0);
            HilbertC(level - 1);
            sg.lineRel(0, -dist);
            HilbertC(level - 1);
            sg.lineRel(dist, 0);
            HilbertA(level - 1);
        }
    }

    private void HilbertD(int level) {
        if (level > 0) {
            HilbertC(level - 1);
            sg.lineRel(0, -dist);
            HilbertD(level - 1);
            sg.lineRel(-dist, 0);
            HilbertD(level - 1);
            sg.lineRel(0, dist);
            HilbertB(level - 1);
        }
    }
}

class SimpleGraphics {
    private Graphics g = null;
    private int x = 0, y = 0;
    private int step = 1;

    public SimpleGraphics(Graphics g) {
        this.g = g;
    }

    public void goToXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lineRel(int deltaX, int deltaY) {
        g.drawLine(x, y, x + deltaX, y + deltaY);
        System.err.print(step + "\t" + x + "\t" + y + "\t");
        
        if(deltaX < 0) {
            System.err.println("R");
        } else if(deltaX > 0) {
            System.err.println("L");
        }
        
        if(deltaY < 0) {
            System.err.println("U");
        } else if(deltaY > 0) {
            System.err.println("D");
        }
        
        x += deltaX;
        y += deltaY;
        step++;
    }
}