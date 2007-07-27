import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bubbles {

    public static void main(String[] args) {
        BubbleWindow w = new BubbleWindow();
        w.start();
    }

}

class BubbleWindow extends JFrame {

    private List<Bubble> theBubbles;
    private Timer timer;
    private TimerTask task;

    public BubbleWindow() {
        super("Bubbles!");
        
        theBubbles = new ArrayList<Bubble>();
        
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BubbleDrawPane p = new BubbleDrawPane(theBubbles);
        
        setContentPane(p);
        
        pack();
        
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                // Update the bubble locations
                for (Bubble bubble : theBubbles) {
                    bubble.tick();
                }
                
                // Redraw the bubbles
                repaint();
            }
        };
    }
    
    public void start() {
        setVisible(true);
        timer.scheduleAtFixedRate(task, 0, 150);
    }
    
}

class BubbleDrawPane extends JPanel {
    
    private List<Bubble> theBubbles;
    private Random r;

    public BubbleDrawPane(List<Bubble> bubbles) {
        super(new BorderLayout());
        theBubbles = bubbles;
        r = new Random();
        
        JPanel buttonBar = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonBar.setPreferredSize(new Dimension(150, 20));
        JButton teleportButton = new JButton("teleport");
        teleportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                randomizeBubbles();
            }
        });
        buttonBar.add(teleportButton);
        
        JButton popButton = new JButton("pop");
        popButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                theBubbles.clear();
            }
        });
        buttonBar.add(popButton);
        add(buttonBar, BorderLayout.SOUTH);
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                theBubbles.add(createNewBubbleInWindow());
            }
        });
    }
    
    protected void paintComponent(Graphics g) {
        for (Bubble bubble : theBubbles) {
            bubble.paint(g);
        }
    }

    protected Bubble createNewBubbleInWindow() {
        Point p = getRandomPointInWindow();
        
        Bubble b = new Bubble(p);
        return b;
    }

    private Point getRandomPointInWindow() {
        int x = r.nextInt(getWidth());
        int y = r.nextInt(getHeight());
        Point p = new Point(x,y);
        return p;
    }

    protected void randomizeBubbles() {
        for (Bubble bubble : theBubbles) {
            bubble.setCenter(getRandomPointInWindow());
        }
    }

}

class Bubble {
    
    private static final int DEFAULT_RADIUS = 5;
    private Point center;
    private int rad;
    private int diam;
    private Random r;

    public Bubble(Point loc, int radius) {
        r = new Random();
        center = loc;
        rad = radius;
        diam = 2 * rad;
    }

    public void tick() {
        if(r.nextBoolean()) {
            center.y++;
        } else {
            center.y--;
        }
    }

    public void paint(Graphics g) {
        g.drawOval(center.x - rad, center.y - rad, diam, diam);
    }

    public Bubble(Point point) {
        this(point, DEFAULT_RADIUS);
    }

    public void setCenter(Point loc) {
        center = loc;
    }
    
}