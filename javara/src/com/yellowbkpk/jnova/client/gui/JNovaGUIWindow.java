package com.yellowbkpk.jnova.client.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.yellowbkpk.jnova.client.game.JNovaController;

public class JNovaGUIWindow {

    private JFrame frame;

    public JNovaGUIWindow(final JNovaController c) {
        frame = new JFrame("JNova");
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                c.disconnectFromServer();
                frame.setVisible(false);
                System.exit(0);
            }

        });
        JNovaGUI panel = new JNovaGUI(c);
        frame.setContentPane(panel);
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

}
