package com.yellowbkpk.algebracircuit.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ConnectorButton extends JButton {

    private AlgebraCircuitGUI parentGUI;

    public ConnectorButton(AlgebraCircuitGUI circuitGUI) {
        super("Connector");
        parentGUI = circuitGUI;
        
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentGUI.latchConnector();
            }
        });
    }
    
}
