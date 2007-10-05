package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class NegationCircuit extends OneInputCircuit {

    public NegationCircuit(Point c) {
        super(CircuitsEnum.NEGATE, c, "NEG");
    }

	public double getUpdatedValue() {
		return -(inputs[0].getValue());
	}

}
