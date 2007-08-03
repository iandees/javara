package com.yellowbkpk.pool.game;

import java.util.ArrayList;
import java.util.List;

import com.yellowbkpk.util.anim.ControllerIF;
import com.yellowbkpk.util.anim.DrawableIF;

public class PoolGame implements ControllerIF {

    private List<DrawableIF> balls;
    
    public PoolGame() {
        balls = new ArrayList<DrawableIF>();
    }

    public List<DrawableIF> getDrawables() {
        return balls;
    }

    public void update() {
        
    }

}
