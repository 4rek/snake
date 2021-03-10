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

    /*
        Method that sets X coordinate of tile have been implemented with a check
        whether tile is not out of boundary - if so, it's being set to start of the opposite side of board.
     */
    public void setX(int x) {
        if (x > Configuration.X_MAX) {
            this.x = Configuration.X_MIN;
        } else if (x < Configuration.X_MIN) {
            this.x = Configuration.X_MAX;
        } else {
            this.x = x;
        }
    }

    /*
        Method that sets Y coordinate of tile have been implemented with a check
        whether tile is not out of boundary - if so, it's being set to start of the opposite side of board.
     */
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
