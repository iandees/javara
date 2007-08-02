package com.yellowbkpk.pool;

import com.yellowbkpk.pool.game.PoolGame;
import com.yellowbkpk.pool.gui.PoolView;

public class Pool {

    public static void main(String[] args) {
        PoolGame g = new PoolGame();
        PoolView v = new PoolView(g);
        v.start();
    }

}
