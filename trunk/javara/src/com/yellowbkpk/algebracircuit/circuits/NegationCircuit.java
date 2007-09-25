package com.yellowbkpk.algebracircuit.circuits;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class NegationCircuit extends OneInputCircuit {

    public NegationCircuit(Point c) {
        super(CircuitsEnum.NEGATE, c, "NEG");
    }

    public double getValue() {
        if (inputs[0] != null) {
            if (nPasses < RECURSIVE_CUTOFF) {
                nPasses++;
                value = (-inputs[0].getValue());
                return value;
            } else {
                return value;
            }
        } else {
            return Double.NaN;
        }
    }

}
