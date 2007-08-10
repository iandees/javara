package com.yellowbkpk.maps.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.yellowbkpk.maps.map.GLatLng;
import com.yellowbkpk.maps.map.Map;

public class MapDisplayPanel extends JPanel implements Runnable {

    private static final int TILE_SIZE = 256;
    private static final long TIME_PER_FRAME = 60;
    private static final Dimension SIZE = new Dimension(800, 600);
    private static final GLatLng DEFAULT_CENTER = new GLatLng(43, -90);
    private static final int DEFAULT_ZOOM = 4;
	protected static final double MAX_PAN_VELOCITY = 3.0;
    
    private Map map;
    private Image dbImage;
    private Thread animator;
    private boolean running;
    private Graphics dbg;
    protected Point mousedownPoint;
    protected Point originalPixelCenter;
    private SlidingWindow mapSlidingWindow;
    private ImageCache imageCache;
    private Image loadingTileImage;
    private boolean needsUpdate = false;;

    public MapDisplayPanel(Map m) {
        setPreferredSize(new Dimension(800,600));
        setFocusable(true);
        
        map = m;
        loadingTileImage = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = loadingTileImage.getGraphics();
        graphics.setColor(Color.gray);
        graphics.drawString("Loading...", 120, 128);
        imageCache = new ImageCache();
        mapSlidingWindow = new SlidingWindow(map, DEFAULT_CENTER, SIZE, DEFAULT_ZOOM);
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mousedownPoint = e.getPoint();
                originalPixelCenter = mapSlidingWindow.getGlobalPixelCenter();
                
                if(e.getClickCount() == 2) {
                    needsUpdate = true;
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
                needsUpdate = true;
            }
        });
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                dbImage = createImage(getSize());
                mapSlidingWindow.resize(getSize());
                needsUpdate = true;
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
                    needsUpdate = true;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					panVelocity += 0.2;

					if (panVelocity > MAX_PAN_VELOCITY) {
						panVelocity = MAX_PAN_VELOCITY;
					}

					GLatLng center = mapSlidingWindow.getCenter();
					GLatLng newCenter = new GLatLng(center.getLatitude()
							- panVelocity, center.getLongitude());

					mapSlidingWindow.setCenter(newCenter);
                    needsUpdate = true;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					panVelocity += 0.2;

					if (panVelocity > MAX_PAN_VELOCITY) {
						panVelocity = MAX_PAN_VELOCITY;
					}

					GLatLng center = mapSlidingWindow.getCenter();
					GLatLng newCenter = new GLatLng(center.getLatitude()
							, center.getLongitude() - panVelocity);

					mapSlidingWindow.setCenter(newCenter);
                    needsUpdate = true;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					panVelocity += 0.2;

					if (panVelocity > MAX_PAN_VELOCITY) {
						panVelocity = MAX_PAN_VELOCITY;
					}

					GLatLng center = mapSlidingWindow.getCenter();
					GLatLng newCenter = new GLatLng(center.getLatitude()
							, center.getLongitude() + panVelocity);

					mapSlidingWindow.setCenter(newCenter);
                    needsUpdate = true;
				} else if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
					mapSlidingWindow.zoomIn();
                    needsUpdate = true;
				} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					mapSlidingWindow.zoomOut();
                    needsUpdate = true;
				}
			}

			public void keyReleased(KeyEvent e) {
				panVelocity = 0.0;
			}

			public void keyTyped(KeyEvent e) {
			}
        });
    }

    public void addNotify() {
        super.addNotify();
        startAnimation();
    }

    private void startAnimation() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
            needsUpdate = true;
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
        dbg.fillRect(0, 0, dbImage.getWidth(this), dbImage.getHeight(this));

        drawField(dbg);
    }

    private void drawField(Graphics dbg2) {
            if(!needsUpdate) {
                return;
            }
        
            dbg2.setColor(Color.white);
            final int zoomLevel = (17-mapSlidingWindow.getZoom());
        
            // Get the northwest corner tile x and y coordinates
            int nwXPixels = GoogleMapUtilities.lngToX(mapSlidingWindow.getNorthwest().getLongitude(), zoomLevel);
            int nwYPixels = GoogleMapUtilities.latToY(mapSlidingWindow.getNorthwest().getLatitude(), zoomLevel);
            
            // Get the southeast corner tile x and y coordinates
            int seXPixels = GoogleMapUtilities.lngToX(mapSlidingWindow.getSoutheast().getLongitude(), zoomLevel);
            int seYPixels = GoogleMapUtilities.latToY(mapSlidingWindow.getSoutheast().getLatitude(), zoomLevel);
            
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
                    
                    dbg2.drawImage(image, localTilePixelX, localTilePixelY, this);
                    
                    //dbg2.drawRect(localTilePixelX, localTilePixelY, 256, 256);
                    //dbg2.drawString("("+x+","+y+")", localTilePixelX+1, localTilePixelY+11);
                }
            }
            
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }

    private void mapUpdate() {
        map.update();
    }
}
