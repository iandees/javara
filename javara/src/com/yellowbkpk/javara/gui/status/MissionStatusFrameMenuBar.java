package com.yellowbkpk.javara.gui.status;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MissionStatusFrameMenuBar extends JMenuBar {
    public MissionStatusFrameMenuBar() {
        // File menu
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Quit");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Shuld initiate a shutdown here, not sys.exit.
                System.exit(0);
            }
        });
        menu.add(item);
        
        add(menu);
    }
}
