package com.yellowbkpk.maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public class GMarker extends GOverlay {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    protected GLatLng myPoint;
    private GLatLngBounds myBounds;
    private Color color;

    /**
     * @param point
     */
    public GMarker(GLatLng point) {
        myPoint = point;
        myBounds = new GLatLngBounds(myPoint, myPoint);
        color = Color.black;
    }

    public synchronized boolean shouldDraw(GLatLngBounds viewBounds) {
        return isVisible() && viewBounds.contains(myBounds);
    }

    public synchronized void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds, ImageObserver imageObserver) {
        dbg2.setColor(color);
        int x = GoogleMapUtilities.lngToX(myPoint.getLongitude(), 17-zoom) - bounds.x;
        int y = GoogleMapUtilities.latToY(myPoint.getLatitude(), 17-zoom) - bounds.y;
        dbg2.fillOval(x-WIDTH/2, y-HEIGHT/2, WIDTH, HEIGHT);
    }

    /**
     * @param latLng
     */
    public synchronized void setCenter(GLatLng latLng) {
        myPoint = latLng;
        myBounds = new GLatLngBounds(myPoint, myPoint);
        notifyOverlayListeners();
    }

}
