package com.yellowbkpk.maps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public class GPolyline extends GOverlay {

    private List<GLatLng> myPoints;
    private GLatLngBounds myBounds;
    private Stroke myStroke;
    private List<Color> myColors;

    public GPolyline(List<GLatLng> points, Color color, int width) {
        myPoints = points;
        myColors = new ArrayList<Color>();
        myStroke = new BasicStroke(width);
    }

    public synchronized void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds, ImageObserver imageObserver) {
        Graphics2D g2d = (Graphics2D) dbg2;
        
        Stroke prevStroke = g2d.getStroke();
        g2d.setStroke(myStroke);
        
        int prevX = 0;
        int prevY = 0;
        boolean prevWasIn = false;
        
        for (int i = 0; i < myPoints.size(); i++) {
            GLatLng point = myPoints.get(i);
            Color color = myColors.get(i);
            
            int x = GoogleMapUtilities.lngToX(point.getLongitude(), 17 - zoom) - bounds.x;
            int y = GoogleMapUtilities.latToY(point.getLatitude(), 17 - zoom) - bounds.y;
            boolean wasIn = false;
            if(viewBounds.contains(point)) {
                wasIn = true;
            }

            g2d.setColor(color);
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
        if(!isVisible()) {
            return false;
        }
        
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
        addPoint(latLng, Color.red);
    }

    /**
     * @param latLng
     * @param color
     */
    public synchronized void addPoint(GLatLng latLng, Color color) {
        myPoints.add(latLng);
        myColors.add(color);
        notifyOverlayListeners();
    }

}
