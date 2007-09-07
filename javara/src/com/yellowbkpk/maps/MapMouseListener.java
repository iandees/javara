package com.yellowbkpk.maps;

import java.util.EventListener;

import com.yellowbkpk.maps.map.GLatLng;

/**
 * @author Ian Dees
 *
 */
public interface MapMouseListener extends EventListener {

    void mouseClicked(GLatLng latLng, int clickCount);

    void mouseDragged(GLatLng latLng);

}
