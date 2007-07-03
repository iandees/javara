package com.yellowbkpk.maps.gui;

import java.awt.Image;
import java.awt.MediaTracker;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

public class ImageCache {
    
    ConcurrentHashMap<String, Image> cacheMap;
    Map<String, Thread> imgFetchers;
    private String baseURL = "http://mt3.google.com/mt?n=404&v=w2.56&";
    
    public ImageCache() {
        cacheMap = new ConcurrentHashMap<String, Image>();
        imgFetchers = new HashMap<String, Thread>();
    }
    
    public Image get(int x, int y, int zoom) {
        final String url = baseURL + "x=" + x + "&y=" + y + "&zoom=" + zoom;

        Image image = cacheMap.get(url);

        if (image == null) {
            Thread thread = imgFetchers.get(url);
            if (thread == null) {
                Thread imgFetcher = new Thread(new Runnable() {
                    public void run() {
                        URL u = null;
                        try {
                            u = new URL(url);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            System.err.println("Bad URL");
                            return;
                        }

                        ImageIcon i = new ImageIcon(u);

                        if (i.getImageLoadStatus() == MediaTracker.COMPLETE) {
                            cacheMap.put(url, i.getImage());
                        } else {
                            System.err.println("Could not load " + u + " code was " + i.getImageLoadStatus());
                        }
                    }
                });
                imgFetchers.put(url, imgFetcher);
                imgFetcher.start();
                return null;
            } else {
                // Already fetching it.
                return null;
            }
        } else {
            return image;
        }
    }

}
