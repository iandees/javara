package com.yellowbkpk.algebracircuit;

import java.util.ArrayList;
import java.util.List;

public class CircuitState {

    private List<Circuit> circuits;
    
    public CircuitState() {
        circuits = new ArrayList<Circuit>();
    }
    
    public synchronized void addCircuit(Circuit circ) {
        circuits.add(circ);
    }

    public List<Circuit> getCircuits() {
        return circuits;
    }

    public synchronized void removeAllCircuits() {
        circuits.clear();
    }
    
    public synchronized void resetAllPassCounts() {
        for (Circuit circuit : circuits) {
            circuit.resetPasses();
        }
    }

}
