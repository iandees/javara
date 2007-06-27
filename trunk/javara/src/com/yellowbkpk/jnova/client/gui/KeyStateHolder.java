package com.yellowbkpk.jnova.client.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyStateHolder implements KeyListener {

    private Map<Integer, Boolean> keyStatus;
    
    public KeyStateHolder() {
        keyStatus = new HashMap<Integer, Boolean>();
    }

    public void keyPressed(KeyEvent e) {
        keyStatus.put(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        keyStatus.put(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent e) {
    }
    
    public synchronized boolean isKeyPressed(int val) {
        if(keyStatus.get(val) == null) {
            return false;
        } else {
            return keyStatus.get(val);
        }
    }

}
