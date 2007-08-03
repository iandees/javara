package com.yellowbkpk.util.anim;

import java.awt.Graphics;

public interface Drawable {

    void step(long delta);

    void paint(Graphics dbg);

}
