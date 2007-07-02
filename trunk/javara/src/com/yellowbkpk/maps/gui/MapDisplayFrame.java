package com.yellowbkpk.maps.gui;

import javax.swing.JFrame;

import com.yellowbkpk.maps.map.Map;

public class MapDisplayFrame extends JFrame {

    public MapDisplayFrame(Map m) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MapDisplayPanel panel = new MapDisplayPanel(m);
        setContentPane(panel);
        
        pack();
    }

    public void start() {
        setVisible(true);
    }

}
