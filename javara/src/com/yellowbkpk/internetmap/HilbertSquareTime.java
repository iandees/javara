package com.yellowbkpk.internetmap;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class HilbertSquareTime extends Applet {

    private static final String FILE_BASE = "C:\\Documents and Settings\\Ian Dees\\Desktop\\imwebcamdata\\";

    private static final String NO_FILE_FOUND = "NOFILE";

    private static final int ROOM_FOR_SCALE = 20;

    private Set<SquareInfo> squares = new HashSet<SquareInfo>();

    private int overallPixelWidth = 1024;

    private int level = 4;

    private int sides = (int) Math.pow(2, level);

    private int squareWidth = overallPixelWidth / sides;

    private int numberSquaresOnSide = overallPixelWidth / squareWidth;

    private int smallSquareWidth = squareWidth / sides;
    
    private int reallySmallSquareWidth = smallSquareWidth / sides;

    private BufferedImage image;

    long step = 0;

    long maxStep = (sides * sides) * (sides * sides);

    boolean drawSquare = true;

    boolean saveSquare = true;

    private double mostOnOneBlock;

    public void init() {
        image = new BufferedImage(overallPixelWidth, overallPixelWidth + ROOM_FOR_SCALE, BufferedImage.TYPE_INT_RGB);

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
        drawScale();

        if (drawSquare) {
            resize(overallPixelWidth, overallPixelWidth + 10);
        }

        if (saveSquare) {
            saveBitmap();
        }
    }

    private void fixBoxRotations() {
        for(int x = 0; x < numberSquaresOnSide; x++) {
            for(int y = 0; y < numberSquaresOnSide; y++) {
                fixBigBoxRotation(x, y);
            }
        }
    }

    private void fixBigBoxRotation(int x, int y) {
        BufferedImage subimage = image.getSubimage(0, 0, 566, 566);
        AffineTransform rotateImageTransform = AffineTransform.getRotateInstance(Math.toRadians(90.0), 128, 128);
        AffineTransformOp rotateImageOp = new AffineTransformOp(rotateImageTransform, AffineTransformOp.TYPE_BICUBIC);
        BufferedImage subOutputImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        rotateImageOp.filter(subimage, subOutputImage);
        Graphics g = image.getGraphics();
        g.drawImage(subOutputImage, 0, 0, this);
    }

    private void drawScale() {
        Graphics g = image.getGraphics();
        for(int i = 0; i < overallPixelWidth; i++) {
            int colorValue = linearNormalizeTo(i, overallPixelWidth, 765);
            g.setColor(generateColor(colorValue));
            g.drawLine(i, overallPixelWidth, i, overallPixelWidth+(ROOM_FOR_SCALE));
        }
        drawScaleText();
    }

    private void drawScaleText() {
        Graphics g = image.getGraphics();
        for(int i = 0; i < overallPixelWidth; i++) {
            int valueValue = linearNormalizeTo(i, overallPixelWidth, (int) mostOnOneBlock);
            if(i % (overallPixelWidth / 5) == 0) {

                g.setColor(Color.DARK_GRAY);
                g.drawString("" + valueValue, i, overallPixelWidth + 10);
            }
        }
    }

    private int linearNormalizeTo(int val, int max, int normalValue) {
        return (int) (((double)val/(double)max)* (double) normalValue);
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

        // White Background
        g.setColor(Color.white);
        g.fillRect(0, 0, overallPixelWidth, overallPixelWidth);

        System.err.println("Zeroing data...");
        // Zero out the data storage
        double[][] data = new double[256][256];
        for(int i = 0; i < 256; i++) {
            for(int j = 0; j < 256; j++) {
                    data[i][j] = 0;
            }
        }
        System.err.println("... done.");
        
        System.err.println("Reading points...");
        long nPoints = 0;
        for(int month = 11; month <= 12; month++) {
            for(int day = 1; day <= 31; day++) {
                // Read the file
                File f = null;
                if(day < 10) {
                    f = new File(FILE_BASE + "mapki.access.log.2006-" + month + "-0" + day + "");
                } else {
                    f = new File(FILE_BASE + "mapki.access.log.2006-" + month + "-" + day + "");
                }
                System.err.println("Adding data for file " + f.getAbsolutePath());
                BufferedReader r = null;
                String line = null;
                String ip = null;
                try {
                    r = new BufferedReader(new FileReader(f));

                    while((line = r.readLine()) != null) {
                        // Parse the line
                        String[] splits = line.split(" ");
                        ip = splits[0];

                        String[] ipSplits = ip.split("\\.");
                        int classA = Integer.parseInt(ipSplits[0]);
                        int classB = Integer.parseInt(ipSplits[1]);
                        //int classC = Integer.parseInt(ipSplits[2]);

                        data[classA][classB]++;
                        nPoints++;
                    }

                } catch (FileNotFoundException e) {
                    System.err.println("File not found for day " + month + "" + day);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    System.err.println("Bad number format for ip:");
                    System.err.println(ip);
                    e.printStackTrace();
                }
            }
        }
        System.err.println("Read " + nPoints + " lines.");
        System.err.println("... done.");
        
        System.err.print("Square rooting everything...");
        // Squirt everything
        for (int a = 0; a < 256; a++) {
            for (int b = 0; b < 256; b++) {
                    data[a][b] = Math.sqrt(data[a][b]);
            }
        }
        System.err.println("done.");

        System.err.println("Search for largest...");
        // Search for biggest
        double biggestVal = 0;
        for (int a = 0; a < 256; a++) {
            for (int b = 0; b < 256; b++) {
                    if (data[a][b] > biggestVal) {
                        biggestVal = data[a][b];
                    }
            }
        }
        biggestVal /= 3;
        mostOnOneBlock = biggestVal;
        System.err.println("biggestVal = " + mostOnOneBlock);
        System.err.println("done.");

        System.err.print("Drawing dots");
        // Normalize and draw
        for (int a = 0; a < 256; a++) {
            Point aPoint = HilbertMap.getPoint(a);
            for (int b = 0; b < 256; b++) {
                Point bPoint = HilbertMap.getPoint(b);
                Point shiftedPoint = new Point((aPoint.x * squareWidth) + (bPoint.x * smallSquareWidth),
                        (aPoint.y * squareWidth) + (bPoint.y * smallSquareWidth));

                int normalized = (int) ((((double) (data[a][b]) / (double) biggestVal) * 765.0));

                Color color = generateColor(normalized);
                g.setColor(color);
                g.fillRect(shiftedPoint.x, shiftedPoint.y, smallSquareWidth, smallSquareWidth);
            }
            System.err.print(".");
        }
        System.err.println("done.");
        
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



                        // Reset the color
                        g.setColor(Color.darkGray);
                    }
                }
                // Draw the big square's box
                g.setFont(new Font("Arial", Font.PLAIN, 9));
                g.setColor(Color.darkGray);
                g.drawString(""+classA, bigSquarePixelX+2, bigSquarePixelY+9);
                g.setColor(Color.darkGray);
                g.drawRect(bigSquarePixelX, bigSquarePixelY, squareWidth, squareWidth);
            }
        }
    }

    private Color generateColor(int normalized) {
        int red = normalized - 256;
        if(red < 0) {
            red = 0;
        } else if(red > 255) {
            red = 255;
        }
        
        int green = normalized - 0;
        if(green < 0) {
            green = 0;
        } else if(green > 255) {
            green = 255;
        }
        
        int blue = normalized - 512;
        if(blue < 0) {
            blue = 0;
        } else if(blue > 255) {
            blue = 255;
        }
        
        return new Color(red, green, blue);
    }

    public void paint(Graphics g) {
        if(drawSquare) {
            g.drawImage(image, 0, 0, this);
        }
    }
}