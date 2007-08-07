package com.yellowbkpk.mobileclient;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.SocketConnection;

public class MobileConnector {

    private String remoteHost;
    
    private Vector connectionListeners;

    private String port = "8080";

    private HttpConnection socket;

    public MobileConnector(String host) {
        connectionListeners = new Vector();
        
        remoteHost = host;
    }

    public void addConnectionListener(MobileConnectionListener listener) {
        connectionListeners.addElement(listener);
    }

    public void connect() {
        try {
            socket = (HttpConnection) Connector.open("http://" + remoteHost + ":" + port);
        } catch (IOException e) {
            notifyConnectionListenersOfError(e);
        }
        
        notifyConnectionListenersOfConnection();
    }

    private void notifyConnectionListenersOfConnection() {
        for(int i = 0; i < connectionListeners.size(); i++) {
            ((MobileConnectionListener) connectionListeners.elementAt(i)).connected();
        }
    }

    private void notifyConnectionListenersOfError(IOException e) {
        for(int i = 0; i < connectionListeners.size(); i++) {
            ((MobileConnectionListener) connectionListeners.elementAt(i)).error(e.getMessage());
        }
    }

    public void fetchBedList() {
        // get the input stream from the connection
        // parse the xml
        // collect the bed names and sessions
        notifyConnectionListenersOfNewBedList();
    }

    private void notifyConnectionListenersOfNewBedList() {
        for(int i = 0; i < connectionListeners.size(); i++) {
            ((MobileConnectionListener) connectionListeners.elementAt(i)).bedListUpdated();
        }
    }

}
