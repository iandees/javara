package com.yellowbkpk.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public class GMarker implements GOverlay {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private GLatLng myPoint;
    private Color color;

    /**
     * @param point
     */
    public GMarker(GLatLng point) {
        myPoint = point;
        color = Color.black;
    }

    public GLatLng getLocation() {
        return myPoint;
    }

    public void drawOverlay(Graphics dbg2, Point nwPoint, int zoom, GLatLngBounds viewBounds) {
        dbg2.setColor(color);
        int x = GoogleMapUtilities.lngToX(myPoint.getLongitude(), 17-zoom) - nwPoint.x;
        int y = GoogleMapUtilities.latToY(myPoint.getLatitude(), 17-zoom) - nwPoint.y;
        dbg2.fillOval(x-WIDTH/2, y-HEIGHT/2, WIDTH, HEIGHT);
    }

    /**
     * @param latLng
     */
    public void setCenter(GLatLng latLng) {
        myPoint = latLng;
    }

}
