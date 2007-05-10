package com.yellowbkpk.javara.gui.status;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class MissionStatusFrame extends JFrame {
    public MissionStatusFrame() {
        // Add menu bar
        JMenuBar menuBar = new MissionStatusFrameMenuBar();
        setJMenuBar(menuBar);
    }
}
