package com.yellowbkpk.util.anim;

import java.awt.Graphics;

public interface DrawableIF {

    void step(float delta);

    void paint(Graphics dbg);

}
