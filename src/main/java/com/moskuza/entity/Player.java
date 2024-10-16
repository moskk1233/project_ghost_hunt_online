package com.moskuza.entity;

import java.io.Serial;

public class Player extends Entity {
    @Serial
    private static final long serialVersionUID = 4421699854961570315L;
    private int ammo;
    private int score;

    public Player() {
        super();
        this.ammo = 5;
        this.score = 0;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void shoot() {
        this.ammo = Math.max(0, this.ammo - 1);
    }

    public boolean isHit(Ghost ghost) {
        return this.getX() >= ghost.getX() && this.getX() <= ghost.getX() + 80 && this.getY() >= ghost.getY() && this.getY() <= ghost.getY() + 80;
    }
}
