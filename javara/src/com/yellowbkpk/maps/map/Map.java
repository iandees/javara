package com.yellowbkpk.maps.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yellowbkpk.maps.GLatLngBounds;
import com.yellowbkpk.maps.GOverlay;
import com.yellowbkpk.maps.OverlayUpdateListener;

public class Map implements OverlayUpdateListener {

    private List<GOverlay> overlays;
    private List<MapUpdateListener> mapUpdateListener;
    
    public Map() {
        overlays = new ArrayList<GOverlay>();
        mapUpdateListener = new ArrayList<MapUpdateListener>();
    }

    public synchronized void addOverlay(GOverlay dot) {
        dot.registerForUpdates(this);
        overlays.add(dot);
        notifyMapUpdate();
    }

    public synchronized Collection<GOverlay> getOverlays(GLatLngBounds bounds) {
        Collection<GOverlay> ov = new ArrayList<GOverlay>();
        
        for (GOverlay overlay : overlays) {
            if(overlay.shouldDraw(bounds)) {
                ov.add(overlay);
            }
        }
        
        return ov;
    }

    public synchronized void registerForUpdates(MapUpdateListener listener) {
        mapUpdateListener.add(listener);
    }

    public void overlayUpdated() {
        notifyMapUpdate();
    }

    private synchronized void notifyMapUpdate() {
        for (MapUpdateListener listener : mapUpdateListener) {
            listener.mapUpdated();
        }
    }

}
