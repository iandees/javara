package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class MultiplicationCircuit extends TwoInputCircuit {

    public MultiplicationCircuit(Point c) {
        super(CircuitsEnum.MULTIPLY, c, "MULT");
    }

	public double getUpdatedValue() {
		return (inputs[0].getValue() * inputs[1].getValue());
	}
    
}
