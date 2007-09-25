package com.yellowbkpk.algebracircuit.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.yellowbkpk.algebracircuit.CircuitsEnum;

public class AddCircuitButton extends JButton {

    private CircuitsEnum circuitType;
    private AlgebraCircuitGUI parentGUI;

    /**
     * @param parent 
     * @param ct
     */
    public AddCircuitButton(AlgebraCircuitGUI parent, CircuitsEnum ct) {
        super();
        circuitType = ct;
        parentGUI = parent;
        
        setText(circuitType.getName());
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentGUI.latchCircuitType(circuitType);
            }
        });
    }

}
