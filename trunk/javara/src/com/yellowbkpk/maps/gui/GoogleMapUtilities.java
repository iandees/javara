package com.yellowbkpk.maps.gui;

/**
 * From http://groups.google.com/group/Google-Maps-API/msg/64bd3dff69e40cca
 * 
 * @author Ian Dees
 *
 */
public final class GoogleMapUtilities {

    private static final int GOOG_OFFSET = 16777216;
    private static final double GOOG_RADIUS = 5340354;
    private static final int GOOG_PIXELS = 256;
    
    public static int lngToX(double lng) {
        return (int) (GOOG_OFFSET + GOOG_RADIUS * Math.toRadians(lng));
    }
    
    public static int latToY(double lat) {
        return (int) (GOOG_OFFSET - GOOG_RADIUS
                * Math.log((1 + Math.sin(Math.toRadians(lat))) / (1 - Math.sin(Math.toRadians(lat)))) / 2);
    }
    
    public static double xToLng(int x) {
        return Math.toDegrees((x-GOOG_OFFSET)/GOOG_RADIUS);
    }
    
    public static double yToLat(int y) {
        return Math.toRadians(Math.PI / 2 - 2 * Math.atan(Math.exp((y - GOOG_OFFSET) / GOOG_RADIUS)));
    }
    
    public static int xToTileX(int x, int zoom) {
        return x / GOOG_PIXELS >> zoom;
    }
    
    public static int yToTileY(int y, int zoom) {
        return y / GOOG_PIXELS >> zoom;
    }

    public static int tileXToX(int tileX, int zoom) {
        return tileX * GOOG_PIXELS << zoom;
    }

    public static int tileYToY(int tileY, int zoom) {
        return tileY * GOOG_PIXELS << zoom;
    }
}
