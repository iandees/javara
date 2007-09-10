package com.yellowbkpk.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.yellowbkpk.maps.gui.GoogleMapUtilities;
import com.yellowbkpk.maps.gui.MapDisplayFrame;
import com.yellowbkpk.maps.gui.MapDisplayPanel;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;
import com.yellowbkpk.util.gps.GPSChangeListener;
import com.yellowbkpk.util.gps.GPSReader;

public class MapsMain {

    private static GOverlayLayer layer;
    private static int coverageZoom = 5;
    protected static HashMap<String,Thread> downloads;

    public static void main(String[] args) {
        /*// Modify system properties
        Properties sysProperties = System.getProperties();
        // Specify proxy settings
        sysProperties.put("proxyHost", "3.20.128.6");
        sysProperties.put("proxyPort", "88");
        sysProperties.put("proxySet",  "true");*/
        
        downloads = new HashMap<String, Thread>();
        
        final JFrame speedoFrame = new JFrame();
        final SpeedometerPanel speedometerPanel = new SpeedometerPanel();
        speedoFrame.setContentPane(speedometerPanel);
        speedoFrame.setVisible(true);
        
        final Map map = new Map();
        final MapDisplayPanel panel = new MapDisplayPanel(map);
        final MapDisplayFrame frame = new MapDisplayFrame(panel);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        final JCheckBoxMenuItem showCoverage = new JCheckBoxMenuItem("Show Coverage");
        showCoverage.setState(false);
        showCoverage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showCoverage.getState()) {
                    showCoverage.setState(true);
                    layer.setVisible(true);
                } else {
                    showCoverage.setState(false);
                    layer.setVisible(false);
                }
            }
        });
        fileMenu.add(showCoverage);
        JMenuItem decreaseCoverageZoomItem = new JMenuItem("Increase Coverage Zoom");
        decreaseCoverageZoomItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decreaseCoverageZoom();
            }
        });
        fileMenu.add(decreaseCoverageZoomItem);
        JMenuItem increaseCoverageZoomItem = new JMenuItem("Decrease Coverage Zoom");
        increaseCoverageZoomItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                increaseCoverageZoom();
            }
        });
        fileMenu.add(increaseCoverageZoomItem );
        fileMenu.addSeparator();
        final JCheckBoxMenuItem enableDragPanItem = new JCheckBoxMenuItem("Enable Drag Pan");
        enableDragPanItem.setState(true);
        enableDragPanItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (enableDragPanItem.getState()) {
                    enableDragPanItem.setState(true);
                    panel.setDragPanEnabled(true);
                } else {
                    enableDragPanItem.setState(false);
                    panel.setDragPanEnabled(false);
                }
            }
        });
        fileMenu.add(enableDragPanItem);
        final JCheckBoxMenuItem enableDownloadDraggerItem = new JCheckBoxMenuItem("Enable Drag Download");
        enableDownloadDraggerItem.setState(false);
        enableDownloadDraggerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (enableDownloadDraggerItem.getState()) {
                    enableDownloadDraggerItem.setState(true);
                } else {
                    enableDownloadDraggerItem.setState(false);
                }
            }
        });
        fileMenu.add(enableDownloadDraggerItem);
        final JCheckBoxMenuItem enableNetConnectionItem = new JCheckBoxMenuItem("Enable Net Connection");
        enableNetConnectionItem.setState(false);
        enableNetConnectionItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (enableNetConnectionItem.getState()) {
                    enableNetConnectionItem.setState(true);
                    panel.setNetConnectionEnabled(true);
                } else {
                    enableNetConnectionItem.setState(false);
                    panel.setNetConnectionEnabled(false);
                }
            }
        });
        fileMenu.add(enableNetConnectionItem);
        menuBar.add(fileMenu );
        frame.setJMenuBar(menuBar);
        
        panel.setCenter(new GLatLng(43.0, -90.0), 7);
        frame.start();

        final GLatLng dotLatLng = new GLatLng(43.0, -90.0);
        final GDirectedMarker dot = new GDirectedMarker(dotLatLng);
        map.addOverlay(dot);
        
        final List<GLatLng> linePoints = new ArrayList<GLatLng>();
        final GPolyline line = new GPolyline(linePoints, Color.red, 5);
        map.addOverlay(line);
        
        // Scan a cache directory for all the images in it
        layer = new GOverlayLayer();
        map.addOverlay(layer);
        //updateCoverageOverlay();
        
        panel.addMapMouseListener(new MapMouseListener() {
            public void mouseClicked(GLatLng latLng, int clickCount) {
            }
            public void mouseDragged(final GLatLng latLng) {
                if (enableNetConnectionItem.getState()) {
                    GLatLng ll = new GLatLng(latLng.getLatitude(), latLng.getLongitude());
                    int globalX = GoogleMapUtilities.lngToX(ll.getLongitude(), coverageZoom);
                    int globalY = GoogleMapUtilities.latToY(ll.getLatitude(), coverageZoom);
                    int tileX = GoogleMapUtilities.xToTileX(globalX);
                    int tileY = GoogleMapUtilities.yToTileY(globalY);
                    final File f = new File("cache/" + coverageZoom + "/" + tileX + "-" + tileY + ".png");
                    if (!f.exists() && !downloads.containsKey(f.getAbsolutePath())) {
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                GLatLng ll = new GLatLng(latLng.getLatitude(), latLng.getLongitude());
                                int globalX = GoogleMapUtilities.lngToX(ll.getLongitude(), coverageZoom);
                                int globalY = GoogleMapUtilities.latToY(ll.getLatitude(), coverageZoom);
                                int tileX = GoogleMapUtilities.xToTileX(globalX);
                                int tileY = GoogleMapUtilities.yToTileY(globalY);
                                int tileGlobalX = GoogleMapUtilities.tileXToX(tileX);
                                int tileGlobalY = GoogleMapUtilities.tileYToY(tileY);
                                

                                double nwLng = GoogleMapUtilities.xToLng(tileGlobalX, coverageZoom);
                                double seLng = GoogleMapUtilities.xToLng(tileGlobalX+256, coverageZoom);
                                double nwLat = GoogleMapUtilities.yToLat(tileGlobalY, coverageZoom);
                                double seLat = GoogleMapUtilities.yToLat(tileGlobalY+256, coverageZoom);
                                GRectangle r = new GRectangle(new GLatLng(nwLat, nwLng), new GLatLng(seLat, seLng));
                                layer.addOverlay(r);

                                final String urlString = "http://mt3.google.com/mt?n=404&v=w2.60&x=" + tileX + "&y=" + tileY
                                        + "&zoom=" + coverageZoom;
                                URL url = null;
                                try {
                                    url = new URL(urlString);
                                } catch (MalformedURLException e1) {
                                    e1.printStackTrace();
                                }
                                ImageIcon i = new ImageIcon(url);
                                // This chunk here saves the image icon to a
                                // file
                                while (i.getImageLoadStatus() == MediaTracker.LOADING) {
                                    try {
                                        Thread.sleep(150);
                                        System.out.println("Sleeping while loading.");
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (i.getImageLoadStatus() == MediaTracker.COMPLETE) {
                                    BufferedImage bI = new BufferedImage(i.getIconWidth(), i.getIconWidth(),
                                            BufferedImage.TYPE_INT_ARGB);
                                    Graphics bIGraphics = bI.getGraphics();
                                    bIGraphics.drawImage(i.getImage(), 0, 0, null);
                                    bIGraphics.dispose();
                                    try {
                                        f.mkdirs();
                                        ImageIO.write(bI, "PNG", f);
                                        System.out.println("done downloading " + f);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    r.setFillColor(new Color(0,255,0,50));
                                } else {
                                    System.out.println("Can't load image " + url + ". (" + i.getImageLoadStatus()
                                            + ")");
                                }
                            }
                        });
                        t.start();
                        downloads.put(f.getAbsolutePath(), t);
                    }
                }
            }
        });
        

        GPSReader gps = null;
        try {
            gps = new GPSReader("COM21");
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
            e.printStackTrace();
        }
        gps.addGPSChangeListener(new GPSChangeListener() {
            public void locationUpdated(double lat, double lng, double speed, double course) {
                dot.setCenter(new GLatLng(lat, lng));
                dot.setDirection(course);
                line.addPoint(new GLatLng(lat, lng), Color.getHSBColor((float) (speed/90.0), 1f, 1f));
                speedometerPanel.setSpeed(speed);
            }
        });
         
    }

    protected static void increaseCoverageZoom() {
        if(coverageZoom + 1 < 17) {
            coverageZoom++;
            layer.clearOverlays();
            updateCoverageOverlay();
        }
    }

    protected static void decreaseCoverageZoom() {
        if(coverageZoom - 1 > 0) {
            coverageZoom--;
            layer.clearOverlays();
            updateCoverageOverlay();
        }
    }

    private static void updateCoverageOverlay() {
        File cacheDirToScan = new File("cache/" + coverageZoom);
        String[] list = cacheDirToScan.list();
        for (String string : list) {
            String[] split = string.split("-");
            if(split.length == 2) {
                int xForZoom = Integer.parseInt(split[0]);
                int yForZoom = Integer.parseInt(split[1].substring(0, split[1].length()-4));
                int globalX = GoogleMapUtilities.tileXToX(xForZoom);
                int globalY = GoogleMapUtilities.tileYToY(yForZoom);
                double nwLng = GoogleMapUtilities.xToLng(globalX, coverageZoom);
                double seLng = GoogleMapUtilities.xToLng(globalX+256, coverageZoom);
                double nwLat = GoogleMapUtilities.yToLat(globalY, coverageZoom);
                double seLat = GoogleMapUtilities.yToLat(globalY+256, coverageZoom);
                GRectangle r = new GRectangle(new GLatLng(nwLat, nwLng), new GLatLng(seLat, seLng));
                r.setFillColor(new Color(0,255,0,50));
                layer.addOverlay(r);
            }
        }
    }

}
