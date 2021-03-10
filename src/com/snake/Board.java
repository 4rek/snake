package com.snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Tile> fruits = new ArrayList<>();

    private String title = "";
    private String message = "";
    private int score = 0;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public void setFruits(ArrayList<Tile> fruits) {
        this.fruits = fruits;
    }

    public Board() {
        int width = Configuration.COL_COUNT * Configuration.TILE_SIZE;
        int height = Configuration.ROW_COUNT * Configuration.TILE_SIZE;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.WHITE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if (!title.equals("")) {
            graphics.drawString(title, centerX - title.length() * 3, centerY);
        }

        if (!message.equals("")) {
            graphics.drawString(message, centerX - message.length() * 3, centerY + 20);
        }

        if (score > 0) {
            graphics.drawString("Twój wynik: " + score, centerX - 45, centerY + 40);
        }

        // Rendering all snake tiles - head and tail
        for (Tile tile : tiles) {
            graphics.setColor(Configuration.TILE_COLOR);
            graphics.fillRect(tile.getX(), tile.getY(), Configuration.TILE_SIZE, Configuration.TILE_SIZE);
            graphics.setColor(Color.WHITE);
        }

        // Rendering all available fruits on board
        for (Tile fruit : fruits) {
            graphics.setColor(Configuration.FRUIT_COLOR);
            graphics.fillRect(fruit.getX(), fruit.getY(), Configuration.TILE_SIZE, Configuration.TILE_SIZE);
            graphics.setColor(Color.WHITE);
        }
    }
}
