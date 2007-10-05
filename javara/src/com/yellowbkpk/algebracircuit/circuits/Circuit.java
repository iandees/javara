package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public interface Circuit {

    void draw(Graphics g);

    Rectangle getBounds();

    void addInputConnectionFrom(Circuit conn);

    Point getCenter();

    CircuitsEnum getType();

    void step();
    
    double getValue();

    Point getOutputPoint();

    void setCenter(Point newCenter);

    void setForegroundColor(Color c);
    
    void setBackgroundColor(Color b);
    
    void setTextColor(Color t);

	boolean isSteady();

	void resetSteadyState();
}
