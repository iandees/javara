package com.yellowbkpk.internetmap;
import java.applet.Applet;
import java.awt.Color;
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
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

import com.sun.imageio.plugins.png.PNGImageWriter;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;

/**
 * @author Ian Dees
 * 
 */
public class HilbertSquares extends Applet {

    private static final String FILE_BASE = "D:\\cygwin\\home\\Ian Dees\\whois\\";

    private static final String NO_FILE_FOUND = "NOFILE";

    private Set<SquareInfo> squares = new HashSet<SquareInfo>();

    private int overallPixelWidth = 512;

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

    public void init() {
        image = new BufferedImage(overallPixelWidth, overallPixelWidth, BufferedImage.TYPE_INT_RGB);
        
        this.addMouseListener(new MouseAdapter () {

            public void mouseClicked(MouseEvent arg0) {
                int x = arg0.getX();
                int y = arg0.getY();
                
                // Make sure we're in the map
                if(x < 0 || x > overallPixelWidth) {
                    return;
                }
                
                if(y < 0 || y > overallPixelWidth) {
                    return;
                }
                
                int clickedBigSquareX = x / squareWidth;
                int clickedBigSquareY = y / squareWidth;
                int clickedSmallSquareX = (x / smallSquareWidth) - (clickedBigSquareX * sides);
                int clickedSmallSquareY = (y / smallSquareWidth) - (clickedBigSquareY * sides);
                
                System.err.println("big: [" + clickedBigSquareX + "," + clickedBigSquareY + "] small: [" + clickedSmallSquareX + "," + clickedSmallSquareY + "]");
                
                int classA = HilbertMap.getVal(clickedBigSquareX, clickedBigSquareY);
                int classB = HilbertMap.getVal(clickedSmallSquareX, clickedSmallSquareY);
                String filename = classA + "\\" + classA + "." + classB + ".0.0";
                String wholeFile = "";
                
                wholeFile = readWholeFile(filename);
                
                if(wholeFile.equals(NO_FILE_FOUND)) {
                    filename = classA + "\\" + classA + ".0.0.0";
                    wholeFile = readWholeFile(filename);
                }
                
                System.err.println(wholeFile);
            }
            
        });
        
        makeBitmap();
        if (drawSquare) {
            resize(overallPixelWidth, overallPixelWidth + 10);
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

    private String readWholeFile(String filename) {
        filename = FILE_BASE + filename;
        System.err.println(filename);
        
        StringBuffer b = new StringBuffer();
        try {
            // Read the whole file into a string
            BufferedReader r = new BufferedReader(new FileReader(new File(filename)));
            b = new StringBuffer();
            String t = "";
            while ((t = r.readLine()) != null) {
                b.append(t + "\n");
            }
            r.close();
        } catch (FileNotFoundException e) {
            return NO_FILE_FOUND;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return b.toString();
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

                        String filename = classA + "\\" + classA + "." + classB + ".0.0";

                        try {
                            // See if the directory is empty (if it is, we can
                            // just skip it)
                            File directory = new File(FILE_BASE + "\\" + classA);
                            if (directory.list().length > 0) {

                                // Read the whole file into a string
                                BufferedReader r = new BufferedReader(new FileReader(new File(FILE_BASE + filename)));
                                StringBuffer b = new StringBuffer();
                                String t = "";
                                while ((t = r.readLine()) != null) {
                                    b.append(t + "\n");
                                }
                                r.close();

                                String wholeFile = b.toString();

                                // Do some checks for the properties of this IP
                                // address range
                                int cidrIndex = 0;
                                if (wholeFile.indexOf("No match found for ") > 0) {
                                    // No match = red square
                                    g.setColor(Color.red);
                                    g.fillRect(smallSquarePixelX, smallSquarePixelY, smallSquareWidth,
                                                    smallSquareWidth);
                                } else {
                                    if (wholeFile.indexOf("RESERVED-") > 0) {
                                        if ((cidrIndex = wholeFile.indexOf("CIDR:")) > 0) {
                                            int slashIndex = wholeFile.indexOf("/", cidrIndex) + 1;

                                            int netblockSize = Integer.valueOf(wholeFile.substring(slashIndex,
                                                    slashIndex + 1));
                                            if (netblockSize <= 8) {
                                                g.setColor(Color.blue);
                                                g.fillRect(bigSquarePixelX, bigSquarePixelY, squareWidth, squareWidth);
                                                // Since this is an /8 block, just
                                                // skip over the whole big block by
                                                // skipping the smaller blocks
                                                xL = numberSquaresOnSide;
                                                yL = numberSquaresOnSide;
                                            }
                                        } else {
                                            // Reserved net block
                                            g.setColor(Color.blue);
                                            g.fillRect(smallSquarePixelX, smallSquarePixelY, smallSquareWidth,
                                                    smallSquareWidth);
                                        }
                                    } else if ((cidrIndex = wholeFile.indexOf("CIDR:")) > 0) {
                                        int slashIndex = wholeFile.indexOf("/", cidrIndex) + 1;

                                        int netblockSize = Integer.valueOf(wholeFile.substring(slashIndex,
                                                slashIndex + 1));
                                        if (netblockSize == 8) {
                                            g.setColor(Color.green);
                                            g.fillRect(bigSquarePixelX, bigSquarePixelY, squareWidth, squareWidth);
                                            // Since this is an /8 block, just
                                            // skip over the whole big block by
                                            // skipping the smaller blocks
                                            xL = numberSquaresOnSide;
                                            yL = numberSquaresOnSide;
                                        }
                                    } else {
                                        // We have a file, just no special color
                                        // for its contents
                                        g.setColor(Color.orange);
                                        g.fillRect(smallSquarePixelX, smallSquarePixelY, smallSquareWidth,
                                                smallSquareWidth);
                                    }
                                }
                            } else {
                                // The directory doesn't have anything in it, so none of the /8 block will be there
                                g.setColor(Color.lightGray);
                                g.fillRect(bigSquarePixelX, bigSquarePixelY, squareWidth, squareWidth);

                                xL = numberSquaresOnSide;
                                yL = numberSquaresOnSide;
                            }
                        } catch (FileNotFoundException e) {
                            g.setColor(Color.lightGray);
                            g.fillRect(smallSquarePixelX, smallSquarePixelY, smallSquareWidth, smallSquareWidth);
                            // System.err.println("Couldn't open file " +
                            // FILE_BASE + filename + "!");
                        } catch (IOException e) {
                            g.setColor(Color.lightGray);
                            g.fillRect(smallSquarePixelX, smallSquarePixelY, smallSquareWidth, smallSquareWidth);
                            // System.err.println("Couldn't close file " +
                            // FILE_BASE + filename + "!");
                        }

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

    private void updateStatus() {
        // TODO Auto-generated method stub
        
    }

    public void paint(Graphics g) {
        if(drawSquare) {
            g.drawImage(image, 0, 0, this);
        }
    }
}