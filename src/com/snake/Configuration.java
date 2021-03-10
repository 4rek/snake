package com.snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
    Class containing all configuration constants needed for game.
 */
public class Configuration {
    public static final int COL_COUNT = 25;

    /**
     * The number of rows on the board. (Should be odd so we can start in
     * the center).
     */
    public static final int ROW_COUNT = 25;

    /**
     * The size of each tile in pixels.
     */
    public static final int TILE_SIZE = 20;

    /**
     * Default color for snake tiles.
     */
    public static final Color TILE_COLOR = Color.CYAN;
    /**
     * Default color for fruit tiles.
     */
    public static final Color FRUIT_COLOR = Color.RED;

    public static final int X_MIN = 0;
    public static final int X_MAX = (COL_COUNT - 1) * TILE_SIZE;
    public static final int X_STARTING = (COL_COUNT - 1) * TILE_SIZE / 2;

    public static final int Y_MIN = 0;
    public static final int Y_MAX = (ROW_COUNT - 1) * TILE_SIZE;
    public static final int Y_STARTING = (ROW_COUNT - 1) * TILE_SIZE / 2;

    /**
     * DTime duration of each round in miliseconds.
     */
    public static final int ROUND_DURATION = 120;

    /**
     * Default score awarded to player after snake eats a fruit
     */
    public static final int FRUIT_SCORE = 10;

    /**
     * Maximum amount of fruits available on board.
     */
    public static final int MAX_FRUITS_ON_BOARD = 3;

    /**
     * List of possible movements from given starting position of snake.
     */
    public static final HashMap<Direction, ArrayList<Direction>> ALLOWED_DIRECTIONS = new HashMap<>() {{
        put(Direction.UP, new ArrayList<>() {{
            add(Direction.LEFT);
            add(Direction.RIGHT);
        }});
        put(Direction.DOWN, new ArrayList<>() {{
            add(Direction.LEFT);
            add(Direction.RIGHT);
        }});
        put(Direction.LEFT, new ArrayList<>() {{
            add(Direction.UP);
            add(Direction.DOWN);
        }});
        put(Direction.RIGHT, new ArrayList<>() {{
            add(Direction.UP);
            add(Direction.DOWN);
        }});
    }};
}
