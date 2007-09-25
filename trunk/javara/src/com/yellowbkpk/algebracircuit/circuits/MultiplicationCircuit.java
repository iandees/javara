package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class MultiplicationCircuit extends TwoInputCircuit {

    public MultiplicationCircuit(Point c) {
        super(CircuitsEnum.MULTIPLY, c, "MULT");
    }

    public double getValue() {
        if (inputs[0] != null && inputs[1] != null) {
            if (nPasses < RECURSIVE_CUTOFF) {
                nPasses++;
                value = (inputs[0].getValue() * inputs[1].getValue());
                return value;
            } else {
                return value;
            }
        } else {
            return Double.NaN;
        }
    }
    
}
