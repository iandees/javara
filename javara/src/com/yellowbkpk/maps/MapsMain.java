package com.yellowbkpk.maps;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.swing.JFrame;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.gui.MapDisplayPanel;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;
import com.yellowbkpk.util.gps.GPSChangeListener;
import com.yellowbkpk.util.gps.GPSReader;

public class MapsMain {

    public static void main(String[] args) {
        // Modify system properties
        Properties sysProperties = System.getProperties();
        // Specify proxy settings
        sysProperties.put("proxyHost", "3.20.128.6");
        sysProperties.put("proxyPort", "88");
        sysProperties.put("proxySet",  "true");
        
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
        
        // Scan a cache directory for all the images in it
        GOverlayLayer layer = new GOverlayLayer();
        int zoom = 5;
        File cacheDirToScan = new File("cache/" + zoom);
        String[] list = cacheDirToScan.list();
        for (String string : list) {
            String[] split = string.split("-");
            if(split.length == 2) {
                int xForZoom = Integer.parseInt(split[0]);
                int yForZoom = Integer.parseInt(split[1].substring(0, split[1].length()-4));
                int globalX = GoogleMapUtilities.tileXToX(xForZoom);
                int globalY = GoogleMapUtilities.tileYToY(yForZoom);
                double nwLng = GoogleMapUtilities.xToLng(globalX, zoom);
                double seLng = GoogleMapUtilities.xToLng(globalX+256, zoom);
                double nwLat = GoogleMapUtilities.yToLat(globalY, zoom);
                double seLat = GoogleMapUtilities.yToLat(globalY+256, zoom);
                GRectangle r = new GRectangle(new GLatLng(nwLat, nwLng), new GLatLng(seLat, seLng));
                layer.addOverlay(r);
            }
        }
        map.addOverlay(layer);
        
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
