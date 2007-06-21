package com.yellowbkpk.jnova.client.gui;

import javax.swing.JFrame;

import com.yellowbkpk.jnova.client.game.JNovaController;

public class JNovaGUIWindow {

    private JFrame frame;

    public JNovaGUIWindow(JNovaController c) {
        frame = new JFrame("JNova");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JNovaGUI panel = new JNovaGUI(c);
        frame.setContentPane(panel);
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

}
