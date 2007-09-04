package com.yellowbkpk.maps;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.swing.JFrame;

import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.gui.MapDisplayPanel;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;
import com.yellowbkpk.util.gps.GPSChangeListener;
import com.yellowbkpk.util.gps.GPSReader;

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
        
        final JFrame speedoFrame = new JFrame();
        final SpeedometerPanel speedometerPanel = new SpeedometerPanel();
        speedoFrame.setContentPane(speedometerPanel);
        speedoFrame.setVisible(true);
        
        final Map map = new Map();
        final MapDisplayPanel panel = new MapDisplayPanel(map);
        final MapDisplayFrame frame = new MapDisplayFrame(panel);
        panel.setCenter(new GLatLng(43.0, -90.0), 7);
        frame.start();

        final GLatLng dotLatLng = new GLatLng(43.0, -90.0);
        final GDirectedMarker dot = new GDirectedMarker(dotLatLng);
        map.addOverlay(dot);
        
        final List<GLatLng> linePoints = new ArrayList<GLatLng>();
        final GPolyline line = new GPolyline(linePoints, Color.red, 5);
        map.addOverlay(line);
        
        GPSReader gps = null;
        try {
            gps = new GPSReader("COM21");
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        }
        gps.addGPSChangeListener(new GPSChangeListener() {
            public void locationUpdated(double lat, double lng, double speed, double course) {
                dot.setCenter(new GLatLng(lat, lng));
                dot.setDirection(course);
                line.addPoint(new GLatLng(lat, lng));
                speedometerPanel.setSpeed(speed);
            }
        });

        panel.addMapMouseListener(new MapMouseListener() {

            public void mouseClicked(GLatLng latLng, int clickCount) {
            }
        });
    }

}
