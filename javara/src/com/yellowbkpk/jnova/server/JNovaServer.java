package com.yellowbkpk.jnova.server;

public class JNovaServer {

    private static final int PORT = 6674;
    
    public static void main(String[] args) {
        JNovaServerController c = new JNovaServerController(PORT);
        c.start();
    }

}
