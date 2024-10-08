package com.moskuza.entity;

public class Ghost extends Entity {
    private boolean death;

    public Ghost() {
        super();
        this.death = false;
    }

    public boolean isDead() {
        return death;
    }

    public void setDead(boolean dead) {
        this.death = dead;
    }
}
