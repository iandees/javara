package com.yellowbkpk.maps.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

import com.yellowbkpk.maps.GLatLngBounds;
import com.yellowbkpk.maps.GOverlay;
import com.yellowbkpk.maps.MapMouseListener;
import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;
import com.yellowbkpk.maps.map.MapUpdateListener;

public class MapDisplayPanel extends JPanel implements MapDisplayUpdateListener, ImageUpdateListener, MapUpdateListener {

    private static final int TILE_SIZE = 256;
    private static final Dimension SIZE = new Dimension(800, 600);
    private static final GLatLng DEFAULT_CENTER = new GLatLng(43, -90);
	protected static final double MAX_PAN_VELOCITY = 3.0;
    
    private Map map;
    private Image dbImage;
    private Graphics dbg;
    protected Point mousedownPoint;
    protected Point originalPixelCenter;
    private SlidingWindow mapSlidingWindow;
    private ImageCache imageCache;
    private Image loadingTileImage;
    private List<MapMouseListener> mapMouseListeners;
    private boolean initialized;

    public MapDisplayPanel(Map m) {
        setPreferredSize(new Dimension(800,600));
        setFocusable(true);
        mapMouseListeners = new ArrayList<MapMouseListener>();

        initialized = false;
        map = m;
        map.registerForUpdates(this);
        
        loadingTileImage = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = loadingTileImage.getGraphics();
        graphics.setColor(Color.gray);
        graphics.drawString("Loading...", 120, 128);
        
        imageCache = new ImageCache();
        imageCache.registerForUpdates(this);
        
        mapSlidingWindow = new SlidingWindow(DEFAULT_CENTER, SIZE);
        mapSlidingWindow.registerForUpdates(this);
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mousedownPoint = e.getPoint();
                originalPixelCenter = mapSlidingWindow.getGlobalPixelCenter();
            }

