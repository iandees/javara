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
public class GRectangle extends GOverlay {

    private GLatLngBounds llBounds;
    
    public GRectangle(GLatLngBounds bnds) {
        llBounds = bnds;
    }

    public GRectangle(GLatLng nw, GLatLng se) {
        this(new GLatLngBounds(nw, se));
    }
    
    public void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds,
            ImageObserver imageObserver) {

        GLatLng northwest = llBounds.getNorthwest();
        int x = GoogleMapUtilities.lngToX(northwest.getLongitude(), 17-zoom);
        int y = GoogleMapUtilities.latToY(northwest.getLatitude(), 17-zoom);
        
        GLatLng southeast = llBounds.getSoutheast();
        int x1 = GoogleMapUtilities.lngToX(southeast.getLongitude(), 17-zoom);
        int y1 = GoogleMapUtilities.latToY(southeast.getLatitude(), 17-zoom);
        
        int width = x1 - x;
        int height = y1 - y;
        
        x -= bounds.x;
        y -= bounds.y;
        
        dbg2.setColor(Color.red);
        dbg2.drawRect(x, y, width, height);
        dbg2.setColor(new Color(255,0,0,25));
        dbg2.fillRect(x, y, width, height);
    }

    public boolean shouldDraw(GLatLngBounds view) {
        return true;
    }

}
