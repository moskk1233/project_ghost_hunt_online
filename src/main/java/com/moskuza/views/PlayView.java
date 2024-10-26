// PlayView.java
package com.moskuza.views;

import com.moskuza.controller.GameController;
import com.moskuza.entity.Ghost;
import com.moskuza.entity.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class PlayView extends JPanel {
    private final Image backgroundImage;
    private final Image bulletImage;
    private final Image crosshairImage;
    private final Image ghostImage;
    private final Player player;
    private Clip gunSound;

    private final GameController gameController;

    private Clip loadSound(String path) throws Exception {
        URL url = getClass().getResource(path);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    private void playSound(Clip clip) {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public GameController getGameController() {
        return gameController;
    }

    public PlayView() {
        setSize(new Dimension(1024, 800));

        URL backgroundUrl = getClass().getResource("/images/ghost_house.jpg");
        this.backgroundImage = Toolkit.getDefaultToolkit().createImage(backgroundUrl);

        URL bulletUrl = getClass().getResource("/images/bullet.png");
        this.bulletImage = Toolkit.getDefaultToolkit().createImage(bulletUrl);

        URL crosshairUrl = getClass().getResource("/images/crosshair.png");
        this.crosshairImage = Toolkit.getDefaultToolkit().createImage(crosshairUrl);

        URL ghostUrl = getClass().getResource("/images/ghost.png");
        this.ghostImage = Toolkit.getDefaultToolkit().createImage(ghostUrl);

        try {
            this.gunSound = this.loadSound("/sounds/gun.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }


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

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Ghost ghost : gameController.getGhosts()) {
                    if (player.isHit(ghost) && !ghost.isDead() && player.getAmmo() > 0) {
                        player.setScore(player.getScore() + 100);
                        System.out.println(ghost.getId());
                        ghost.setDead(true);
                        gameController.sendObject(ghost);
                        break;
                    }
                }

                if (player.getAmmo() > 0) {
                    playSound(gunSound);
                }
                player.shoot();
                gameController.sendObject(player);
                instance.repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // เรียก super.paint(g) เพื่อให้แน่ใจว่า Panel ได้รับการวาดอย่างถูกต้อง
        g.drawImage(this.backgroundImage, 0, 0, 1024, 800, this);

        // Draw Bullet
        for (int i = 0; i < this.player.getAmmo(); i++) {
            final int BULLET_OFFSET = 40;
            g.drawImage(this.bulletImage, (BULLET_OFFSET * i), 700, 60, 60, this);
        }

        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.setColor(Color.WHITE);

        // Draw Ghosts
        for (Ghost ghost : gameController.getGhosts()) {
            if (ghost.isDead()) continue;
            g.drawImage(this.ghostImage, ghost.getX(), ghost.getY(), 80, 80, this);  // วาดผี
            g.drawRect(ghost.getX(), ghost.getY(), 80, 80); // Hitbox
        }

        // Draw Player
        g.drawString("Scoreboard", 0, 20);
        int scoreY = 50;
        ArrayList<Player> sortedPlayers = new ArrayList<>(gameController.getPlayers().values());
        sortedPlayers.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for (Player player : sortedPlayers) {
            g.drawString("Player %s".formatted(player.getId().toString().substring(0, 8)), player.getX() - 60, player.getY() - 20);
            g.drawImage(this.crosshairImage, player.getX() - 20, player.getY() - 20, 35, 35, this);

            // Draw Score
            g.drawString("Player %s: %d".formatted(player.getId().toString().substring(0, 8), player.getScore()), 0, scoreY);
            scoreY += 30;
        }
        // Draw Wave
        int currentWave = gameController.getWave();
        g.setFont(new Font(g.getFont().getName(), getFont().getStyle(), 40));
        g.drawString("Wave %d".formatted(currentWave), this.getWidth() / 2, 50);

        if (currentWave >= 10) {
            boolean allDead = true;
            for (Ghost ghost : gameController.getGhosts()) {
                if (!ghost.isDead()) {
                    allDead = false;
                    break;
                }
            }

            if (allDead) {
                g.setFont(new Font(g.getFont().getName(), getFont().getStyle(), 50));
                g.setColor(Color.ORANGE);
                g.drawString("WINNER!!", this.getWidth() / 2 - 100, this.getHeight() / 2 - 50);
                g.drawString(sortedPlayers.getFirst().getId().toString().substring(0, 8), this.getWidth() / 2 - 100, this.getHeight() / 2);
                g.setFont(new Font(g.getFont().getName(), getFont().getStyle(), 50));
            }
        }
    }
}
