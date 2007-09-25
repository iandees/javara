package com.yellowbkpk.algebracircuit.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
    
    private Point dragStart;
    private Point circuitDragStart;

    public CircuitPanel(AlgebraCircuitGUI parentGUI, CircuitState state) {
        super();
        parent = parentGUI;
        controller = state;
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            private Circuit connectorOutput;
            private Circuit connectorInput;

            public void mousePressed(MouseEvent e) {
                if(selectedCircuit != null) {
                    System.out.println("Mouse pressed on " + selectedCircuit);
                    circuitDragStart = selectedCircuit.getCenter();
                    dragStart = e.getPoint();
                }
            }

            public void mouseReleased(MouseEvent e) {
                // Drag is finished
                circuitDragStart = null;
                dragStart = null;
            }

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
                            connectorInput.addInputConnectionFrom(connectorOutput);
                            parent.latchConnectorUsed();
                            
                            repaint();
                        }
                    }
                } else if (e.getClickCount() == 2 && getClickedCircuit(e.getPoint()) != null) {
                    System.out.println("Changing an input circuit value.");
                    Circuit selectedInputCircuit = getClickedCircuit(e.getPoint());
                    if (CircuitsEnum.INPUT.equals(selectedInputCircuit.getType())) {
                        InputCircuit inputCircuit = (InputCircuit) selectedInputCircuit;
                        String string = JOptionPane.showInputDialog("Input value:");
                        if (string != null) {
                            double d = Double.parseDouble(string);
                            inputCircuit.setValue(d);

                            repaint();
                        }
                    }
                } else if (getClickedCircuit(e.getPoint()) != null) {
                    Circuit recentlyClickedCircuit = getClickedCircuit(e.getPoint());
                    
                    // If there already was a circuit selected, change the old one's color back
                    if(selectedCircuit != null && selectedCircuit != recentlyClickedCircuit) {
                        // Change the previously selected circuit back to regular colors
                        selectedCircuit.setForegroundColor(Color.black);
                    }
                    
                    System.out.println("Selecting a circuit.");
                    selectedCircuit = recentlyClickedCircuit;
                    
                    // Highlight the selected circuit
                    selectedCircuit.setForegroundColor(Color.red);
                    repaint();
                } else {
                    if (selectedCircuit != null) {
                        // Change the selected circuit back to regular colors
                        selectedCircuit.setForegroundColor(Color.black);

                        // Then "deselect" from memory
                        selectedCircuit = null;
                        
                        repaint();
                    }
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedCircuit != null) {
                    Point newCenter = new Point(circuitDragStart.x + (e.getPoint().x - dragStart.x),
                            circuitDragStart.y + (e.getPoint().y - dragStart.y));
                    selectedCircuit.setCenter(newCenter);
                    repaint();
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