            public void mouseClicked(MouseEvent e) {
                Point nwPt = mapSlidingWindow.getNorthwestPoint();
                double lng = GoogleMapUtilities.xToLng(nwPt.x + e.getX(), 17-mapSlidingWindow.getZoom());
                double lat = GoogleMapUtilities.yToLat(nwPt.y + e.getY(), 17-mapSlidingWindow.getZoom());
                GLatLng clickLL = new GLatLng(lat, lng);
                
                if(e.getClickCount() == 1) {
                    for (MapMouseListener listener : mapMouseListeners) {
                        listener.mouseClicked(clickLL, 1);
                    }
                } else if(e.getClickCount() == 2) {
                    for (MapMouseListener listener : mapMouseListeners) {
                        listener.mouseClicked(clickLL, 2);
                    }
                    mapSlidingWindow.zoomIn();
                }
                
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousedownPoint.x;
                int dy = e.getY() - mousedownPoint.y;
                int newPixelCenterX = originalPixelCenter.x - dx;
                int newPixelCenterY = originalPixelCenter.y - dy;
                Point newPixelCenter = new Point(newPixelCenterX, newPixelCenterY);
                
                mapSlidingWindow.setPixelCenter(newPixelCenter);
            }
        });
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                dbImage = createImage(getSize());
                mapSlidingWindow.resize(getSize());
            }
        });
        
        addKeyListener(new KeyAdapter() {
			private double panVelocity = 0.0;

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					panVelocity += 0.2;

					if (panVelocity > MAX_PAN_VELOCITY) {
						panVelocity = MAX_PAN_VELOCITY;
					}

					GLatLng center = mapSlidingWindow.getCenter();
					GLatLng newCenter = new GLatLng(center.getLatitude()
							+ panVelocity, center.getLongitude());

					mapSlidingWindow.setCenter(newCenter);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					panVelocity += 0.2;

					if (panVelocity > MAX_PAN_VELOCITY) {
						panVelocity = MAX_PAN_VELOCITY;
					}

					GLatLng center = mapSlidingWindow.getCenter();
					GLatLng newCenter = new GLatLng(center.getLatitude()
							- panVelocity, center.getLongitude());

					mapSlidingWindow.setCenter(newCenter);
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					panVelocity += 0.2;

					if (panVelocity > MAX_PAN_VELOCITY) {
						panVelocity = MAX_PAN_VELOCITY;
					}

					GLatLng center = mapSlidingWindow.getCenter();
					GLatLng newCenter = new GLatLng(center.getLatitude()
							, center.getLongitude() - panVelocity);

					mapSlidingWindow.setCenter(newCenter);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					panVelocity += 0.2;

					if (panVelocity > MAX_PAN_VELOCITY) {
						panVelocity = MAX_PAN_VELOCITY;
					}

					GLatLng center = mapSlidingWindow.getCenter();
					GLatLng newCenter = new GLatLng(center.getLatitude()
							, center.getLongitude() + panVelocity);

					mapSlidingWindow.setCenter(newCenter);
				} else if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
					mapSlidingWindow.zoomIn();
				} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					mapSlidingWindow.zoomOut();
				}
			}

			public void keyReleased(KeyEvent e) {
				panVelocity = 0.0;
			}

			public void keyTyped(KeyEvent e) {
			}
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    private void mapRender() {
        if (dbImage == null) {
            dbImage = createImage(SIZE);
        }
        
        dbg = dbImage.getGraphics();

        if(initialized) {
            dbg.clearRect(0, 0, getWidth(), getHeight());
            drawField((Graphics2D) dbg);
        }
    }

    private void drawField(Graphics2D dbg2) {

        dbg2.setColor(Color.white);
        final int zoomLevel = (17-mapSlidingWindow.getZoom());

        // Get the northwest corner tile x and y coordinates
        //int nwXPixels = GoogleMapUtilities.lngToX(mapSlidingWindow.getNorthwest().getLongitude(), zoomLevel);
        int nwXPixels = mapSlidingWindow.getNorthwestPoint().x;
        //int nwYPixels = GoogleMapUtilities.latToY(mapSlidingWindow.getNorthwest().getLatitude(), zoomLevel);
        int nwYPixels = mapSlidingWindow.getNorthwestPoint().y;

        // Get the southeast corner tile x and y coordinates
        //int seXPixels = GoogleMapUtilities.lngToX(mapSlidingWindow.getSoutheast().getLongitude(), zoomLevel);
        int seXPixels = mapSlidingWindow.getSoutheastPoint().x;
        //int seYPixels = GoogleMapUtilities.latToY(mapSlidingWindow.getSoutheast().getLatitude(), zoomLevel);
        int seYPixels = mapSlidingWindow.getSoutheastPoint().y;

        // Get the northwest tile number
        //Point nwTile = GoogleMapUtilities.getTileCoordinate(mapSlidingWindow.getNorthwest().getLatitude(), mapSlidingWindow.getNorthwest().getLongitude(), zoomLevel);
        int nwTileX = GoogleMapUtilities.xToTileX(mapSlidingWindow.getNorthwestPoint().x);
        int nwTileY = GoogleMapUtilities.yToTileY(mapSlidingWindow.getNorthwestPoint().y);

        // Get the southeast tile number
        //Point seTile = GoogleMapUtilities.getTileCoordinate(mapSlidingWindow.getSoutheast().getLatitude(), mapSlidingWindow.getSoutheast().getLongitude(), zoomLevel);
        int seTileX = GoogleMapUtilities.xToTileX(mapSlidingWindow.getSoutheastPoint().x);
        int seTileY = GoogleMapUtilities.yToTileY(mapSlidingWindow.getSoutheastPoint().y);

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

                dbg2.drawImage(image, localTilePixelX, localTilePixelY, this);

                dbg2.drawRect(localTilePixelX, localTilePixelY, 256, 256);
                dbg2.drawString("("+x+","+y+")", localTilePixelX+1, localTilePixelY+11);
            }
        }

        Collection<GOverlay> overlays = map.getOverlays(getGBounds());
        for (GOverlay overlay : overlays) {
            Rectangle bounds = new Rectangle(mapSlidingWindow.getNorthwestPoint().x, mapSlidingWindow.getNorthwestPoint().y, getWidth(), getHeight());
            overlay.drawOverlay(dbg2, bounds, mapSlidingWindow.getZoom(), getGBounds(), this);
        }

        // Draw a center cross hatch
        Point globalPixelCenter = mapSlidingWindow.getGlobalPixelCenter();
        dbg2.setColor(Color.red);
        dbg2.drawLine(globalPixelCenter.x - nwXPixels, globalPixelCenter.y - nwYPixels - 5, globalPixelCenter.x - nwXPixels, globalPixelCenter.y - nwYPixels + 5);
        dbg2.drawLine(globalPixelCenter.x - nwXPixels - 5, globalPixelCenter.y - nwYPixels, globalPixelCenter.x - nwXPixels + 5, globalPixelCenter.y - nwYPixels);
    }

    /**
     * @return
     */
    private GLatLngBounds getGBounds() {
        return mapSlidingWindow.getGBounds();
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }

    public void setCenter(GLatLng latLng) {
        initialized = true;
        mapSlidingWindow.setCenter(latLng);
    }

    public void addMapMouseListener(MapMouseListener listener) {
        mapMouseListeners.add(listener);
    }

    /**
     * @param latLng
     * @param i
     */
    public void setCenter(GLatLng latLng, int zoom) {
        initialized = true;
        mapSlidingWindow.setCenter(latLng, zoom);
    }

    public void needsRedraw() {
        mapRender();
        repaint();
    }

    public void imageUpdated() {
        mapRender();
        repaint();
    }

    public void mapUpdated() {
        mapRender();
        repaint();
    }
}
