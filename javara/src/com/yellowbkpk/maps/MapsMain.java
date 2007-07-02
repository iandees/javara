package com.yellowbkpk.maps;

import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.map.Map;

public class MapsMain {

    public static void main(String[] args) {
        Map m = new Map();
        MapDisplayFrame frame = new MapDisplayFrame(m);
        frame.start();
    }

}
