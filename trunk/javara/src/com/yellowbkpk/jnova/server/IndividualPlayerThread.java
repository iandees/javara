package com.yellowbkpk.jnova.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IndividualPlayerThread implements Runnable {

    private Socket mySocket;
    private InputStream incoming;
    private OutputStream outgoing;
    private int numMissedPongs;

    public IndividualPlayerThread(Socket incomingSocket) {
        System.out.println("Player connected. Creating handler for them.");
        mySocket = incomingSocket;
        try {
            incoming = mySocket.getInputStream();
            outgoing = mySocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        numMissedPongs = 0;
    }

    public void run() {
        System.out.println("Running the player handler.");
        
        while(numMissedPongs < 5) {
            try {
                int b = incoming.read();
                
                System.out.println(b);
                
                if(b < 0) {
                    numMissedPongs++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Lost connection from " + mySocket.getInetAddress());
    }

}
