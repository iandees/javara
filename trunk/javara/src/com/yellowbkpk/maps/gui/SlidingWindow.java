package com.yellowbkpk.maps.gui;

import java.awt.Dimension;
import java.awt.Point;

import com.yellowbkpk.maps.GLatLngBounds;
import com.yellowbkpk.maps.map.GLatLng;

public class SlidingWindow {

    private static final int MIN_ZOOM = 0;
    private static final int MAX_ZOOM = 16;
    
    private int zoom;
    
    private GLatLng center;
    private GLatLng northwest;
    private Dimension pixelSize;
    private GLatLng southeast;
    private Point pixelCenter;
    private Point nw;
    private Point se;
    private GLatLngBounds bounds;

    public SlidingWindow(GLatLng llCenter, Dimension size) {
        center = llCenter;
        pixelSize = size;
        
        updateWindow();
    }

    private void updateWindow() {
        // Convert the lat/lng center to global-pixel coordinates
        int centerX = GoogleMapUtilities.lngToX(center.getLongitude(), 17-zoom);
        int centerY = GoogleMapUtilities.latToY(center.getLatitude(), 17-zoom);
        pixelCenter = new Point(centerX, centerY);
        
        // Determine the northwest pixel coordinates
        int nwX = centerX - (pixelSize.width / 2);
        int nwY = centerY - (pixelSize.height / 2);
        nw = new Point(nwX, nwY);
        
        // Determine the southeast pixel coordinates
        int seX = centerX + (pixelSize.width / 2);
        int seY = centerY + (pixelSize.height / 2);
        se = new Point(seX, seY);
        
        // Convert the northwest pixel coordinates back to lat/lng
        double nwLat = GoogleMapUtilities.yToLat(nwY, 17-zoom);
        double nwLng = GoogleMapUtilities.xToLng(nwX, 17-zoom);
        
        // Convert the southeast pixel coordinates back to lat/lng
        double seLat = GoogleMapUtilities.yToLat(seY, 17-zoom);
        double seLng = GoogleMapUtilities.xToLng(seX, 17-zoom);
        
        northwest = new GLatLng(nwLat, nwLng);
        southeast = new GLatLng(seLat, seLng);
        bounds = new GLatLngBounds(northwest, southeast);
    }
    
    public GLatLng getNorthwest() {
        return northwest;
    }
    
    public GLatLng getNortheast() {
        return new GLatLng(getLatNorth(), getLngEast());
    }
    
    public GLatLng getSoutheast() {
        return new GLatLng(getLatSouth(), getLngEast());
    }
    
    public GLatLng getSouthwest() {
        return new GLatLng(getLatSouth(), getLngWest());
    }
    
    public double getLatNorth() {
        return northwest.getLatitude();
    }
    
    public double getLatSouth() {
        return southeast.getLatitude();
    }
    
    public double getLngWest() {
        return northwest.getLongitude();
    }
    
    public double getLngEast() {
        return southeast.getLongitude();
    }
    
    public void zoomOut() {
        if(zoom - 1 < MIN_ZOOM) {
            return;
        } else {
            zoom--;
        }
        
        updateZoom();
        updateWindow();
    }
    
    public void zoomIn() {
        if(zoom + 1 > MAX_ZOOM) {
            return;
        } else {
            zoom++;
        }
        
        updateZoom();
        updateWindow();
    }
    
    private void updateZoom() {
    }

    public void setCenter(GLatLng c, int z) {
        center = c;
        zoom = z;
        
        updateWindow();
    }
    
    public void setCenter(GLatLng c) {
        setCenter(c, zoom);
    }
    
    public void setPixelCenter(Point c) {
        setPixelCenter(c, zoom);
    }
    
    public void setPixelCenter(Point c, int z) {
        pixelCenter = c;
        zoom = z;
        
        double centerLat = GoogleMapUtilities.yToLat(pixelCenter.y, (17-zoom));
        double centerLng = GoogleMapUtilities.xToLng(pixelCenter.x, (17-zoom));
        GLatLng centerLatLng = new GLatLng(centerLat, centerLng);
        setCenter(centerLatLng);
    }

    public GLatLng getCenter() {
        return center;
    }

    public int getZoom() {
        return zoom;
    }

    public Point getGlobalPixelCenter() {
        return pixelCenter;
    }

    public void resize(Dimension size) {
        pixelSize = size;
        
        updateWindow();
    }

    /**
     * @return
     */
    public Point getNorthwestPoint() {
        return nw;
    }

    /**
     * @return
     */
    public Point getSoutheastPoint() {
        return se;
    }

    /**
     * @return
     */
    public GLatLngBounds getGBounds() {
        return bounds;
    }

}
