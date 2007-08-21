package com.yellowbkpk.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class TetrisPainter extends JPanel {

	private TetrisField field;

    private Random random;
	
	public static final int SQUARE_DIMENSION = 20;
	
	public TetrisPainter(TetrisField f) {
		this.field = f;
        
        random = new Random();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
        
		for(int height = 0; height < field.getHeight(); height++) {
			for(int width = 0; width < field.getWidth(); width++) {
                
				int x = width * SQUARE_DIMENSION;
				int y = height * SQUARE_DIMENSION;
                
                //g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                //g2d.drawLine(random.nextInt(350), random.nextInt(450), random.nextInt(350), random.nextInt(450));
                
				if(field.hasSquare(height, width)) {
					g2d.setColor(field.getSquareColor(height, width));
					g2d.fillRect(x, y, SQUARE_DIMENSION, SQUARE_DIMENSION);
					g2d.setColor(Color.BLACK);
					g2d.drawRect(x, y, SQUARE_DIMENSION, SQUARE_DIMENSION);
				} else {
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.fillRect(x+1, y+1, SQUARE_DIMENSION-1, SQUARE_DIMENSION-1);
				}
			}
		}
	}
}
