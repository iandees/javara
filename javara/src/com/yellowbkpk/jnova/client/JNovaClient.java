package com.yellowbkpk.jnova.client;

import com.yellowbkpk.jnova.client.game.JNovaController;
import com.yellowbkpk.jnova.client.gui.JNovaGUIWindow;

public class JNovaClient {

    public static void main(String[] args) {
        JNovaController c = new JNovaController();
        JNovaGUIWindow g = new JNovaGUIWindow(c);
        g.start();
        c.connectTo("localhost");
    }

}
