package com.yellowbkpk.algebracircuit;

import java.util.ArrayList;
import java.util.List;

import com.yellowbkpk.algebracircuit.circuits.Circuit;

public class CircuitState {

    private static final int MAX_STEPS = 5000;
    
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

	public void updateCircuitsToSteadyState() {
		for (Circuit circuit : circuits) {
			circuit.resetSteadyState();
		}
		
		for (int steps = 0; steps < MAX_STEPS; steps++) {
			int circuitsNotSteady = circuits.size();
			for (Circuit circuit : circuits) {
				if (circuit.isSteady()) {
					circuitsNotSteady--;
				} else {
					circuit.step();
				}
			}
			
			if(circuitsNotSteady <= 0) {
				return;
			}
		}
	}

}
