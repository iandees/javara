package com.yellowbkpk.maps.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;

public class MapDisplayPanel extends JPanel implements Runnable {

    private static final int TILE_SIZE = 256;
    private static final long TIME_PER_FRAME = 10;
    private static final Dimension SIZE = new Dimension(800, 600);
    private static final GLatLng DEFAULT_CENTER = new GLatLng(0, 0);
    private static final int DEFAULT_ZOOM = 10;
    
    private Map map;
    private Image dbImage;
    private Thread animator;
    private boolean running;
    private Graphics dbg;
    protected Point mousedownPoint;
    protected Point pixelCenter;
    protected Point originalPixelCenter;
    private SlidingWindow mapSlidingWindow;
    protected GLatLng originalLatLngCenter;

    public MapDisplayPanel(Map m) {
        setPreferredSize(new Dimension(800,600));
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mousedownPoint = e.getPoint();
                originalPixelCenter = pixelCenter;
                originalLatLngCenter = mapSlidingWindow.getCenter();
                System.out.println(originalLatLngCenter);
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousedownPoint.x;
                int dy = e.getY() - mousedownPoint.y;
                pixelCenter = new Point(originalPixelCenter.x + dx, originalPixelCenter.y + dy);
                
                // Convert the new pixel center to a new lat/lng center
                double dLng = GoogleMapUtilities.xToLng(dx);
                double dLat = GoogleMapUtilities.yToLat(dy);
                System.out.println(dLat+","+dLng);
                
                mapSlidingWindow.setCenter(new GLatLng(originalLatLngCenter.getLatitude() + dLat, originalLatLngCenter.getLongitude() + dLng));
                
                // Update the sliding window with a new center
                //mapSlidingWindow.setCenter();
            }
        });
        
        pixelCenter = new Point(0,0);
        map = m;
        mapSlidingWindow = new SlidingWindow(map, DEFAULT_CENTER, SIZE, DEFAULT_ZOOM);
    }

    protected double pixelsToLongitude(int dy, int zoom) {
        return 0;
    }

    protected double pixelsToLatitude(int dx, int zoom) {
        return 0;
    }

    public void addNotify() {
        super.addNotify();
        startAnimation();
    }

    private void startAnimation() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void stopAnimation() {
        running = false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    public void run() {
        running = true;

        while (running) {
            mapUpdate();
            mapRender();
            repaint();

            try {
                Thread.sleep(TIME_PER_FRAME);
            } catch (InterruptedException e) {
            }
        }
    }

    private void mapRender() {
        if (dbImage == null) {
            dbImage = createImage(SIZE);
        }
        
        dbg = dbImage.getGraphics();

        dbg.setColor(Color.gray);
        dbg.fillRect(0, 0, SIZE.width, SIZE.height);
        
        drawField(dbg);
    }

    private void drawField(Graphics dbg2) {
        
            // Get the northwest corner tile x and y coordinates
            int nwXPixels = GoogleMapUtilities.lngToX(mapSlidingWindow.getNorthwest().getLongitude());
            int nwYPixels = GoogleMapUtilities.latToY(mapSlidingWindow.getNorthwest().getLatitude());
            
            // Get the southeast corner tile x and y coordinates
            int seXPixels = GoogleMapUtilities.lngToX(mapSlidingWindow.getSoutheast().getLongitude());
            int seYPixels = GoogleMapUtilities.latToY(mapSlidingWindow.getSoutheast().getLatitude());
            
            // Get the northwest tile number
            int nwTileX = GoogleMapUtilities.xToTileX(nwXPixels, mapSlidingWindow.getZoom());
            int nwTileY = GoogleMapUtilities.yToTileY(nwYPixels, mapSlidingWindow.getZoom());
            
            // Get the southeast tile number
            int seTileX = GoogleMapUtilities.xToTileX(seXPixels, mapSlidingWindow.getZoom());
            int seTileY = GoogleMapUtilities.yToTileY(seYPixels, mapSlidingWindow.getZoom());
            
            // Loop from northwest to southeast and draw the tiles
            for(int x = nwTileX; x <= seTileX; x++) {
                for(int y = nwTileY; y <= seTileY; y++) {
                    // Convert the tile x,y to global-pixel coordinates
                    int tilePixelX = GoogleMapUtilities.tileXToX(x, mapSlidingWindow.getZoom());
                    int tilePixelY = GoogleMapUtilities.tileYToY(y, mapSlidingWindow.getZoom());
                    
                    // Convert the global-pixel coordinates to local-pixel coordinates
                }
            }
            
            dbg2.setColor(Color.white);
            dbg2.drawString(nwTileX+","+nwTileY, 0, 9);
            dbg2.drawString(seTileX+","+seTileY, SIZE.width-150, SIZE.height-6);
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }

    private void mapUpdate() {
        map.update();
    }
}
