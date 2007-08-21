package com.yellowbkpk.tetris;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class TetrisField {
	private boolean[][] field;

    private List<TetrisPiece> allPieces;
    private TetrisPiece activePiece;

    private Random rand;
	
	private static int DEFAULT_HEIGHT = 10;
	private static int DEFAULT_WIDTH = 30;
	
	public TetrisField(List<TetrisPiece> pieces) {
		this.field = new boolean[DEFAULT_WIDTH][DEFAULT_HEIGHT];
		allPieces = pieces;
        activePiece = pieces.get(0);
        rand = new Random();
        
		synchronized (field) {
            //Default the field to nothings
            for (int w = 0; w < field.length; w++) {
                for (int h = 0; h < field[0].length; h++) {
                    this.field[w][h] = false;
                }
            }
        }
	}

	public boolean hasSquare(int width, int height) {
	    synchronized(field) {
	        return field[width][height];
	    }
	}
	
	public Color getSquareColor(int width, int height) {
		return new Color(0.6f, 0.2f, 0.5f);
	}

	public int getWidth() {
		return field[0].length;
	}

	public int getHeight() {
		return field.length;
	}
	
	public void addPiece(TetrisPiece piece) {
		activePiece = piece;
        addPieceToBoard();
	}

    /**
     * @return
     */
    public TetrisPiece getActivePiece() {
        return activePiece;
    }

    /**
     * 
     */
    public void slideLeft() {
        if(activePiece.getTopLeftY()-1 < 0 ) {
            return;
        } else {
            removePieceFromBoard();
            getActivePiece().slideLeft();
            addPieceToBoard();
        }
    }

    /**
     * 
     */
    public void tick() {
        if(!hitBottom()) {
            fall();
        }
        checkForCompleteRow();
    }

    /**
     * 
     */
    private boolean hitBottom() {
        // Get the lower row of the active piece
        boolean[][] cont = activePiece.getContainer();
        boolean[] lowestRow = cont[cont.length-1];
        int nextRow = activePiece.getTopLeftY() + activePiece.getHeight() + 1;
        
        // For each slot in the row
        for (int i = 0; i < lowestRow.length; i++) {
            // Check to see if the slot one lower is out of bounds OR is filled in on the field
            // If it is, put the active piece onto the field and load up another one some how
            // (return true)
            // It it isn't, return false
        }
        return false;
    }

    /**
     * 
     */
    private void checkForCompleteRow() {
        for(int y = field.length-1; y > 0; y--) {
            int nPiecesAcross = 0;
            for(int x = 0; x < field[y].length; x++) {
                if(field[y][x]) {
                    nPiecesAcross++;
                }
            }
            
            if(nPiecesAcross == field[0].length) {
            } else {
            }
        }
    }

    /**
     * 
     */
    private void fall() {
        removePieceFromBoard();
        activePiece.fall();
        addPieceToBoard();
    }

    /**
     * 
     */
    private void addPieceToBoard() {

        boolean[][] activeContainer = activePiece.getContainer();
        int activeX = activePiece.getTopLeftX();
        int activeY = activePiece.getTopLeftY();
        
        synchronized(field) {
            for (int x = 0; x < activeContainer.length; x++) {
                for (int y = 0; y < activeContainer[0].length; y++) {
                    if(activeContainer[x][y]) {
                        field[activeX + x][activeY + y] = true;
                    }
                }
            }
        }
    }

    /**
     * 
     */
    private void removePieceFromBoard() {

        boolean[][] activeContainer = activePiece.getContainer();
        int activeX = activePiece.getTopLeftX();
        int activeY = activePiece.getTopLeftY();
        
        synchronized (field) {
            for (int x = 0; x < activeContainer.length; x++) {
                for (int y = 0; y < activeContainer[0].length; y++) {
                    if (activeContainer[x][y]) {
                        field[activeX + x][activeY + y] = false;
                    }
                }
            }
        }
    }

    /**
     * 
     */
    public void slideRight() {
        if(activePiece.getTopLeftY()+activePiece.getWidth()+1 > getWidth()) {
            return;
        } else {
            removePieceFromBoard();
            getActivePiece().slideRight();
            addPieceToBoard();
        }
    }

    public String toString() {
        String s = "";
        
        synchronized (field) {
            for (int x = 0; x < field.length; x++) {
                for (int y = 0; y < field[0].length; y++) {
                    if (field[x][y]) {
                        s += "X";
                    } else {
                        s += ".";
                    }
                }
                s += "\n";
            }
        }
        
        return s;
    }

    /**
     * 
     */
    public void rotateClockwise() {
        if(rotateClockwiseWorks()) {
            removePieceFromBoard();
            activePiece.rotateClockwise();
            addPieceToBoard();
        } else {
            return;
        }
    }

    /**
     * @return
     */
    private boolean rotateClockwiseWorks() {
        return false;
    }

    /**
     * 
     */
    public void newPiece() {
        addPiece(allPieces.get(rand.nextInt(allPieces.size())));
    }
    
}
