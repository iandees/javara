package com.yellowbkpk.maps;

import java.awt.Graphics;
import java.awt.Point;

import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public interface GOverlay {

    /**
     * @return
     */
    GLatLng getLocation();

    /**
     * @param dbg2
     * @param nwPoint 
     * @param bounds
     */
    void drawOverlay(Graphics dbg2, Point nwPoint, int zoom, GLatLngBounds viewBounds);

}
