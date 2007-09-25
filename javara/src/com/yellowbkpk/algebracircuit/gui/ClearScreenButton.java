package com.yellowbkpk.algebracircuit.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ClearScreenButton extends JButton {

    private AlgebraCircuitGUI parentGUI;

    public ClearScreenButton(AlgebraCircuitGUI circuitGUI) {
        super("Clear");
        parentGUI = circuitGUI;
        
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentGUI.clearCircuits();
            }
        });
    }

}
