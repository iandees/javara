package com.yellowbkpk.maps;

import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;

/**
 * @author Ian Dees
 *
 */
public class GGroundOverlay implements GOverlay {

    private ImageIcon image;
    private GLatLngBounds myBounds;

    /**
     * @param imageURL
     * @param bounds
     */
    public GGroundOverlay(String imageURL, GLatLngBounds bounds) {
        URL u = null;
        try {
            System.out.println(imageURL);
            u = new URL(imageURL);
            System.out.println(u);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        image = new ImageIcon(u);
        System.out.println(image.getImageLoadStatus());
        myBounds = bounds;
    }

    public void drawOverlay(Graphics2D dbg2, Point nwPoint, int zoom, GLatLngBounds viewBounds,
            ImageObserver imageObserver) {
        int dx1 = GoogleMapUtilities.lngToX(myBounds.getNorthwest().getLongitude(), 17 - zoom) - nwPoint.x;
        int dy1 = GoogleMapUtilities.latToY(myBounds.getNorthwest().getLatitude(), 17 - zoom) - nwPoint.y;
        int dx2 = GoogleMapUtilities.lngToX(myBounds.getSoutheast().getLongitude(), 17 - zoom) - nwPoint.x;
        int dy2 = GoogleMapUtilities.latToY(myBounds.getSoutheast().getLatitude(), 17 - zoom) - nwPoint.y;

        int sx1 = 0;
        int sy1 = 0;
        int sx2 = image.getIconWidth();
        int sy2 = image.getIconHeight();
        
        if (image.getImageLoadStatus() == MediaTracker.COMPLETE) {
            dbg2.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, imageObserver);
        } else {
            System.out.println(image.getImageLoadStatus());
        }
    }

    public boolean shouldDraw(GLatLngBounds view) {
        return (view.contains(myBounds));
    }

}
