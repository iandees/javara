package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.yellowbkpk.algebracircuit.Circuit;
import com.yellowbkpk.algebracircuit.CircuitsEnum;

public abstract class BaseCircuit implements Circuit {

    protected static final int RADIUS = 50;
    protected Point center;
    private CircuitsEnum type;
    private Color fgColor;
    private Color bgColor;
    private Color txColor;
    private String label;
    protected Circuit[] inputs;
    protected Circuit output;
    private int nInputs;
    private int maxInputs;

    BaseCircuit(CircuitsEnum t, Point c, String l, int n) {
        center = c;
        type = t;
        nInputs = 0;
        maxInputs = n;
        inputs = new Circuit[n];

        label = l;

        fgColor = Color.black;
        bgColor = Color.white;
        txColor = Color.black;
    }

    public abstract void draw(Graphics g);

    abstract void recomputeShape();

    public Color getForegroundColor() {
        return fgColor;
    }

    public Color getBackgroundColor() {
        return bgColor;
    }

    public Color getTextColor() {
        return txColor;
    }

    public String getLabel() {
        return label;
    }

    public void setOutputConnectionTo(Circuit conn) {
        output = conn;
        conn.addInputConnectionFrom(this);
    }

    public void addInputConnectionFrom(Circuit conn) {
        if ((nInputs + 1) > maxInputs) {
            return;
        } else {
            inputs[nInputs++] = conn;
        }
    }

    public Point getCenter() {
        return center;
    }

    public CircuitsEnum getType() {
        return type;
    }

    public synchronized void setCenter(Point newCenter) {
        center = newCenter;
        recomputeShape();
    }

    public synchronized void setBackgroundColor(Color b) {
        bgColor = b;
    }

    public synchronized void setForegroundColor(Color c) {
        fgColor = c;
    }

    public synchronized void setTextColor(Color t) {
        txColor = t;
    }
    
}
