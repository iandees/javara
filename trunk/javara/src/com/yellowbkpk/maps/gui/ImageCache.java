package com.yellowbkpk.maps.gui;

import java.awt.MediaTracker;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class ImageCache {
    
    Map<URL, ImageIcon> cacheMap;
    private String baseURL = "http://mt3.google.com/mt?n=404&v=w2.56&";
    
    public ImageCache() {
        cacheMap = new HashMap<URL, ImageIcon>();
    }
    
    public ImageIcon get(int x, int y, int zoom) {
        URL u = null;
        try {
            u = new URL(baseURL + "x="+x+"&y="+y+"&zoom="+zoom);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        
        ImageIcon image = cacheMap.get(u);
        
        if (image == null) {
            ImageIcon i = new ImageIcon(u);
            if (i.getImageLoadStatus() == MediaTracker.COMPLETE) {
                cacheMap.put(u, i);
                return i;
            } else {
                System.err.println("Could not load " + u + " code was " + i.getImageLoadStatus());
            }
        } else {
            return image;
        }
        
        return null;
    }

}
