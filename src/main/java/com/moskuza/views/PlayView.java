// PlayView.java
package com.moskuza.views;

import com.moskuza.controller.GameController;
import com.moskuza.entity.Ghost;
import com.moskuza.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class PlayView extends JPanel {
    final Image backgroundImage;
    final Image bulletImage;
    final Image crosshairImage;
    final Image ghostImage;
    int bulletAmount;
    final Player player;
    private final GameController gameController;


    public PlayView() {
        setSize(new Dimension(1024, 800));

        URL backgroundUrl = getClass().getResource("/images/ghost_house.jpg");
        this.backgroundImage = Toolkit.getDefaultToolkit().createImage(backgroundUrl);

        URL bulletUrl = getClass().getResource("/images/bullet.png");
        this.bulletImage = Toolkit.getDefaultToolkit().createImage(bulletUrl);
        this.bulletAmount = 5;

        URL crosshairUrl = getClass().getResource("/images/crosshair.png");
        this.crosshairImage = Toolkit.getDefaultToolkit().createImage(crosshairUrl);

        URL ghostUrl = getClass().getResource("/images/ghost.png");
        this.ghostImage = Toolkit.getDefaultToolkit().createImage(ghostUrl);

        // Add Player
        this.player = new Player();
        this.gameController = new GameController(this);
        JPanel instance = this;

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // อัปเดตตำแหน่งของ Player และ Crosshair ตามเมาส์
                player.setX(e.getX());
                player.setY(e.getY());
                gameController.sendObject(player);
                instance.repaint(); // เรียก repaint เพื่อแสดงผลการเคลื่อนไหวของ Player
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // เรียก super.paint(g) เพื่อให้แน่ใจว่า Panel ได้รับการวาดอย่างถูกต้อง
        g.drawImage(this.backgroundImage, 0, 0, 1024, 800, this);
        for (int i = 0; i < this.bulletAmount; i++) {
            final int BULLET_OFFSET = 40;
            g.drawImage(this.bulletImage, (BULLET_OFFSET * i), 700, 60, 60, this);
        }

        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.setColor(Color.WHITE);

        // Draw Ghosts
        for (Ghost ghost : gameController.getGhosts()) {
            g.drawImage(this.ghostImage, ghost.getX(), ghost.getY(), 80, 80, this);  // วาดผี
            g.drawRect(ghost.getX(), ghost.getY(), 80, 80); // Hitbox
        }

        // Draw Player
        for (Map.Entry<UUID, Player> entry : gameController.getPlayers().entrySet()) {
            Player player = entry.getValue();
            g.drawString("Player %s".formatted(player.getId().toString().substring(0, 8)), player.getX() - 60, player.getY() - 20);
            g.drawImage(this.crosshairImage, player.getX() - 20, player.getY() - 20, 35, 35, this);
        }

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
