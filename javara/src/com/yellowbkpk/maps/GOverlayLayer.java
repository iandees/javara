/*
 * GOverlayLayer.java
 *
 * Copyright 2007 General Electric Company. All Rights Reserved.
 */

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
public class GOverlayLayer extends GOverlay implements OverlayUpdateListener {

    List<GOverlay> overlays;
    
    public GOverlayLayer() {
        overlays = new ArrayList<GOverlay>();
    }
    
    public void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds,
            ImageObserver imageObserver) {
        for (GOverlay overlay : overlays) {
            if (overlay.shouldDraw(viewBounds)) {
                overlay.drawOverlay(dbg2, bounds, zoom, viewBounds, imageObserver);
            }
        }
    }

    public boolean shouldDraw(GLatLngBounds view) {
        if(!isVisible()) {
            return false;
        }
        
        for (GOverlay overlay : overlays) {
            if(overlay.shouldDraw(view)) {
                return true;
            }
        }
        
        return false;
    }

    public void addOverlay(GOverlay overlay) {
        overlays.add(overlay);
        overlay.registerForUpdates(this);
        notifyOverlayListeners();
    }

    public void clearOverlays() {
        overlays.clear();
        notifyOverlayListeners();
    }

    public void overlayUpdated() {
        notifyOverlayListeners();
    }
}
