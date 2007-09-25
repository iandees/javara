package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class MultiplicationCircuit extends TwoInputCircuit {

    public MultiplicationCircuit(Point c) {
        super(CircuitsEnum.MULTIPLY, c, "MULT");
    }

    public double getValue() {
        if(inputs[0] != null && inputs[1] != null) {
            return (inputs[0].getValue() * inputs[1].getValue());
        } else {
            return Short.MIN_VALUE;
        }
    }
    
}
