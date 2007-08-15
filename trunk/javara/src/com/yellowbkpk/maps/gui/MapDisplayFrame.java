package com.yellowbkpk.maps.gui;

import javax.swing.JFrame;

public class MapDisplayFrame extends JFrame {

    private MapDisplayPanel panel;

    /**
     * @param panel2
     */
    public MapDisplayFrame(MapDisplayPanel mapPanel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = mapPanel;
        setContentPane(panel);
        
        pack();
    }

    public void start() {
        setVisible(true);
    }

}
