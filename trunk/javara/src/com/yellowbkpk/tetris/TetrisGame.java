package com.yellowbkpk.tetris;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisGame {
	private int score;
	private int level;
	private TetrisField field;
    private Timer gameTicker;
	
	private TetrisGame() {
	}

    public TetrisGame(TetrisField field) {
        this(0, 0, field);
    }
    
	private TetrisGame(int score, int level, TetrisField field) {
		this.score = score;
		this.level = level;
		this.field = field;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void newGame() {
        field.newPiece();
        
        gameTicker = new Timer();
        TimerTask tTask = new TimerTask() {
            public void run() {
                field.tick();
            }
        };
        gameTicker.scheduleAtFixedRate(tTask, 0, 1000);
	}

	public TetrisField getField() {
		return this.field;
	}
}
