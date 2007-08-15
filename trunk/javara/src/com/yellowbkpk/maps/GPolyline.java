package com.yellowbkpk.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public class GPolyline implements GOverlay {

    private List<GLatLng> myPoints;
    private Color myColor;
    private GLatLngBounds myBounds;
    private int myWidth;

    public GPolyline(List<GLatLng> points, Color color, int width) {
        myPoints = points;
        myColor = color;
        myWidth = width;
    }

    /**
     * 
     */
    private void updateBounds() {
        for (GLatLng point : myPoints) {
            myBounds.expandToFit(point);
        }
    }

    public void drawOverlay(Graphics dbg2, Point nwPoint, int zoom, GLatLngBounds viewBounds) {
        dbg2.setColor(myColor);
        int prevX = 0;
        int prevY = 0;
        for (GLatLng point : myPoints) {
            int x = GoogleMapUtilities.lngToX(point.getLongitude(), 17 - zoom) - nwPoint.x;
            int y = GoogleMapUtilities.latToY(point.getLatitude(), 17 - zoom) - nwPoint.y;
            if (prevX != 0 && prevY != 0) {
                dbg2.drawLine(x, y, prevX, prevY);
            }
            prevX = x;
            prevY = y;
        }
    }

    public boolean shouldDraw(GLatLngBounds viewBounds) {
        if(myPoints.size() < 2) {
            return false;
        } else {
            for (GLatLng point : myPoints) {
                if(viewBounds.contains(point)) {
                    return true;
                }
            }
        }
        
        if(myBounds.contains(viewBounds)) {
            return true;
        } else {
            return false;
        }
    }

}
