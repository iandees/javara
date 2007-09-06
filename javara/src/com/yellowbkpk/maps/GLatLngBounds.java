package com.yellowbkpk.maps;

import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public class GLatLngBounds {
    private GLatLng northwest;
    private GLatLng southeast;

    public GLatLngBounds(GLatLng nw, GLatLng se) {
        northwest = nw;
        southeast = se;
    }

    /**
     * @param location
     * @return
     */
    public boolean contains(GLatLngBounds location) {
        return contains(location.getNorthwest()) || contains(location.getSoutheast());
    }

    public GLatLng getNorthwest() {
        return northwest;
    }

    public GLatLng getSoutheast() {
        return southeast;
    }

    public void expandToFit(GLatLng point) {
        if(point.getLatitude() < northwest.getLatitude()) {
            northwest.setLatitude(point.getLatitude());
        }
        
        if(point.getLatitude() > southeast.getLatitude()) {
            southeast.setLatitude(point.getLatitude());
        }
        
        if(point.getLongitude() < northwest.getLongitude()) {
            northwest.setLongitude(point.getLongitude());
        }
        
        if(point.getLongitude() > southeast.getLongitude()) {
            southeast.setLongitude(point.getLongitude());
        }
    }

    /**
     * @param point
     * @return
     */
    public boolean contains(GLatLng point) {
        double x1 = northwest.getLatitude();
        double y0 = northwest.getLongitude();
        double x0 = southeast.getLatitude();
        double y1 = southeast.getLongitude();
        double locNWX = point.getLatitude();
        double locNWY = point.getLongitude();
        
        return (locNWX >= x0 &&
            locNWY >= y0 &&
            locNWX < x1 &&
            locNWY < y1);
    }

    public String toString() {
        return northwest.toString() + "," + southeast.toString();
    }
    
    
}
