package com.yellowbkpk.jnova.server;

public class JNovaServer {

    public static void main(String[] args) {
        JNovaServerController c = new JNovaServerController(6744);
        c.start();
    }

}
