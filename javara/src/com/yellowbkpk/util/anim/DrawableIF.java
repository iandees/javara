package com.yellowbkpk.util.anim;

import java.awt.Graphics;

public interface DrawableIF {

    void step(long delta);

    void paint(Graphics dbg);

}
