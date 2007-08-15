package com.yellowbkpk.util.gps;

/**
 * @author Ian Dees
 *
 */
public interface GPSChangeListener {

    /**
     * @param lat
     * @param lng
     */
    void locationUpdated(double lat, double lng);

}
