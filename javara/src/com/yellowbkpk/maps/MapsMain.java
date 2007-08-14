package com.yellowbkpk.maps;

import java.awt.Dimension;

import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;

public class MapsMain {

    private static final Dimension DEFAULT_SIZE = new Dimension(600, 800);

	public static void main(String[] args) {
        Map map = new Map(DEFAULT_SIZE);
        MapDisplayFrame frame = new MapDisplayFrame(map);
        frame.start();
        
        map.setCenter(new GLatLng(43.0, -90.0), 7);
    }

}
