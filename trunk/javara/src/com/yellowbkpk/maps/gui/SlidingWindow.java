package com.yellowbkpk.maps.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;

public class SlidingWindow {

    private static final int MIN_ZOOM = 0;
    private static final int MAX_ZOOM = 16;
    
    private Map map;

    private int zoom;
    
    private GLatLng center;
    private double latHeight;
    private double lngWidth;
    private GLatLng northwest;
    private Dimension pixelSize;

    public SlidingWindow(Map m, GLatLng cent, Dimension size, int z) {
        map = m;
        center = cent;
        zoom = z;
        pixelSize = size;
        
        updateWindow();
    }

    private void updateWindow() {
        // Convert the lat/lng center to global-pixel coordinates
        Point bitmapCoordinate = GoogleMapUtilities.getBitmapCoordinate(center.getLatitude(), center.getLongitude(), zoom);
        
        int centerX = GoogleMapUtilities.lngToX(center.getLongitude());
        int centerY = GoogleMapUtilities.latToY(center.getLatitude());
        int bitmapOrigin = 256 << (17 - zoom);
        
        // Determine the northwest pixel coordinates
        int nwX = bitmapCoordinate.x - (pixelSize.width / 2);
        int nwY = bitmapCoordinate.y - (pixelSize.height / 2);
        
        // Convert the northwest pixel coordinates back to lat/lng
        double nwLat = GoogleMapUtilities.xToLng(nwX);
        double nwLng = GoogleMapUtilities.yToLat(nwY);
        
//        northwest = new GLatLng(nwLat, nwLng);
        
        // Convert the pixel window dimension to lat/lng
        latHeight = GoogleMapUtilities.yToLat(pixelSize.height);
        lngWidth = GoogleMapUtilities.xToLng(pixelSize.width);
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
        return northwest.getLatitude()+latHeight;
    }
    
    public double getLngWest() {
        return northwest.getLongitude();
    }
    
    public double getLngEast() {
        return northwest.getLongitude()+lngWidth;
    }
    
    public void zoomIn() {
        if(zoom - 1 < MIN_ZOOM) {
            return;
        } else {
            zoom--;
        }
        
        updateZoom();
        updateWindow();
    }
    
    public void zoomOut() {
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

    public GLatLng getCenter() {
        return center;
    }

    public int getZoom() {
        return zoom;
    }

}
