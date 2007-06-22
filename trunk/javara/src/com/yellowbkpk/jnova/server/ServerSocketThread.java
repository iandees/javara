package com.yellowbkpk.jnova.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketThread implements Runnable {

    private int port;
    private boolean running;
    private ServerSocket socket;

    public ServerSocketThread(int netPort) throws IOException {
        port = netPort;
        running = false;
        socket = new ServerSocket(port);
    }

    public void run() {
        running = true;
        
        while(running) {
            try {
                System.out.println("Waiting for incoming connection.");
                Socket incomingSocket = socket.accept();
                System.out.println("Incoming connection from " + incomingSocket.getInetAddress());
                IndividualPlayerThread playerThread = new IndividualPlayerThread(incomingSocket);
                new Thread(playerThread).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void stop() {
        running = false;
    }

}
