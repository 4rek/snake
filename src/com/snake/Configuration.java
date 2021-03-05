package com.snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static final Color TILE_COLOR = Color.CYAN;
    public static final Color FRUIT_COLOR = Color.RED;

    public static final int X_MIN = 0;
    public static final int X_MAX = (COL_COUNT - 1) * TILE_SIZE;
    public static final int X_STARTING = (COL_COUNT - 1) * TILE_SIZE / 2;

    public static final int Y_MIN = 0;
    public static final int Y_MAX = (ROW_COUNT - 1) * TILE_SIZE;
    public static final int Y_STARTING = (ROW_COUNT - 1) * TILE_SIZE / 2;

    public static final int ROUND_DURATION = 120;

    public static final int FRUIT_SCORE = 10;

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
