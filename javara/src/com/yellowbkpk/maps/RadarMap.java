/*
 * RadarMap.java
 *
 * Copyright 2007 General Electric Company. All Rights Reserved.
 */

package com.yellowbkpk.maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.gui.MapDisplayPanel;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;

/**
 * @author Ian Dees
 *
 */
public class RadarMap {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final Map map = new Map();
        final MapDisplayPanel panel = new MapDisplayPanel(map);
        final MapDisplayFrame frame = new MapDisplayFrame(panel);
        panel.setCenter(new GLatLng(43.0, -90.0), 7);
        frame.start();
        
        try {
            BufferedReader r = new BufferedReader(new FileReader("radar.properties"));
            String s = new String();
            while((s = r.readLine()) != null) {
                String site = s;
                s = r.readLine();
                double line1 = Double.parseDouble(s);
                s = r.readLine();
                double line4 = Double.parseDouble(s);
                s = r.readLine();
                double line5 = Double.parseDouble(s);
                s = r.readLine();
                double line6 = Double.parseDouble(s);
                
                String imageURL = "http://radar.weather.gov/ridge/RadarImg/N0R/"+site+"_N0R_0.gif";
                GLatLngBounds bounds = new GLatLngBounds(new GLatLng(line6, line5), new GLatLng(line6 - (550 * line1), line5 - (600 * line4)));
                GGroundOverlay radarOverlay = new GGroundOverlay(imageURL, bounds);
                map.addOverlay(radarOverlay);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
