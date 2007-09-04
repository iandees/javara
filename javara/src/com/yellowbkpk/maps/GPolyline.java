package com.yellowbkpk.maps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.ImageObserver;
import java.util.List;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public class GPolyline extends GOverlay {

    private List<GLatLng> myPoints;
    private Color myColor;
    private GLatLngBounds myBounds;
    private Stroke myStroke;

    public GPolyline(List<GLatLng> points, Color color, int width) {
        myPoints = points;
        myColor = color;
        myStroke = new BasicStroke(width);
    }

    public synchronized void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds, ImageObserver imageObserver) {
        Graphics2D g2d = (Graphics2D) dbg2;
        
        Stroke prevStroke = g2d.getStroke();
        g2d.setColor(myColor);
        g2d.setStroke(myStroke);
        
        int prevX = 0;
        int prevY = 0;
        boolean prevWasIn = false;
        
        for (GLatLng point : myPoints) {
            int x = GoogleMapUtilities.lngToX(point.getLongitude(), 17 - zoom) - bounds.x;
            int y = GoogleMapUtilities.latToY(point.getLatitude(), 17 - zoom) - bounds.y;
            boolean wasIn = false;
            if(viewBounds.contains(point)) {
                wasIn = true;
            }
            
            if (prevX != 0 && prevY != 0 && (wasIn || prevWasIn)) {
                g2d.drawLine(x, y, prevX, prevY);
            }
            prevX = x;
            prevY = y;
            prevWasIn = wasIn;
        }
        
        g2d.setStroke(prevStroke);
    }

    public synchronized boolean shouldDraw(GLatLngBounds viewBounds) {
        if (myPoints.size() < 2) {
            return false;
        } else {
            for (GLatLng point : myPoints) {
                if (viewBounds.contains(point)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param latLng
     */
    public synchronized void addPoint(GLatLng latLng) {
        myPoints.add(latLng);
        notifyOverlayListeners();
    }

}
