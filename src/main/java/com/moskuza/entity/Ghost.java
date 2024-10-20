package com.moskuza.entity;

import java.io.Serial;

public class Ghost extends Entity {
    @Serial
    private static final long serialVersionUID = 5593693463030055296L;
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
