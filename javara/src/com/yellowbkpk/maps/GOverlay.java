package com.yellowbkpk.maps;

import java.awt.Graphics;
import java.awt.Point;


/**
 * @author Ian Dees
 *
 */
public interface GOverlay {

    /**
     * @return
     */
    boolean shouldDraw(GLatLngBounds view);

    /**
     * @param dbg2
     * @param nwPoint 
     * @param bounds
     */
    void drawOverlay(Graphics dbg2, Point nwPoint, int zoom, GLatLngBounds viewBounds);

}
