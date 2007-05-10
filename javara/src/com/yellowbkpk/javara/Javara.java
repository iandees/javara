/**
 * Copyright 2007 Ian Dees. All rights reserved.
 */
package com.yellowbkpk.javara;

import com.yellowbkpk.javara.game.JavaraGame;
import com.yellowbkpk.javara.gui.GameGUI;

/**
 * @author Ian Dees
 *
 */
public class Javara {

    /**
     * @param args
     */
    public static void main(String[] args) {
        GameGUI gui = new GameGUI();
        JavaraGame game = new JavaraGame(gui);
        gui.start();
    }

}
