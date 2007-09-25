package com.yellowbkpk.algebracircuit;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface Circuit {

    void draw(Graphics g);

    Rectangle getBounds();

    void setOutputConnectionTo(Circuit conn);

    void addInputConnectionFrom(Circuit conn);

    Point getCenter();

    CircuitsEnum getType();

    double getValue();

    Point getOutputPoint();
}
