package com.snake;

public class Tile {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        if (x > Configuration.X_MAX) {
            this.x = Configuration.X_MIN;
        } else if (x < Configuration.X_MIN) {
            this.x = Configuration.X_MAX;
        } else {
            this.x = x;
        }
    }

    public void setY(int y) {
        if (y > Configuration.Y_MAX) {
            this.y = Configuration.Y_MIN;
        } else if (y < Configuration.Y_MIN) {
            this.y = Configuration.Y_MAX;
        } else {
            this.y = y;
        }
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
