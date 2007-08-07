package com.yellowbkpk.mobileclient;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Screen;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.corba.se.pept.transport.Connection;

public class Main extends MIDlet implements MobileConnectionListener {
    
    private Display display;
    private MobileConnector connection;

    public Main() {
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        System.exit(0);
    }

    protected void pauseApp() {
        
    }

    protected void startApp() throws MIDletStateChangeException {
        connection = new MobileConnector("yellowbkpk.dyndns.org");
        connection.addConnectionListener(this);
        
        display = Display.getDisplay(this);
        // Set the connecting screen until we get connected
        Screen waitScreen = new Form("Connecting...");
        display.setCurrent(waitScreen);
        connection.connect();
        
        /*List bedList = new List("Beds", List.IMPLICIT);
        Command chooseCommand = new Command("Choose", Command.ITEM, 0);
        final Command exitCommand = new Command("Exit", Command.EXIT, 0);
        bedList.addCommand(exitCommand);
        bedList.addCommand(chooseCommand);
        bedList.setCommandListener(new CommandListener() {
            public void commandAction(Command arg0, Displayable arg1) {
                if(exitCommand.equals(arg0)) {
                    try {
                        destroyApp(true);
                    } catch (MIDletStateChangeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        display.setCurrent(bedList);*/
    }

    public void connected() {
        List bedScreen = new List("Beds", List.IMPLICIT);
        display.setCurrent(bedScreen);
        connection.fetchBedList();
    }

    public void error(String message) {
        Screen errorScreen = new Form("Error connecting.");
        display.setCurrent(errorScreen);
    }

    public void bedListUpdated() {
        // Get the bed list from the connection
        // Add to the bedScreen
        // Add commands for the screen
        // Add command listener for the screen
        // Select command listener sends a request for the bed to the connection
    }

}
