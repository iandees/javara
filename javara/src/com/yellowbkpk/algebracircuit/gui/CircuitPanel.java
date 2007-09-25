package com.yellowbkpk.algebracircuit.gui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.yellowbkpk.algebracircuit.Circuit;
import com.yellowbkpk.algebracircuit.CircuitFactory;
import com.yellowbkpk.algebracircuit.CircuitState;
import com.yellowbkpk.algebracircuit.CircuitsEnum;
import com.yellowbkpk.algebracircuit.circuits.InputCircuit;

public class CircuitPanel extends JPanel {

    private AlgebraCircuitGUI parent;
    private CircuitState controller;
    private Circuit selectedCircuit;

    public CircuitPanel(AlgebraCircuitGUI parentGUI, CircuitState state) {
        super();
        parent = parentGUI;
        controller = state;
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            private Circuit connectorOutput;
            private Circuit connectorInput;
            private Circuit selectedCircuit;

            public void mouseClicked(MouseEvent e) {
                if (parent.getLatchType() != null) {
                    System.out.println("Adding a circuit.");
                    Circuit circ = CircuitFactory.buildCircuit(parent.getLatchType(), e.getPoint());
                    
                    if (circ != null) {
                        controller.addCircuit(circ);
                        parent.latchCircuitType(null);
                        updateLatch();
                        repaint();
                    }
                } else if(parent.getConnectorLatchCount() > 0) {
                    System.out.println("Adding a piece of connector.");
                    Circuit clickedCircuit = getClickedCircuit(e.getPoint());
                    
                    if(clickedCircuit == null) {
                        System.err.println("No circuit clicked.");
                    } else {
                        if(parent.getConnectorLatchCount() == 2) {
                            // output
                            System.out.println("Clicked the first circuit.");
                            connectorOutput = clickedCircuit;
                            parent.latchConnectorUsed();
                        } else if(parent.getConnectorLatchCount() == 1) {
                            // input
                            System.out.println("Clicked the second circuit");
                            connectorInput = clickedCircuit;
                            
                            // done with connector
                            System.out.println("Done with connection.");
                            connectorOutput.setOutputConnectionTo(connectorInput);
                            parent.latchConnectorUsed();
                            
                            repaint();
                        }
                    }
                } else if (getClickedCircuit(e.getPoint()) != null) {
                    System.out.println("Selecting a circuit.");
                    selectedCircuit = getClickedCircuit(e.getPoint());
                    if (CircuitsEnum.INPUT.equals(selectedCircuit.getType())) {
                        InputCircuit inputCircuit = (InputCircuit) selectedCircuit;
                        String string = JOptionPane.showInputDialog("Input value:");
                        double d = Double.parseDouble(string);
                        inputCircuit.setValue(d);
                        
                        repaint();
                    }
                } else {
                    selectedCircuit = null;
                }
            }
        });
    }

    protected Circuit getSelectedCircuit() {
        return selectedCircuit;
    }

    protected Circuit getClickedCircuit(Point point) {
        List<Circuit> circuits = controller.getCircuits();
        for (Circuit circuit : circuits) {
            if(circuit.getBounds().contains(point)) {
                return circuit;
            }
        }
        return null;
    }

    public void updateLatch() {
        if (parent.getLatchType() == null) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    public void updateConnectorLatch() {
        if (parent.getConnectorLatchCount() > 0) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        List<Circuit> circuits = controller.getCircuits();
        for (Circuit circuit : circuits) {
            circuit.draw(g);
        }
    }
    
}
