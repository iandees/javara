/*
 * JNovaClientApplet.java
 *
 * Copyright 2007 General Electric Company. All Rights Reserved.
 */

package com.yellowbkpk.jnova.client;

import java.applet.Applet;
import java.awt.BorderLayout;

import com.yellowbkpk.jnova.client.game.JNovaController;
import com.yellowbkpk.jnova.client.gui.JNovaGUI;

/**
 * @author Ian Dees
 *
 */
public class JNovaClientApplet extends Applet {

    public void init() {
        JNovaController c = new JNovaController();
        
        JNovaGUI g = new JNovaGUI(c);
        setLayout(new BorderLayout());
        add(g, BorderLayout.CENTER);
        
        setVisible(true);
        g.start();
    }

}
