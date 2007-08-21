package com.yellowbkpk.tetris;

import java.util.ArrayList;
import java.util.List;

public class TetrisPiece {

	private boolean[][] container;
    private short x;
    private short y;
    private List<boolean[][]> containers;
    private int currentContainer;
	
	public TetrisPiece() {
		this(0,0);
	}
    
    public TetrisPiece(int upperLeftY) {
        this(0, upperLeftY);
    }
    
    private TetrisPiece(int upperLeftX, int upperLeftY) {
        x = (short) upperLeftX;
        y = (short) upperLeftY;
        containers = new ArrayList<boolean[][]>();
    }
    
    public void addConfiguration(boolean[][] c) {
        container = c;
        containers.add(container);
        currentContainer = containers.size();
    }

    /**
     * 
     */
    public void slideLeft() {
        y--;
    }
    
    public void slideRight() {
        y++;
    }

    /**
     * @return
     */
    public boolean[][] getContainer() {
        return container;
    }

    /**
     * 
     */
    public void fall() {
        x++;
    }

    /**
     * @return
     */
    public int getTopLeftY() {
        return y;
    }
    
    public int getTopLeftX() {
        return x;
    }

    /**
     * @return
     */
    public int getWidth() {
        return container[0].length;
    }

    /**
     * 
     */
    public void rotateClockwise() {
        currentContainer++;
        
        if(currentContainer >= containers.size()) {
            currentContainer = 0;
        }
        
        System.err.println(currentContainer);
        boolean[][] newContainer = containers.get(currentContainer);
        
        container = newContainer;
    }

    /**
     * @return
     */
    public int getHeight() {
        return container.length;
    }

}
