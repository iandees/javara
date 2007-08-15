package com.yellowbkpk.maps.gui;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

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
    private static final int NUM_ZOOM_LEVELS = 17;
    private static final double[] PIXELS_PER_LON_DEGREE = new double[NUM_ZOOM_LEVELS+1];
    private static final double[] PIXELS_PER_LON_RADIAN = new double[NUM_ZOOM_LEVELS+1];
    private static final double TWO_PI = Math.PI * 2.0;
    private static final Point[] BITMAP_ORIGIN = new Point[NUM_ZOOM_LEVELS+1];
    private static final int[] NUM_TILES = new int[NUM_ZOOM_LEVELS+1];
    private static final double RADIAN_PI = Math.PI / 180.0;
    
    /* Fill in the static variables. */
    static {

        int c = GOOG_PIXELS;
        for (int d = NUM_ZOOM_LEVELS; d >= 0; d--) {
            PIXELS_PER_LON_DEGREE[d] = c / 360.0;
            PIXELS_PER_LON_RADIAN[d] = c / TWO_PI;
            int e = c / 2;
            BITMAP_ORIGIN[d] = new Point(e, e);
            NUM_TILES[d] = c / GOOG_PIXELS;
            c *= 2;
        }
        System.out.println("");
    }
    
    public static Point getBitmapCoordinate(double latitude,
            double longitude, int zoomLevel) {
        Point d = new Point(0, 0);

        d.x = (int) Math.floor(BITMAP_ORIGIN[zoomLevel].x + longitude
                * PIXELS_PER_LON_DEGREE[zoomLevel]);
        double e = Math.sin(latitude * RADIAN_PI);

        if (e > 0.9999) {
            e = 0.9999;
        }

        if (e < -0.9999) {
            e = -0.9999;
        }

        d.y = (int) Math.floor(BITMAP_ORIGIN[zoomLevel].y + 0.5
                * Math.log((1 + e) / (1 - e)) * -1
                * (PIXELS_PER_LON_RADIAN[zoomLevel]));
        return d;
    }

    public static Point getTileCoordinate(double latitude,
            double longitude, int zoomLevel) {
        Point d = getBitmapCoordinate(latitude, longitude, zoomLevel);
        d.x = (int) Math.floor(d.x / GOOG_PIXELS);
        d.y = (int) Math.floor(d.y / GOOG_PIXELS);

        return new Point(d.x, d.y);
    }

    /**
     * returns a Rectangle2D with x = lon, y = lat, width=lonSpan,
     * height=latSpan for an x,y,zoom as used by google.
     */
    public static Rectangle2D.Double getTileLatLong(int tileX, int tileY, int zoom) {
        double lon = -180; // x
        double lonWidth = 360; // width 360

        // double lat = -90; // y
        // double latHeight = 180; // height 180
        double lat = -1;
        double latHeight = 2;

        int tilesAtThisZoom = 1 << zoom;
        lonWidth = 360.0 / tilesAtThisZoom;
        lon = -180 + (tileX * lonWidth);
        latHeight = 2.0 / tilesAtThisZoom;
        lat = ((tilesAtThisZoom/2 - tileY-1) * latHeight);

        // convert lat and latHeight to degrees in a transverse mercator
        // projection note that in fact the coordinates go from about -85 to +85
        // not -90 to 90!
        latHeight += lat;
        latHeight = (2.0 * Math.atan(Math.exp(Math.PI * latHeight)))
                - (Math.PI / 2.0);
        latHeight *= (180.0 / Math.PI);

        lat = (2.0 * Math.atan(Math.exp(Math.PI * lat))) - (Math.PI / 2.0);
        lat *= (180.0 / Math.PI);

        latHeight -= lat;

        if (lonWidth < 0) {
            lon = lon + lonWidth;
            lonWidth = -lonWidth;
        }

        if (latHeight < 0) {
            lat = lat + latHeight;
            latHeight = -latHeight;
        }

        return new Rectangle2D.Double(lon, lat, lonWidth, latHeight);
    }
    
    public int lngToTileX(double longitudeDegrees, int zoom) {
        int tiles = (int) Math.pow(2, zoom);
        int circumference = GOOG_PIXELS * tiles;
        double radius = circumference / (2.0 * Math.PI);
        double longitude = Math.toRadians(longitudeDegrees);
        int falsenorthing = (circumference / 2);
        return (int) ((radius * longitude) + falsenorthing) + BITMAP_ORIGIN[17-zoom].x;
    }
    
    public double tileXToLng(int x, int zoom) {
        int tiles = (int) Math.pow(2, zoom);
        int circumference = GOOG_PIXELS * tiles;
        double radius = circumference / (2.0 * Math.PI);
        double longRadians = x/radius;
        double longDegrees = Math.toDegrees(longRadians);
        
        /* The user could have panned around the world a lot of times.
        Lat long goes from -180 to 180.  So every time a user gets 
        to 181 we want to subtract 360 degrees.  Every time a user
        gets to -181 we want to add 360 degrees. */
           
        double rotations = Math.floor((longDegrees + 180)/360);
        double longitude = longDegrees - (rotations * 360.0);
        return longitude;
        
    }
    
    public int latToTileY(double latitudeDegrees, int zoom) {
        int tiles = (int) Math.pow(2, zoom);
        int circumference = GOOG_PIXELS * tiles;
        double radius = circumference / (2.0 * Math.PI);
        double latitude = Math.toRadians(latitudeDegrees);
        double y = radius/2.0 * 
                Math.log( (1.0 + Math.sin(latitude)) /
                          (1.0 - Math.sin(latitude)) );
        int falseeasting = -1 * (circumference / 2);
        return (int) y + falseeasting + BITMAP_ORIGIN[17-zoom].y;
    }
    
    public double tileYToLat(int y, int zoom) {
        int tiles = (int) Math.pow(2, zoom);
        int circumference = GOOG_PIXELS * tiles;
        double radius = circumference / (2.0 * Math.PI);
        double latitude = (Math.PI / 2)
                - (2 * Math.atan(Math.exp(-1.0 * y / radius)));
        return Math.toDegrees(latitude);
        
    }
    
    public static int lngToX(double lng, int zoom) {
        return (int) (GOOG_OFFSET + GOOG_RADIUS * Math.toRadians(lng)) >> zoom;
    }
    
    public static int latToY(double lat, int zoom) {
        return (int) (GOOG_OFFSET - GOOG_RADIUS
                * Math.log((1 + Math.sin(Math.toRadians(lat))) / (1 - Math.sin(Math.toRadians(lat)))) / 2) >> zoom;
    }
    
    public static double xToLng(int x, int zoom) {
        return Math.toDegrees(((x<<zoom)-GOOG_OFFSET)/GOOG_RADIUS);
    }
    
    public static double yToLat(int y, int zoom) {
        return Math.toDegrees(Math.PI / 2 - 2 * Math.atan(Math.exp(((y<<zoom) - GOOG_OFFSET) / GOOG_RADIUS)));
    }
    
    public static int xToTileX(int x) {
        return x / GOOG_PIXELS;
    }
    
    public static int yToTileY(int y) {
        return y / GOOG_PIXELS;
    }

    public static int tileXToX(int tileX) {
        return tileX * GOOG_PIXELS;
    }

    public static int tileYToY(int tileY) {
        return tileY * GOOG_PIXELS;
    }

    public static int tilesAtZoom(int zoom) {
        return NUM_TILES[zoom];
    }
    
    public static String getSatelliteRef(double lat, double lng, int zoom) {
        Point tileXY = new Point(lngToX(lng, zoom), latToY(lat, zoom));
        int invZoom  = 17 - zoom;
        int stepSize = 1 << (17 - zoom);
        int currentX = 0;
        int currentY = 0;

        StringBuffer satString = new StringBuffer(zoom);
        satString.append("t");

        for (int i = 0; i < invZoom; i++) {
           stepSize >>= 1;

           if ((currentY + stepSize) > tileXY.y) {
              if ((currentX + stepSize) > tileXY.x) {
                 satString.append('q');
              }
              else {
                 currentX += stepSize;
                 satString.append('r');
              }
           }
           else {
              currentY += stepSize;

              if ((currentX + stepSize) > tileXY.x) {
                 satString.append('t');
              }
              else {
                 currentX += stepSize;
                 satString.append('s');
              }
           }
        }

        return satString.toString();
     }
}
