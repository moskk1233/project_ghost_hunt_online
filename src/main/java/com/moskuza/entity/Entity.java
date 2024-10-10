package com.moskuza.entity;

import java.awt.*;

public class Entity {
    private final Point point;

    public Entity() {
        this.point = new Point(0, 0);
    }

    public Point getPoint() {
        return this.point;
    }
}
