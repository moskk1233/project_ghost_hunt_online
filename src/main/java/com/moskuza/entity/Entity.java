package com.moskuza.entity;

import java.awt.*;
import java.io.Serializable;

public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Point point;
    private int x;
    private int y;

    public Entity() {
        this.point = new Point(0, 0);
        this.x = 0;
        this.y = 0;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setPoint(Point point) {
        this.point = point;
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
