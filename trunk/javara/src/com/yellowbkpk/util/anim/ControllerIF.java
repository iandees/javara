package com.yellowbkpk.util.anim;

import java.util.List;



public interface ControllerIF {

    public List<? extends AbstractDrawable> getDrawables();

    public void update(float deltaTime);

}