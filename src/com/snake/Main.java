package com.snake;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Game game = new Game(board);

        game.run();
    }
}
