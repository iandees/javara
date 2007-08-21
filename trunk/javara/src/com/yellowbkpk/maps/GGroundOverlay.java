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
    private URL myURL;

    /**
     * @param imageURL
     * @param bounds
     */
    public GGroundOverlay(String imageURL, GLatLngBounds bounds) {
        myURL = null;
        try {
            myURL = new URL(imageURL);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myBounds = bounds;
    }

    public void drawOverlay(Graphics2D dbg2, Point nwPoint, int zoom, GLatLngBounds viewBounds,
            ImageObserver imageObserver) {
        if (image == null) {
            System.out.println("Gotta load " + myURL + ".");
            image = new ImageIcon(myURL);
            System.out.println(image.getImageLoadStatus());
        }
        
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
        }
    }

    public boolean shouldDraw(GLatLngBounds view) {
        return (view.contains(myBounds));
    }

}
