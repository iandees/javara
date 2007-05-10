package com.yellowbkpk.javara.gui;

import com.yellowbkpk.javara.gui.status.GameStatusFrame;
import com.yellowbkpk.javara.gui.status.StatusBarFrame;
import com.yellowbkpk.javara.gui.threed.ThreeDFrame;

public class GameGUI {
    private ThreeDFrame threeDFrame;
    private GameStatusFrame gameStatus;
    private StatusBarFrame statusBar;

    public GameGUI() {
        initGUI();
    }

    private void initGUI() {
        // Create a window for the 3D frame
        threeDFrame = new ThreeDFrame(); 
        
        // Create a window for the game status (this will have to have the menu bar on it)
        gameStatus = new GameStatusFrame();
        
        // Create a status bar (no title bar on this window)
        statusBar = new StatusBarFrame();
        
    }

    public void start() {
        // Show all three of the windows
        threeDFrame.setVisible(true);
        gameStatus.setVisible(true);
        statusBar.setVisible(true);
    }

}
