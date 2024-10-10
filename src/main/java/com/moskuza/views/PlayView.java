package com.moskuza.views;

import com.moskuza.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class PlayView extends JPanel {
    final Image backgroundImage;
    final Image bulletImage;
    final Image crosshairImage;

    int bulletAmount;
    final Player player;

    public PlayView() {
        URL backgroundUrl = getClass().getResource("/images/ghost_house.jpg");
        this.backgroundImage = Toolkit.getDefaultToolkit().createImage(backgroundUrl);

        URL bulletUrl = getClass().getResource("/images/bullet.png");
        this.bulletImage = Toolkit.getDefaultToolkit().createImage(bulletUrl);
        this.bulletAmount = 5;

        URL crosshairUrl = getClass().getResource("/images/crosshair.png");
        this.crosshairImage = Toolkit.getDefaultToolkit().createImage(crosshairUrl);

        // Add Player
        this.player = new Player(1);

        JPanel instance = this;

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                Point playerPoint = player.getPoint();
                playerPoint.setLocation(mouseX, mouseY);

                instance.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                Point playerPoint = player.getPoint();
                playerPoint.setLocation(mouseX, mouseY);

                instance.repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g)  {
        g.drawImage(this.backgroundImage, 0, 0, 1024, 800, this);
        for (int i = 0; i < this.bulletAmount; i++) {
            final int BULLET_OFFSET = 40;
            g.drawImage(this.bulletImage, (BULLET_OFFSET * i), 700, 60, 60, this);
        }


        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        Point playerPoint = this.player.getPoint();
        g.setColor(Color.WHITE);
        // Draw Player
        g.drawString("Player %d".formatted(this.player.getId()), playerPoint.x - 30, playerPoint.y - 20);
        g.drawImage(this.crosshairImage, playerPoint.x - 20, playerPoint.y - 20, 35, 35, this);

        // Draw Scoreboard
        g.drawString("Scoreboard", 0, 20);
        for (int i = 0; i < 3; i++) {
            g.drawString("Player %d: 12312".formatted(i + 1), 0, 20 + (i + 1) * 30);
        }

        // Draw Wave
        g.setFont(new Font(g.getFont().getName(), getFont().getStyle(), 40));
        g.drawString("Wave %d".formatted(1), this.getWidth() / 2, 50);
    }
}
