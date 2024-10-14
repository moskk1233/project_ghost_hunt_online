package com.moskuza.controller;

import com.moskuza.entity.Ghost;
import com.moskuza.views.PlayView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GhostMovementController {
    private static final int MOVE_RANGE = 10; // ขยับทีละน้อยเพื่อลดความกระตุก
    private final ArrayList<Ghost> ghosts;
    private final PlayView playView;
    private final Random random;

    public GhostMovementController(ArrayList<Ghost> ghosts, PlayView playView) {
        this.ghosts = ghosts;
        this.playView = playView;
        this.random = new Random();
        startGhostMovement();
    }

    private void startGhostMovement() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::moveGhosts, 0, 50, TimeUnit.MILLISECONDS); // อัปเดตทุก 16ms (ประมาณ 60 FPS)
    }

    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            // สุ่มทิศทางการขยับ (บวกลบ MOVE_RANGE)
            int deltaX = random.nextInt(2 * MOVE_RANGE + 1) - MOVE_RANGE;
            int deltaY = random.nextInt(2 * MOVE_RANGE + 1) - MOVE_RANGE;

            // อัปเดตตำแหน่งผีโดยคำนวณกับขอบเขต PlayView
            ghost.setX(Math.max(0, Math.min(ghost.getX() + deltaX, playView.getWidth())));
            ghost.setY(Math.max(0, Math.min(ghost.getY() + deltaY, playView.getHeight())));

            if (ghost.getX() > 944) {
                ghost.setX(944);
            } else if (ghost.getX() < 50) {
                ghost.setX(50);
            }

            if (ghost.getY() > 670) {
                ghost.setY(670);
            } else if (ghost.getY() < 10) {
                ghost.setY(10);
            }
        }
        playView.repaint(); // Repaint หน้าจอเพื่อแสดงผลการขยับใหม่
    }
}
