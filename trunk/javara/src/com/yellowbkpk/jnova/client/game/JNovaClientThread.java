package com.yellowbkpk.jnova.client.game;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class JNovaClientThread implements Runnable {
    
    private static final int PORT = 6674;
    private String remoteAddr;
    private Socket socketToServer;
    private boolean running;
    private ConcurrentLinkedQueue<ServerClientMessage> queue;

    public JNovaClientThread(String address, ConcurrentLinkedQueue<ServerClientMessage> interconnect) {
        remoteAddr = address;
        queue = interconnect;
        
        running = false;
    }

    public void run() {
        OutputStream outputStream = null;
        
        try {
            socketToServer = new Socket(remoteAddr, PORT);
            outputStream = socketToServer.getOutputStream();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        running = true;

        while(running) {
            ServerClientMessage message = queue.poll();
            
            if(message != null) {
                try {
                    outputStream.write(message.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        try {
            socketToServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void shutdown() {
        running = false;
    }

}
