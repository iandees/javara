package com.yellowbkpk.jnova.server;

import java.net.Socket;

public class IndividualPlayerThread implements Runnable {

    public IndividualPlayerThread(Socket incomingSocket) {
        System.out.println("Player connected. Creating handler for them.");
    }

    public void run() {
        System.out.println("Running the player handler.");
    }

}
