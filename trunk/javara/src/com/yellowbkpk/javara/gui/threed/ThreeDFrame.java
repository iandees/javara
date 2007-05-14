package com.yellowbkpk.javara.gui.threed;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ThreeDFrame extends JFrame {
    public ThreeDFrame() {
        super("Javara 3D Frame");
        // TODO: Should pick this out from a preferences panel or settings.
        setPreferredSize(new Dimension(640, 480));
        // TODO: Should do some sort of shutdown procedure here, not exit.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the 3D frame
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        Wrap3DFrame w3d = new Wrap3DFrame();
        c.add(w3d, BorderLayout.CENTER);
        pack();
    }
}
