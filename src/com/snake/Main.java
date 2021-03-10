package com.snake;
/*
    Main class in application, only for initializing and running the game implementation.
 */
public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Game game = new Game(board);

        game.run();
    }
}
