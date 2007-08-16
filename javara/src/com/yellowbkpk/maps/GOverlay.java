package com.yellowbkpk.maps;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;


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
     * @param imageObserver TODO
     * @param bounds
     */
    void drawOverlay(Graphics2D dbg2, Point nwPoint, int zoom, GLatLngBounds viewBounds, ImageObserver imageObserver);

}
