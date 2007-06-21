package com.yellowbkpk.jnova.client.game;

import java.awt.Graphics;

public interface Drawable {

    void step(long delta);

    void paint(Graphics dbg);

}
