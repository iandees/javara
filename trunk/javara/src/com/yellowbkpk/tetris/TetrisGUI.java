package com.yellowbkpk.tetris;

import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class TetrisGUI {
	private static final int HEIGHT = 700;
	private static final int WIDTH = 500;
	private JFrame frame;
	private TetrisPainter paint;
	private TetrisGame game;
    private Timer updater;
	
	public TetrisGUI(TetrisGame game) {
		this.game = game;
		this.paint = new TetrisPainter(game.getField());
		initGUI();
	}

	private void initGUI() {
		this.frame = new JFrame("Tetris");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = this.frame.getContentPane();
		c.add(paint);
        
        frame.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent key) {
                if(KeyEvent.VK_LEFT == key.getKeyCode()) {
                    System.err.println("Left");
                    game.getField().slideLeft();
                } else if(KeyEvent.VK_RIGHT == key.getKeyCode()) {
                    System.err.println("Right");
                    game.getField().slideRight();
                } else if(KeyEvent.VK_UP == key.getKeyCode()) {
                    System.err.println("Up");
                    game.getField().rotateClockwise();
                } else if(KeyEvent.VK_DOWN == key.getKeyCode()) {
                    System.err.println("Down");
                }
            }
            
        });
	}

	public void start() {
        updater = new Timer();
        TimerTask updateTask = new TimerTask() {
            public void run() {
                paint.repaint();
            }
        };
        updater.scheduleAtFixedRate(updateTask, 0, 50);
		this.frame.setVisible(true);
		//TODO -- add in support for a "new game" button
		this.game.newGame();
	}
	
	
}
