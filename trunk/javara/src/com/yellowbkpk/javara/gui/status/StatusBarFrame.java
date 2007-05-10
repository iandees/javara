package com.yellowbkpk.javara.gui.status;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StatusBarFrame extends JFrame {
    
    private JPanel contentOfUndecoratedFrame;

    public StatusBarFrame() {
        super();
        setPreferredSize(new Dimension(640, 100));
        setLocation(0, 480);
        setUndecorated(true);
        
        contentOfUndecoratedFrame = new JPanel();
        contentOfUndecoratedFrame.setBorder(BorderFactory.createLineBorder(Color.black));
        add(contentOfUndecoratedFrame);
    }
}
