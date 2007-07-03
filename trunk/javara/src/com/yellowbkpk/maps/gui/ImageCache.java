package com.yellowbkpk.maps.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

public class ImageCache {
    
    private static final int TILE_SIZE = 256;
    ConcurrentHashMap<String, Image> cacheMap;
    Map<String, Thread> imgFetchers;
    private String baseURL = "http://mt3.google.com/mt?n=404&v=w2.56&";
    private BufferedImage noImageTile;
    
    public ImageCache() {
        noImageTile = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = noImageTile.getGraphics();
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, TILE_SIZE, TILE_SIZE);
        graphics.setColor(Color.white);
        graphics.drawString("No Image", 120, 128);
        
        cacheMap = new ConcurrentHashMap<String, Image>();
        imgFetchers = new HashMap<String, Thread>();
    }
    
    public Image get(int x, int y, int zoom) {
        final int tilesAtZoom = GoogleMapUtilities.tilesAtZoom(zoom);

        if (y < 0) {
            return noImageTile;
        }

        if (y > tilesAtZoom) {
            return noImageTile;
        }
        
        int correctedX = x;
        if (x >= tilesAtZoom) {
            correctedX = x - tilesAtZoom;
        }

        if (x < 0) {
            correctedX = x + tilesAtZoom;
        }
        
        final String url = baseURL + "x=" + correctedX + "&y=" + y + "&zoom=" + zoom;

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

    private void correctRotatedEarth(int x, int y, int zoom) {
        
    }

}