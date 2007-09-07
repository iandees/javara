package com.yellowbkpk.maps;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ian Dees
 * 
 */
public abstract class GOverlay {

    private List<OverlayUpdateListener> overlayUpdateListeners;
    private boolean visible;

    public GOverlay() {
        overlayUpdateListeners = new ArrayList<OverlayUpdateListener>();
        setVisible(true);
    }
    
    /**
     * @return
     */
    public abstract boolean shouldDraw(GLatLngBounds view);

    /**
     * @param dbg2
     * @param bounds
     * @param imageObserver
     *            TODO
     * @param bounds
     */
    public abstract void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds,
            ImageObserver imageObserver);

    /**
     * @param listener
     */
    public synchronized void registerForUpdates(OverlayUpdateListener listener) {
        overlayUpdateListeners.add(listener);
    }
    
    void notifyOverlayListeners() {
        for (OverlayUpdateListener listener : overlayUpdateListeners) {
            listener.overlayUpdated();
        }
    }
    
    public void setVisible(boolean visi) {
        visible = visi;
        notifyOverlayListeners();
    }
    
    public boolean isVisible() {
        return visible;
    }

}
