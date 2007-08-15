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
    public boolean contains(GLatLng location) {
        double x1 = northwest.getLatitude();
        double y0 = northwest.getLongitude();
        double x0 = southeast.getLatitude();
        double y1 = southeast.getLongitude();
        double x = location.getLatitude();
        double y = location.getLongitude();
        
        return (x >= x0 &&
            y >= y0 &&
            x < x1 &&
            y < y1);
    }
    
}
