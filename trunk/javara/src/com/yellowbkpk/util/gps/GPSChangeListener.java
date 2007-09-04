package com.yellowbkpk.util.gps;

/**
 * @author Ian Dees
 *
 */
public interface GPSChangeListener {

    /**
     * @param lat
     * @param lng
     * @param course 
     * @param speed 
     */
    void locationUpdated(double lat, double lng, double speed, double course);

}
