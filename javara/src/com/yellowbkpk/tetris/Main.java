package com.yellowbkpk.tetris;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
	    List<TetrisPiece> pieces = new ArrayList<TetrisPiece>();
        
        TetrisPiece L = new TetrisPiece();
        L.addConfiguration(new boolean[][] {
                new boolean[] {false, true},
                new boolean[] {false, true},
                new boolean[] {true,  true}
        });
        L.addConfiguration(new boolean[][] {
                new boolean[] {true,  true, true},
                new boolean[] {false,false, true}
        });
        L.addConfiguration(new boolean[][] {
                new boolean[] {true,  true},
                new boolean[] {true, false},
                new boolean[] {true, false}
        });
        L.addConfiguration(new boolean[][] {
                new boolean[] {true, false, false},
                new boolean[] {true,  true, true}
        });
        pieces.add(L);
        
        TetrisPiece I = new TetrisPiece();
        I.addConfiguration(new boolean[][] {
                new boolean[] { true },
                new boolean[] { true },
                new boolean[] { true },
                new boolean[] { true }
        });
        I.addConfiguration(new boolean[][] {
                new boolean[] { true, true, true, true }
        });
        pieces.add(I);
        
        TetrisPiece SQ = new TetrisPiece();
        SQ.addConfiguration(new boolean[][] {
                new boolean[] {true, true},
                new boolean[] {true, true}
        });
        pieces.add(SQ);
        
        TetrisPiece SR = new TetrisPiece();
        SR.addConfiguration(new boolean[][] {
                new boolean[] { false, true },
                new boolean[] { true,  true },
                new boolean[] { true, false }
        });
        SR.addConfiguration(new boolean[][] {
                new boolean[] { true, true, false },
                new boolean[] { false,true, true  }
        });
        pieces.add(SR);
        
        TetrisPiece SL = new TetrisPiece();
        SL.addConfiguration(new boolean[][] {
                new boolean[] { true, false },
                new boolean[] { true, true  },
                new boolean[] { false,true  }
        });
        SL.addConfiguration(new boolean[][] {
                new boolean[] { false, true, true },
                new boolean[] { true,  true,false }
        });
        pieces.add(SL);
        
        TetrisField f = new TetrisField(pieces);
		TetrisGame g = new TetrisGame(f);
		TetrisGUI gui = new TetrisGUI(g);
		gui.start();
	}
}
