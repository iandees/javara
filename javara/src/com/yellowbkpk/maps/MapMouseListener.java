package com.yellowbkpk.maps;

import java.util.EventListener;

import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public interface MapMouseListener extends EventListener {

    /**
     * @param clickLL
     * @param i
     */
    void mouseClicked(GLatLng latLng, int clickCount);

}
