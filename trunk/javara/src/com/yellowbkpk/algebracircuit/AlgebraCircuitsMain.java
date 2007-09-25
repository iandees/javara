package com.yellowbkpk.algebracircuit;

import com.yellowbkpk.algebracircuit.gui.AlgebraCircuitGUI;

public class AlgebraCircuitsMain {

    public static void main(String[] args) {
        CircuitState state = new CircuitState();
        AlgebraCircuitGUI gui = new AlgebraCircuitGUI(state);
        gui.start();
    }

}
