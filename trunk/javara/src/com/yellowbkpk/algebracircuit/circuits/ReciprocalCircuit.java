package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class ReciprocalCircuit extends OneInputCircuit {

    public ReciprocalCircuit(Point c) {
        super(CircuitsEnum.RECIPROCAL, c, "RECIP");
    }

	public double getUpdatedValue() {
		return 1.0 / inputs[0].getValue();
	}

}
