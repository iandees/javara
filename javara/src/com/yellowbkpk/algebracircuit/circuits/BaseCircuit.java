package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public abstract class BaseCircuit implements Circuit {

    protected static final int RADIUS = 50;
	private static final double STEADY_STATE_CUTOFF = 0.0001;
    
    protected Point center;
    private CircuitsEnum type;
    
    private Color fgColor;
    private Color bgColor;
    private Color txColor;
    private String label;
    
    protected Circuit[] inputs;
    private int nInputs;
    private int maxInputs;
    
    protected double value;
    protected double oldValue;
	private boolean steadyState;

    BaseCircuit(CircuitsEnum t, Point c, String l, int n) {
        center = c;
        type = t;
        nInputs = 0;
        maxInputs = n;
        inputs = new Circuit[n];
        
        value = 0.8814;
        oldValue = 0.2501;
        steadyState = false;

        label = l;

        fgColor = Color.black;
        bgColor = Color.white;
        txColor = Color.black;
    }

    public abstract void draw(Graphics g);

    abstract void recomputeShape();
    
    abstract double getUpdatedValue();
    
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
    
    public void step() {
    	if(isFullyConnected()) {
    		oldValue = value;
        	value = (getUpdatedValue() + oldValue) / 2.0;
        	
        	if(Math.abs(value - oldValue) < STEADY_STATE_CUTOFF) {
        		steadyState  = true;
        	}
    	}
    }
    
    public double getValue() {
    	return value;
    }
    
	public boolean isSteady() {
		return steadyState;
	}
	
	public void resetSteadyState() {
		oldValue = 1.0;
		steadyState = false;
	}

	private boolean isFullyConnected() {
		if(maxInputs == 2) {
			return (inputs[0] != null && inputs[1] != null);
		} else if(maxInputs == 1) {
			return (inputs[0] != null);
		} else if(maxInputs == 0) {
			return true;
		} else {
			return false;
		}
	}
}
