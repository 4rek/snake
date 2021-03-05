package com.snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Tile> fruits = new ArrayList<>();

    private String title = "";
    private String message = "";

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
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

//        graphics.setColor(Color.DARK_GRAY);
//        for (int x = 0; x < COL_COUNT; x++) {
//            for (int y = 0; y < ROW_COUNT; y++) {
//                graphics.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, getHeight());
//                graphics.drawLine(0, y * TILE_SIZE, getWidth(), y * TILE_SIZE);
//            }
//        }

        graphics.setColor(Color.WHITE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if (!title.equals("")) {
            graphics.drawString(title, centerX, centerY);
        }

        if (!message.equals("")) {
            graphics.drawString(message, centerX + 20, centerY + 20);
        }

        for (Tile tile : tiles) {
            graphics.setColor(Configuration.TILE_COLOR);
            graphics.fillRect(tile.getX(), tile.getY(), Configuration.TILE_SIZE, Configuration.TILE_SIZE);
            graphics.setColor(Color.WHITE);
        }
        for (Tile fruit : fruits) {
            graphics.setColor(Configuration.FRUIT_COLOR);
            graphics.fillRect(fruit.getX(), fruit.getY(), Configuration.TILE_SIZE, Configuration.TILE_SIZE);
            graphics.setColor(Color.WHITE);
        }
    }
}
