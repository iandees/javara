package com.yellowbkpk.maps;

import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.gui.MapDisplayPanel;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;

public class MapsMain {

    public static void main(String[] args) {
        /*
        // Modify system properties
        Properties sysProperties = System.getProperties();
        // Specify proxy settings
        sysProperties.put("proxyHost", "3.20.128.6");
        sysProperties.put("proxyPort", "88");
        sysProperties.put("proxySet",  "true");
        */
        
        final Map map = new Map();
        final MapDisplayPanel panel = new MapDisplayPanel(map);
        final MapDisplayFrame frame = new MapDisplayFrame(panel);
        panel.setCenter(new GLatLng(43.0, -90.0), 7);
        frame.start();

//        final GLatLng dotLatLng = new GLatLng(43.0, -90.0);
//        final GMarker dot = new GMarker(dotLatLng);
//        map.addOverlay(dot);
//        
//        final List<GLatLng> linePoints = new ArrayList<GLatLng>();
//        final GPolyline line = new GPolyline(linePoints, Color.red, 5);
//        map.addOverlay(line);
        
        String imageURL = "http://radar.weather.gov/ridge/RadarImg/N0R/MPX_N0R_0.gif";
        double line1 = 0.010192078653971;
        double line4 = -0.010192078653971;
        double line5 = -96.617529998270641;
        double line6 = 47.652304219055196;
        GLatLngBounds bounds = new GLatLngBounds(new GLatLng(line6, line5), new GLatLng(line6 - (550 * line1), line5 - (600 * line4)));
        final GGroundOverlay radarOverlay = new GGroundOverlay(imageURL, bounds);
        map.addOverlay(radarOverlay);
        
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
                //dot.setCenter(latLng);
                //linePoints.add(latLng);
            }
        });
    }

}
