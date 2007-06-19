package com.yellowbkpk.jnova.client;

import com.yellowbkpk.jnova.client.game.JNovaController;
import com.yellowbkpk.jnova.client.gui.JNovaGUI;

public class JNovaClient {

    /**
     * @param args
     */
    public static void main(String[] args) {
        JNovaController c = new JNovaController();
        JNovaGUI g = new JNovaGUI(c);
        g.start();
    }

}
