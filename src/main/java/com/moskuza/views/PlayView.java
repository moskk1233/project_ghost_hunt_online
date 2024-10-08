package com.moskuza.views;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PlayView extends JPanel {
    Image backgroundImage;

    public PlayView() {
        URL backgroundUrl = getClass().getResource("/images/ghost_house.jpg");
        this.backgroundImage = Toolkit.getDefaultToolkit().createImage(backgroundUrl);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, 1024, 800, this);
    }
}
