package com.yellowbkpk.maps;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public class GDirectedMarker extends GMarker {

    private double direction;

    public GDirectedMarker(GLatLng point) {
        this(point, 0.0);
    }
    
    public GDirectedMarker(GLatLng point, double dir) {
        super(point);
        direction = dir;
    }

    public synchronized void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds,
            ImageObserver imageObserver) {
        super.drawOverlay(dbg2, bounds, zoom, viewBounds, imageObserver);

        int x1 = GoogleMapUtilities.lngToX(myPoint.getLongitude(), 17-zoom) - bounds.x;
        int y1 = GoogleMapUtilities.latToY(myPoint.getLatitude(), 17-zoom) - bounds.y;
        int x2 = (int) (Math.sin(Math.toRadians(direction))*10.0 + x1);
        int y2 = (int) (-Math.cos(Math.toRadians(direction))*10.0 + y1);
        
        dbg2.drawLine(x1, y1, x2, y2);
    }

    /**
     * @param course
     */
    public void setDirection(double course) {
        direction = course;
        System.err.println(direction);
        notifyOverlayListeners();
    }
    
    

}
