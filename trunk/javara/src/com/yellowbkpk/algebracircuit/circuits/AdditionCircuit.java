package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class AdditionCircuit extends TwoInputCircuit {

    public AdditionCircuit(Point c) {
        super(CircuitsEnum.ADD, c, "ADD");
    }

	public double getUpdatedValue() {
		return (inputs[0].getValue() + inputs[1].getValue());
	}
    
}
