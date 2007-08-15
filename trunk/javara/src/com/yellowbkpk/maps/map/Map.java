package com.yellowbkpk.maps.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yellowbkpk.maps.GLatLngBounds;
import com.yellowbkpk.maps.GOverlay;



public class Map {

    private List<GOverlay> overlays;
    
    public Map() {
        overlays = new ArrayList<GOverlay>();
    }

    /**
     * @param dot
     */
    public void addOverlay(GOverlay dot) {
        overlays.add(dot);
    }

    /**
     * @param bounds
     * @return 
     */
    public Collection<GOverlay> getOverlays(GLatLngBounds bounds) {
        Collection<GOverlay> ov = new ArrayList<GOverlay>();
        
        for (GOverlay overlay : overlays) {
            if(overlay.shouldDraw(bounds)) {
                ov.add(overlay);
            }
        }
        
        return ov;
    }


}
