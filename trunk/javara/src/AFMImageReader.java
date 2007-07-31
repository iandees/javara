import java.awt.Color;
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
    private static int maxResult = Integer.MIN_VALUE;
    private static int minResult = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File f = new File(PREFIX + "100NCLAG.008");
        int[][] dataArr = new int[FIELD_Y][FIELD_X];
        
        try {
            FileInputStream is = new FileInputStream(f);
            is.skip(OFFSET);
            byte[] sArr = new byte[2];
            for(int x = 0; x < FIELD_X; x++) {
                for(int y = 0; y < FIELD_Y; y++) {
                    is.read(sArr);
                    
                    // Convert to short
                    int result = sArr[0] + (sArr[1] << 8);
                    
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
        
        BufferedImage img = new BufferedImage(FIELD_X, FIELD_Y, BufferedImage.TYPE_INT_RGB);
        
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
        frame.add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setVisible(true);
    }

}
