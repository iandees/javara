package com.yellowbkpk.pool.game;

import java.util.ArrayList;
import java.util.List;

import com.yellowbkpk.util.anim.ControllerIF;
import com.yellowbkpk.util.anim.DrawableIF;

public class PoolGame implements ControllerIF {

    private List<DrawableIF> balls;
    private long prevTime;
    
    public PoolGame() {
        balls = new ArrayList<DrawableIF>();
    }

    public List<DrawableIF> getDrawables() {
        return balls;
    }

    public synchronized void update() {
        for (DrawableIF ball : balls) {
            long delta = System.currentTimeMillis() - prevTime;
            ball.step(delta);
        }
        
        prevTime = System.currentTimeMillis();
    }

    public synchronized void addBall(Ball ball) {
        balls.add(ball);
    }

}
