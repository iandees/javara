package com.yellowbkpk.algebracircuit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.yellowbkpk.algebracircuit.CircuitState;
import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class AlgebraCircuitGUI {

    private CircuitState controller;
    private JFrame frame;
    private CircuitsEnum circuitTypeLatch;
    private JToolBar toolBar;
    private CircuitPanel circuitPanel;
    private int connectorDrawLatch;

    public AlgebraCircuitGUI(CircuitState state) {
        controller = state;
        circuitTypeLatch = null;

        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("Algebra Circuits");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel cp = new JPanel(new BorderLayout());

        circuitPanel = new CircuitPanel(this, controller);
        JScrollPane scroller = new JScrollPane(circuitPanel);
        scroller.setPreferredSize(new Dimension(800, 600));
        cp.add(scroller, BorderLayout.CENTER);

        toolBar = new JToolBar();
        toolBar.add(new ConnectorButton(this));
        toolBar.addSeparator();
        toolBar.add(new AddCircuitButton(this, CircuitsEnum.INPUT));
        toolBar.add(new AddCircuitButton(this, CircuitsEnum.OUTPUT));
        toolBar.addSeparator();
        toolBar.add(new AddCircuitButton(this, CircuitsEnum.ADD));
        toolBar.add(new AddCircuitButton(this, CircuitsEnum.NEGATE));
        toolBar.add(new AddCircuitButton(this, CircuitsEnum.MULTIPLY));
        toolBar.add(new AddCircuitButton(this, CircuitsEnum.RECIPROCAL));
        toolBar.addSeparator();
        toolBar.add(new ClearScreenButton(this));
        cp.add(toolBar, BorderLayout.PAGE_START);

        frame.setContentPane(cp);
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

    public void latchCircuitType(CircuitsEnum circuitType) {
        if (circuitTypeLatch == null) {
            // Set the latch if nothing is set
            circuitTypeLatch = circuitType;
        } else {
            // If something is set already
            if (circuitTypeLatch == circuitType) {
                // ... and the latch is what is clicked,
                // then set it back to null
                circuitTypeLatch = null;
            } else {
                // ... and the latch is not the same,
                // then set it to the new one
                circuitTypeLatch = circuitType;
            }
        }
        
        circuitPanel.updateLatch();
    }

    public CircuitsEnum getLatchType() {
        return circuitTypeLatch;
    }

    public void latchConnector() {
        if(connectorDrawLatch == 0) {
            connectorDrawLatch = 2;
        }
        
        circuitPanel.updateConnectorLatch();
    }
    
    public void latchConnectorUsed() {
        if(connectorDrawLatch > 0) {
            connectorDrawLatch--;
        }
        
        circuitPanel.updateConnectorLatch();
    }
    
    public int getConnectorLatchCount() {
        return connectorDrawLatch;
    }
    
    public void latchConnectorReset() {
        connectorDrawLatch = 0;
        
        circuitPanel.updateConnectorLatch();
    }

    public void clearCircuits() {
        controller.removeAllCircuits();
        circuitPanel.repaint();
    }
}
