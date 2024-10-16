package com.moskuza.entity;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
    @Serial
    private static final long serialVersionUID = -8844574920404961349L;
    private Point point;
    private int x;
    private int y;
    private UUID id;

    public Entity() {
        this.point = new Point(0, 0);
        this.id = UUID.randomUUID();
        this.x = 0;
        this.y = 0;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public UUID getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
