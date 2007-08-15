package com.yellowbkpk.maps.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageCache {
    
    private static final int TILE_SIZE = 256;
    private static final String CACHE_DIR = "cache";
    private static final int MAX_CACHE_SIZE = 128;
    ConcurrentHashMap<String, Image> cacheMap;
    Map<String, Thread> imgFetchers;
    private String baseURL = "http://mt3.google.com/mt?n=404&v=w2.60&";
    private BufferedImage noImageTile;
    private static final boolean NETWORK_ACCESS_ENABLED = true;
    
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
        // If the image was not in memory cache
        if (image == null) {
            // If the image was not on disk cache
            final String filename = CACHE_DIR + "/" + zoom + "/" + x + "-" + y + ".png";
            final File file = new File(filename);
            
            if (file.exists()) {
                Thread thread = imgFetchers.get(url);
                if (thread == null) {
                    Thread imgFetcher = new Thread(new Runnable() {
                        public void run() {
                            Image image = new ImageIcon(file.getPath()).getImage();
                            cacheMap.put(url, image);
                            imgFetchers.remove(url);
                        }
                    });
                    imgFetchers.put(url, imgFetcher);
                    imgFetcher.start();
                    return null;
                } else {
                    // Already fetching it from a file.
                    return null;
                }
            } else {
                // If network access is turned on
                if(!NETWORK_ACCESS_ENABLED) {
                    return noImageTile;
                }
                
                // If the image is not already being fetched
                Thread thread = imgFetchers.get(url);
                if (thread == null) {
                    Thread imgFetcher = new Thread(new Runnable() {
                        public void run() {
                            URL u = null;
                            try {
                                u = new URL(url);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                                return;
                            }

                            ImageIcon i = new ImageIcon(u);

                            if (i.getImageLoadStatus() == MediaTracker.COMPLETE) {
                                cacheMap.put(url, i.getImage());
                                
                                // This chunk here saves the image icon to a file
                                BufferedImage bI = new BufferedImage(i.getIconWidth(), i.getIconWidth(), BufferedImage.TYPE_INT_ARGB);
                                Graphics bIGraphics = bI.getGraphics();
                                bIGraphics.drawImage(i.getImage(), 0, 0, null);
                                bIGraphics.dispose();
                                try {
                                    file.mkdirs();
                                    ImageIO.write(bI, "PNG", file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.err.println("Could not load " + u + " code was " + i.getImageLoadStatus());
                                cacheMap.put(url, noImageTile);
                            }

                            imgFetchers.remove(url);
                        }
                    });
                    imgFetchers.put(url, imgFetcher);
                    imgFetcher.start();
                    return null;
                } else {
                    // Already fetching it.
                    return null;
                }
            }
        } else {
            if(cacheMap.size() > MAX_CACHE_SIZE) {
                System.err.println("Cache: Clearing the cache map");
                cacheMap.clear();
            }
            
            return image;
        }
    }

}
