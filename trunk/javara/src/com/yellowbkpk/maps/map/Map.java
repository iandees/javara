package com.yellowbkpk.maps.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.gui.ImageCache;
import com.yellowbkpk.maps.gui.MapDisplayPanel;
import com.yellowbkpk.maps.gui.SlidingWindow;


public class Map {

    private static final int TILE_SIZE = 256;
	private static final GLatLng DEFAULT_CENTER = new GLatLng(0.0, 0.0);
	private static final int DEFAULT_ZOOM = 7;
	private GLatLng center;
	private int zoomLevel;
    private SlidingWindow mapSlidingWindow;
	private ImageCache imageCache;
	private Image loadingTileImage;
	
	public Map(Dimension size) {
        loadingTileImage = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = loadingTileImage.getGraphics();
        graphics.setColor(Color.gray);
        graphics.drawString("Loading...", 120, 128);

        mapSlidingWindow = new SlidingWindow(DEFAULT_CENTER, size, DEFAULT_ZOOM);
        imageCache = new ImageCache();
	}

    public GLatLng getCenter() {
        return center;
    }

	public void setCenter(GLatLng latLng, int zoom) {
		center = latLng;
		setZoom(zoom);
	}

	private void setZoom(int zoom) {
		zoomLevel = zoom;
	}

	public void zoomIn() {
		mapSlidingWindow.zoomIn();
	}

	public void drawAll(Graphics dbg2, ImageObserver obs) {
		dbg2.setColor(Color.white);
        final int zoomLevel = (17-getZoom());
    
        // Get the northwest corner tile x and y coordinates
        int nwXPixels = GoogleMapUtilities.lngToX(getNorthwest().getLongitude(), zoomLevel);
        int nwYPixels = GoogleMapUtilities.latToY(getNorthwest().getLatitude(), zoomLevel);
        
        // Get the southeast corner tile x and y coordinates
        int seXPixels = GoogleMapUtilities.lngToX(getSoutheast().getLongitude(), zoomLevel);
        int seYPixels = GoogleMapUtilities.latToY(getSoutheast().getLatitude(), zoomLevel);
        
        // Get the northwest tile number
        //Point nwTile = GoogleMapUtilities.getTileCoordinate(mapSlidingWindow.getNorthwest().getLatitude(), mapSlidingWindow.getNorthwest().getLongitude(), zoomLevel);
        int nwTileX = GoogleMapUtilities.xToTileX(nwXPixels);
        int nwTileY = GoogleMapUtilities.yToTileY(nwYPixels);
        
        // Get the southeast tile number
        //Point seTile = GoogleMapUtilities.getTileCoordinate(mapSlidingWindow.getSoutheast().getLatitude(), mapSlidingWindow.getSoutheast().getLongitude(), zoomLevel);
        int seTileX = GoogleMapUtilities.xToTileX(seXPixels);
        int seTileY = GoogleMapUtilities.yToTileY(seYPixels);

        // Loop from northwest to southeast and draw the tiles
        for(int x = nwTileX; x <= seTileX; x++) {
            for(int y = nwTileY; y <= seTileY; y++) {
                // Convert the tile x,y to global-pixel coordinates
                int tilePixelX = GoogleMapUtilities.tileXToX(x);
                int tilePixelY = GoogleMapUtilities.tileYToY(y);
                
                // Convert the global-pixel coordinates to local-pixel coordinates
                int localTilePixelX = tilePixelX - nwXPixels;
                int localTilePixelY = tilePixelY - nwYPixels;
                
                // Fetch the image
                Image image = imageCache.get(x,y,zoomLevel);
                
                if(image == null) {
                    image = loadingTileImage;
                }
                
                dbg2.drawImage(image, localTilePixelX, localTilePixelY, obs);
                
                dbg2.drawRect(localTilePixelX, localTilePixelY, 256, 256);
                dbg2.drawString("("+x+","+y+")", localTilePixelX+1, localTilePixelY+11);
            }
        }
	}

	public int getZoom() {
		return zoomLevel;
	}

	public GLatLng getSoutheast() {
		return mapSlidingWindow.getSoutheast();
	}

	public GLatLng getNorthwest() {
		return mapSlidingWindow.getNorthwest();
	}

	public void drawAll(SlidingWindow slidingWindow, Graphics dbg2,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		
	}

}
