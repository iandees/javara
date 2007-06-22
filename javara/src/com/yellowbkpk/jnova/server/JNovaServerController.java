package com.yellowbkpk.jnova.server;

import java.io.IOException;

public class JNovaServerController {

    private int netPort;
    private ServerSocketThread serverSocket;

    public JNovaServerController(int port) {
        netPort = port;
        
        try {
            System.out.println("Listening on port " + netPort);
            serverSocket = new ServerSocketThread(netPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void start() {
        System.out.println("Starting the serve socket.");
        new Thread(serverSocket).start();
    }

}
