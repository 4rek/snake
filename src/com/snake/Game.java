package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 */
public class Game extends JFrame {

    /**
     * Board object that game is played on
     */
    private final Board board;

    /**
     * Current Scene of the game, MENU as default
     */
    private Scene scene = Scene.MENU;

    /**
     * Snake's direction in game, UP as default
     */
    private Direction direction = Direction.UP;

    /**
     * Variable deteermining if loop for a game should be running
     */
    private Boolean gameIsPlaying = false;

    /**
     * List of available tiles that build the snake
     */
    private ArrayList<Tile> tiles;

    /**
     * List of available fruits on scene
     */
    private ArrayList<Tile> fruits;

    /**
     * The tile that represents snake's head
     */
    private Tile head;

    /**
     * Game score, 0 as default
     */
    private int score = 0;

    public Game(Board board) {
        super("Snake Game");

        this.board = board;

        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(board, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        mountKeyListeners();
    }

    private void mountKeyListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> changeDirection(Direction.UP);
                    case KeyEvent.VK_DOWN -> changeDirection(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> changeDirection(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> changeDirection(Direction.RIGHT);
                    case KeyEvent.VK_SPACE -> goToNextScene();
                }
            }
        });
    }

    private int generateRandomCoordinate(int min, int max) {
        return (min + (int) (Math.random() * ((max - min) + 1))) * Configuration.TILE_SIZE;
    }

    /**
     *
     */
    public void run() {
        while (true) {
            switch (scene) {
                case MENU -> renderMenu();
                case GAME_ON -> renderGame();
                case GAME_OVER -> renderGameOver();
            }
        }
    }

    private void renderMenu() {
        board.setTitle("Gra Snake");
        board.setMessage("Aby zagrać naciśnij Spację");
        board.setScore(0);
        repaint();
    }

    private void detectCollisions() throws Exception {
        long damaged = tiles.stream().filter(t -> t.getX() == head.getX() && t.getY() == head.getY()).count();
        if (damaged > 1) {
            throw new Exception("Collision with self!");
        }

        for (Tile fruit : fruits) {
            if (fruit.getX() == head.getX() && fruit.getY() == head.getY()) {
                score += Configuration.FRUIT_SCORE;
                tiles.add(fruit);
                fruits.remove(fruit);
                return;
            }
        }
    }

    private void moveTiles() {
        for (int i = (tiles.size() - 1); i > 0; i--) {
            Tile current = tiles.get(i);
            Tile before = tiles.get(i - 1);

            current.setX(before.getX());
            current.setY(before.getY());
        }

        switch (direction) {
            case UP -> head.setY(head.getY() - Configuration.TILE_SIZE);
            case DOWN -> head.setY(head.getY() + Configuration.TILE_SIZE);
            case LEFT -> head.setX(head.getX() - Configuration.TILE_SIZE);
            case RIGHT -> head.setX(head.getX() + Configuration.TILE_SIZE);
        }
    }

    private void addFruitToBoard() {
        if (fruits.size() < 3) {
            int x = generateRandomCoordinate(0, Configuration.COL_COUNT - 1);
            int y = generateRandomCoordinate(0, Configuration.ROW_COUNT - 1);
            Tile fruit = new Tile(x, y);

            fruits.add(fruit);
        }
    }

    private void renderGame() {
        board.setTitle("");
        board.setMessage("");
        repaint();

        fruits = new ArrayList<>();
        tiles = new ArrayList<>();

        head = new Tile(Configuration.X_STARTING, Configuration.Y_STARTING);

        tiles.add(head);

        int iteration = 0;
        score = 0;
        gameIsPlaying = true;

        while (gameIsPlaying) {
            board.setTiles(tiles);
            board.setFruits(fruits);

            if (iteration % 10 == 0) {
                addFruitToBoard();
            }

            moveTiles();

            try {
                detectCollisions();
            } catch (Exception e) {
                board.setScore(score);
                goToNextScene();
                break;
            }

            repaint();
            waitUntilNextMove();

            iteration++;
        }

    }

    private void waitUntilNextMove() {
        try {
            Thread.sleep(Configuration.ROUND_DURATION);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    private void renderGameOver() {
        board.setTitle("Koniec gry");
        board.setMessage("Aby przejść do menu naciśnij Spację");
        repaint();
    }

    private void clearScene() {
        board.setTiles(new ArrayList<>());
        board.setFruits(new ArrayList<>());
    }

    private void goToNextScene() {
        switch (scene) {
            case MENU -> scene = Scene.GAME_ON;
            case GAME_ON -> scene = Scene.GAME_OVER;
            case GAME_OVER -> scene = Scene.MENU;
        }

        clearScene();

        gameIsPlaying = false;
    }

    private void changeDirection(Direction direction) {
        if (Configuration.ALLOWED_DIRECTIONS.get(this.direction).contains(direction) && scene == Scene.GAME_ON) {
            this.direction = direction;
        }
    }
}
