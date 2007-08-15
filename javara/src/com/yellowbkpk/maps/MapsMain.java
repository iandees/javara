package com.yellowbkpk.maps;

import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.gui.MapDisplayPanel;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;

public class MapsMain {

    public static void main(String[] args) {
        final Map map = new Map();
        final MapDisplayPanel panel = new MapDisplayPanel(map);
        final MapDisplayFrame frame = new MapDisplayFrame(panel);
        panel.setCenter(new GLatLng(43.0, -90.0), 7);
        frame.start();


        final GLatLng dotLatLng = new GLatLng(43.0, -90.0);
        final GMarker dot = new GMarker(dotLatLng);
        map.addOverlay(dot);
        
        /*GPSReader gps = null;
        try {
            gps = new GPSReader("COM9");
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        }
        gps.addGPSChangeListener(new GPSChangeListener() {
            public void locationUpdated(double lat, double lng) {
//                frame.setCenter(new GLatLng(lat, lng));
            }
        });*/

        panel.addMapMouseListener(new MapMouseListener() {
            public void mouseClicked(GLatLng latLng, int clickCount) {
                dot.setCenter(latLng);
            }
        });
    }

}
