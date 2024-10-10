package com.moskuza.entity;

public class Player extends Entity {
    private int id;

    public Player(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
