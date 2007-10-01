package com.yellowbkpk.maps;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.map.GLatLng;

public class RadarOverlayLayer extends GOverlayLayer {
    
    private static final int RADAR_IMG_HEIGHT = 600;
    private static final int RADAR_IMG_WIDTH = 550;
    private static final double RADAR_RANGE = 1.8;

    private Map<GLatLng, String> radarSites;
    private Map<String, GLatLngBounds> radarBounds;
    private Map<GLatLng, String> radarNames;

    public RadarOverlayLayer() {
        radarSites = new HashMap<GLatLng, String>();
        radarBounds = new HashMap<String, GLatLngBounds>();
        radarNames = new HashMap<GLatLng, String>();
        loadRadarProperties();
    }

    private void loadRadarProperties() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("radar.properties"));
            String s = new String();
            while((s = r.readLine()) != null) {
                String site = s;
                s = r.readLine();
                double latPerPixel = Double.parseDouble(s);
                s = r.readLine();
                double lngPerPixel = Double.parseDouble(s);
                s = r.readLine();
                double nwLng = Double.parseDouble(s);
                s = r.readLine();
                double nwLat = Double.parseDouble(s);

                String imageURL = "http://radar.weather.gov/ridge/RadarImg/N0R/"+site+"_N0R_0.gif";
                GLatLngBounds bounds = new GLatLngBounds(new GLatLng(nwLat, nwLng), new GLatLng(nwLat - (RADAR_IMG_WIDTH * latPerPixel), nwLng - (RADAR_IMG_HEIGHT * lngPerPixel)));
                GLatLng center = bounds.getCenter();                

                System.out.println("Radar site " + site + " is at center " + center);
                
                radarSites.put(center, imageURL);
                radarBounds.put(imageURL, bounds);
                radarNames.put(center, site);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawOverlay(Graphics2D dbg2, Rectangle bounds, int zoom, GLatLngBounds viewBounds,
            ImageObserver imageObserver) {
        Set<GLatLng> keys = radarSites.keySet();
        
        for (GLatLng key : keys) {
            if(key.distanceTo(viewBounds.getCenter()) < RADAR_RANGE) {
                int x = GoogleMapUtilities.lngToX(key.getLongitude(), 17-zoom) - bounds.x;
                int y = GoogleMapUtilities.latToY(key.getLatitude(), 17-zoom) - bounds.y;
                dbg2.drawOval(x-5, y-5, 10, 10);
                dbg2.drawOval(x-2, y-2, 4, 4);
                dbg2.drawString(radarNames.get(key), x + 7, y);
                int nwX = GoogleMapUtilities.lngToX(radarBounds.get(radarSites.get(key)).getNorthwest().getLongitude(), 17-zoom) - bounds.x;
                int nwY = GoogleMapUtilities.latToY(radarBounds.get(radarSites.get(key)).getNorthwest().getLatitude(), 17-zoom) - bounds.y;
                System.out.println(nwX);
                dbg2.drawOval(nwX, nwY, RADAR_IMG_WIDTH, RADAR_IMG_HEIGHT);
            }
        }
    }

    public boolean shouldDraw(GLatLngBounds view) {
        return isVisible();
    }

}
