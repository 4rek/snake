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
                    case KeyEvent.VK_UP:
                        changeDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        changeDirection(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        changeDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        changeDirection(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_SPACE:
                        goToNextScene();
                        break;
                    default:
                        break;
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
                case MENU:
                    renderMenu();
                    break;
                case GAME_ON:
                    renderGame();
                    break;
                case GAME_OVER:
                    renderGameOver();
                    break;
                default:
                    break;
            }

            waitUntilNextMove();
        }
    }

    private void renderMenu() {
        board.setTitle("Gra Snake");
        board.setMessage("Aby zagrać naciśnij Spację");
        board.setScore(0);
        repaint();
    }

    private void detectCollisions() throws CollissionException {
        // A collission happens when more than 1 tile has specific coordinates
        // so let's check if head has not move into another tile
        long damaged = tiles.stream().filter(t -> t.getX() == head.getX() && t.getY() == head.getY()).count();
        // if there are more than 1 tile with head's coordinates it means that head moved straight into snake's body
        // meaning we have a collision on which an Exception needs to be raised
        if (damaged > 1) {
            throw new CollissionException("Collision with self!");
        }

        // Since we know that there were no collisions with snake's body let's check if we did not hit a fruit.
        for (Tile fruit : fruits) {
            // iterating through all available fruits on board and checking if the coordinates match up
            if (fruit.getX() == head.getX() && fruit.getY() == head.getY()) {
                // if so, let's add the default score for fruit into player's account
                score += Configuration.FRUIT_SCORE;
                // next we "eat" the fruit which means adding it to the snake's tail
                tiles.add(fruit);
                // and removing fruit from the fruit's list
                fruits.remove(fruit);
                return;
            }
        }
    }

    /*
        Method that moves tiles across the board.
     */
    private void moveTiles() {
        // First we move the snake's tail, starting from last element
        // so that head+1 element reaches coordinates of snake's head
        for (int i = (tiles.size() - 1); i > 0; i--) {
            Tile current = tiles.get(i);
            Tile before = tiles.get(i - 1);

            current.setX(before.getX());
            current.setY(before.getY());
        }

        // After the above we can move snake's head to new coordinates.
        switch (direction) {
            case UP:
                head.setY(head.getY() - Configuration.TILE_SIZE);
                break;
            case DOWN:
                head.setY(head.getY() + Configuration.TILE_SIZE);
                break;
            case LEFT:
                head.setX(head.getX() - Configuration.TILE_SIZE);
                break;
            case RIGHT:
                head.setX(head.getX() + Configuration.TILE_SIZE);
                break;
            default:
                break;
        }
    }

    private void addFruitToBoard() {
        int x = generateRandomCoordinate(0, Configuration.COL_COUNT - 1);
        int y = generateRandomCoordinate(0, Configuration.ROW_COUNT - 1);
        Tile fruit = new Tile(x, y);

        fruits.add(fruit);
    }

    private void renderGame() {
        board.setTitle("");
        board.setMessage("");
        repaint();

        // First we define empty ArrayLists for fruits and snake tiles
        // so that every game runs from scratch.
        fruits = new ArrayList<>();
        tiles = new ArrayList<>();

        head = new Tile(Configuration.X_STARTING, Configuration.Y_STARTING);

        tiles.add(head);

        int iteration = 0;
        score = 0;
        gameIsPlaying = true;

        while (gameIsPlaying) {
            // On every round we update tiles and fruits on board.
            board.setTiles(tiles);
            board.setFruits(fruits);

            // If we haven't yet reach out fruit limit on the board
            // then on every 10th iteration we try to add a fruit
            if (fruits.size() < Configuration.MAX_FRUITS_ON_BOARD && iteration % 10 == 0) {
                addFruitToBoard();
            }

            // Let's move snake's tiles to new positions
            moveTiles();

            // Checking tile's positions
            try {
                detectCollisions();
            } catch (CollissionException e) {
                // If Exception happens that means the collission that happened has been with the snake's body.
                // In that case we end game, so let's save the player's score and go to next scene.
                board.setScore(score);
                goToNextScene();
                break;
            }

            // Repaint board, increase iteration's number and weait for defined period of time for the next round.
            repaint();
            iteration++;
            waitUntilNextMove();
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
            case MENU:
                scene = Scene.GAME_ON;
                break;
            case GAME_ON:
                scene = Scene.GAME_OVER;
                break;
            case GAME_OVER:
                scene = Scene.MENU;
                break;
            default:
                break;
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
