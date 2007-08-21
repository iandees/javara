package com.yellowbkpk.antfarm.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.yellowbkpk.antfarm.creatures.Ant;
import com.yellowbkpk.antfarm.creatures.scent.Scent;
import com.yellowbkpk.antfarm.game.AntGame;

public class AntDrawPanel extends JPanel {

    private AntGame game;
    
    public AntDrawPanel(AntGame theGame) {
        game = theGame;
    }

    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }

    public Border getBorder() {
        return new LineBorder(Color.black, 1);
    }

    public void tick() {
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.red);
        ArrayList<Scent> scents = game.getFarm().getScents();
        for (Scent scent : scents) {
            int x = (int) scent.getLocation().x;
            int y = (int) scent.getLocation().y;
            
            g.fillRect(x, y, 2, 2);
        }

        g.setColor(Color.green);
        ArrayList<Ant> ants = game.getFarm().getAnts();
        for (Ant ant : ants) {
            int x = (int) ant.getLocation().x;
            int y = (int) ant.getLocation().y;
            
            g.fillRect(x, y, 2, 2);
        }
    }
    
}
