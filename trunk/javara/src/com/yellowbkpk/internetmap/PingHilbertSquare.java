package com.yellowbkpk.internetmap;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.sun.imageio.plugins.png.PNGImageWriter;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;

/**
 * @author Ian Dees
 * 
 */
public class PingHilbertSquare extends Applet {

    private int overallPixelWidth = 1024;

    private int level = 4;

    private int sides = (int) Math.pow(2, level);

    private int squareWidth = overallPixelWidth / sides;

    private int numberSquaresOnSide = overallPixelWidth / squareWidth;

    private int smallSquareWidth = squareWidth / sides;
    
    private BufferedImage image;
    
    long step = 0;
    
    long maxStep = (sides * sides) * (sides * sides);
    
    boolean drawSquare = true;
    
    boolean saveSquare = true;

    private Map<String, Double> mapOfAddresses = new HashMap<String, Double>();

    private double maxParseDouble = -1.0;

    private double minParseDouble = 10.0;

    public void init() {
        image = new BufferedImage(overallPixelWidth, overallPixelWidth, BufferedImage.TYPE_INT_RGB);
        
        this.addMouseListener(new MouseAdapter () {

            public void mouseClicked(MouseEvent arg0) {}
            
        });
        
        try {
            // Read the whole file into a string
            BufferedReader r = new BufferedReader(new FileReader(new File(gg)));
            String t = "";
            while ((t = r.readLine()) != null) {
                String[] strings = t.split("\t");
                if ("-1".equals(strings[1])) {
                    // Don't do anything because the host didn't respond
                } else {
                    double parseDouble = Double.parseDouble(strings[2]);
                    if(parseDouble > maxParseDouble) {
                        maxParseDouble = parseDouble;
                    }
                    
                    if(parseDouble < minParseDouble) {
                        minParseDouble = parseDouble;
                    }
                    
                    mapOfAddresses.put(strings[0], parseDouble);
                }
            }
            r.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Max rtt avg = " + maxParseDouble);
        System.out.println("Min rtt avg = " + minParseDouble);
        maxParseDouble = 200;
        System.out.println("Adjusting max to " + maxParseDouble);
        
        makeBitmap();
        if (drawSquare) {
            JLabel scrolled = new JLabel(new ImageIcon(image));
            scrolled.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            JScrollPane scroller = new JScrollPane(scrolled);
            scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            add(scroller);
            resize(512, 512 + 10);
        }

        if (saveSquare) {
            saveBitmap();
        }
    }
    
    private void saveBitmap() {
        ImageWriterSpi imageWriterSPI = new PNGImageWriterSpi();
        ImageWriter iw = new PNGImageWriter(imageWriterSPI);
        try {
            ImageOutputStream fileOutput = ImageIO.createImageOutputStream(new File("graphout."+(System.currentTimeMillis()/100)+".png"));
            iw.setOutput(fileOutput);
            iw.write(image);
            fileOutput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void makeBitmap() {
        Graphics g = image.getGraphics();
        
        // Big squares
        for (int xS = 0; xS < numberSquaresOnSide; xS++) {
            int bigSquarePixelX = (xS * squareWidth);
            for (int yS = 0; yS < numberSquaresOnSide; yS++) {
                int bigSquarePixelY = (yS * squareWidth);

                int classA = HilbertMap.getVal(xS, yS);
                // int xLim = currentX + squareWidth;
                // int yLim = currentY + squareWidth;

                // Little squares
                for (int xL = 0; xL < numberSquaresOnSide; xL++) {
                    // int currentSmallX = currentX + (xL * smallSquareWidth);
                    for (int yL = 0; yL < numberSquaresOnSide; yL++) {
                        // System.err.println("[" + xS + "," + yS + "] and [" +
                        // xL + "," + yL + "]");
                        int classB = HilbertMap.getVal(xL, yL);
                        // int currentSmallY = currentY + (yL *
                        // smallSquareWidth);

                        int smallSquarePixelX = (xS * squareWidth) + (xL * smallSquareWidth);
                        int smallSquarePixelY = (yS * squareWidth) + (yL * smallSquareWidth);
                        
                        String addr = ff + classA + "." + classB;
                        
                        Double double1 = mapOfAddresses.get(addr);
                        Color color;
                        if (double1 == null) {
                            color = Color.black;
                        } else {
                            double avgTime = double1.doubleValue();
                            color = Color.getHSBColor((float) ((avgTime - minParseDouble) / maxParseDouble), 1.0f,
                                    1.0f);
                        }

                        g.setColor(color);
                        g.fillRect(smallSquarePixelX, smallSquarePixelY, smallSquareWidth,
                                        smallSquareWidth);

                        // Reset the color
                        g.setColor(Color.black);
                        
                        // Increment the counter and update the status bar
                        //step++;
                        //updateStatus();
                    }
                }
                // Draw the big square's box
                g.setFont(new Font("Arial", Font.PLAIN, 9));
                g.setColor(Color.darkGray);
                g.drawString(""+classA, bigSquarePixelX+2, bigSquarePixelY+8);
                g.setColor(Color.black);
                g.drawRect(bigSquarePixelX, bigSquarePixelY, squareWidth, squareWidth);
            }
        }
    }

}