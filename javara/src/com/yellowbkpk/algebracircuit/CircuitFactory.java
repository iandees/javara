package com.yellowbkpk.algebracircuit;

import java.awt.Point;

import com.yellowbkpk.algebracircuit.circuits.AdditionCircuit;
import com.yellowbkpk.algebracircuit.circuits.InputCircuit;
import com.yellowbkpk.algebracircuit.circuits.MultiplicationCircuit;
import com.yellowbkpk.algebracircuit.circuits.NegationCircuit;
import com.yellowbkpk.algebracircuit.circuits.OutputCircuit;
import com.yellowbkpk.algebracircuit.circuits.ReciprocalCircuit;

public class CircuitFactory {

    public static Circuit buildCircuit(CircuitsEnum type, Point c) {
        if(CircuitsEnum.ADD.equals(type)) {
            return new AdditionCircuit(c);
        } else if(CircuitsEnum.NEGATE.equals(type)) {
            return new NegationCircuit(c);
        } else if(CircuitsEnum.MULTIPLY.equals(type)) {
            return new MultiplicationCircuit(c);
        } else if(CircuitsEnum.RECIPROCAL.equals(type)) {
            return new ReciprocalCircuit(c);
        } else if(CircuitsEnum.INPUT.equals(type)) {
            return new InputCircuit(c);
        } else if(CircuitsEnum.OUTPUT.equals(type)) {
            return new OutputCircuit(c);
        } else {
            return null;
        }
    }

}
