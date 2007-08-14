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
        mapSlidingWindow = new SlidingWindow(DEFAULT_CENTER, SIZE, DEFAULT_ZOOM);
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mousedownPoint = e.getPoint();
                originalPixelCenter = mapSlidingWindow.getGlobalPixelCenter();
                
                if(e.getClickCount() == 2) {
                    needsUpdate = true;
                    //mapSlidingWindow.zoomIn();
                    map.zoomIn();
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
        
            map.drawAll(mapSlidingWindow, dbg2, this);
            
    }

    private Image createImage(Dimension size) {
        return new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
    }
}
