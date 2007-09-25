package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class ReciprocalCircuit extends OneInputCircuit {

    public ReciprocalCircuit(Point c) {
        super(CircuitsEnum.RECIPROCAL, c, "RECIP");
    }

    public double getValue() {
        if (inputs[0] != null) {
            if (nPasses < RECURSIVE_CUTOFF) {
                nPasses++;
                value = (1.0/inputs[0].getValue());
                return value;
            } else {
                return value;
            }
        } else {
            return Double.NaN;
        }
    }

}
