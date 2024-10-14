package com.moskuza.entity;

import java.util.UUID;

public class Player extends Entity {
    private final UUID id;
    private int ammo;
    private int score;

    public Player() {
        super();
        this.id = UUID.randomUUID();
        this.ammo = 5;
        this.score = 0;
    }

    public UUID getId() {
        return this.id;
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
}
