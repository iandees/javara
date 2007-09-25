package com.yellowbkpk.algebracircuit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface Circuit {

    void draw(Graphics g);

    Rectangle getBounds();

    void addInputConnectionFrom(Circuit conn);

    Point getCenter();

    CircuitsEnum getType();

    double getValue();

    Point getOutputPoint();

    void setCenter(Point newCenter);

    void setForegroundColor(Color c);
    
    void setBackgroundColor(Color b);
    
    void setTextColor(Color t);
}
