import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AFMImageReader {

    private static final long OFFSET = 20480L;
    private static final String PREFIX = "D:\\Documents and Settings\\212034767\\Desktop\\";
    private static final int FIELD_X = 512;
    private static final int FIELD_Y = 512;
    private static final double HARD_SCALE = 0.006713765; // V/LSB
    private static final double SOFT_SCALE = 13.68092; // nm/V
    private static double maxResult = Integer.MIN_VALUE;
    private static double minResult = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File f = new File(PREFIX + "100NCLAG.008");
        final double[][] dataArr = new double[FIELD_Y][FIELD_X];
        
        try {
            FileInputStream is = new FileInputStream(f);
            is.skip(OFFSET);
            byte[] sArr = new byte[2];
            for(int x = 0; x < FIELD_X; x++) {
                for(int y = 0; y < FIELD_Y; y++) {
                    is.read(sArr);
                    
                    // Convert to unsigned short
                    int lsb = sArr[0] + (sArr[1] << 8);
                    
                    // Convert to actual height
                    double result = (double) lsb * HARD_SCALE * SOFT_SCALE;
                    
                    if(result < minResult) {
                        minResult = result;
                    }
                    
                    if(result > maxResult) {
                        maxResult = result;
                    }
                    
                    dataArr[y][x] = result;
                    
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        final BufferedImage img = new BufferedImage(FIELD_X, FIELD_Y, BufferedImage.TYPE_INT_RGB);
        
        for(int x = 0; x < FIELD_X; x++) {
            for(int y = 0; y < FIELD_Y; y++) {
                int rgb = Color.HSBtoRGB(((float) dataArr[y][x] / (float) maxResult), 1, 1);
                img.setRGB(x, y, rgb);
            }
        }
        
        try {
            ImageIO.write(img, "PNG", new File("output.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JFrame frame = new JFrame("Image!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JLabel label = new JLabel(new ImageIcon(img));
        label.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.out.println(e.getX()+","+e.getY()+" = "+dataArr[e.getY()][e.getX()]);
            }
        });
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    double heightAtClick = dataArr[e.getY()][e.getX()];

                    int count = 0;
                    int rgb = Color.black.getRGB();
                    for (int x = 0; x < FIELD_X; x++) {
                        for (int y = 0; y < FIELD_Y; y++) {
                            if (dataArr[y][x] > heightAtClick) {
                                count++;
                            } else {
                                img.setRGB(x, y, rgb);
                            }
                        }
                    }
                    label.repaint();
                    System.out.println("Above " + heightAtClick + " = " + count);
                }
            }
        });
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
    }

}
