package com.yellowbkpk.maps.gui;

import java.awt.Dimension;

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
        int centerX = GoogleMapUtilities.lngToX(center.getLongitude());
        int centerY = GoogleMapUtilities.latToY(center.getLatitude());
        
        // Determine the northwest pixel coordinates
        int nwX = centerX - (pixelSize.width / 2);
        int nwY = centerY - (pixelSize.height / 2);
        
        // Convert the northwest pixel coordinates back to lat/lng
        double nwLat = GoogleMapUtilities.xToLng(nwX);
        double nwLng = GoogleMapUtilities.yToLat(nwY);
        
        northwest = new GLatLng(nwLat, nwLng);
        
        // Convert the pixel window dimension to lat/lng
        latHeight = GoogleMapUtilities.yToLat(pixelSize.height);
        lngWidth = GoogleMapUtilities.xToLng(pixelSize.width);
    }
    
    public GLatLng getNorthwest() {
        return northwest;
    }
    
    public GLatLng getNortheast() {
        return new GLatLng(northwest.getLatitude(), northwest.getLongitude()+lngWidth);
    }
    
    public GLatLng getSoutheast() {
        return new GLatLng(northwest.getLatitude()+latHeight, northwest.getLongitude()+lngWidth);
    }
    
    public GLatLng getSouthwest() {
        return new GLatLng(northwest.getLatitude()+latHeight, northwest.getLongitude());
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
