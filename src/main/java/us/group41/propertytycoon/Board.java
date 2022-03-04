package us.group41.propertytycoon;

import javafx.collections.ObservableIntegerArray;

public class Board {
    //create board
    int[] board = new int[40];

    public void startGame(int players) {
        board[0] = players;
    }

    public void movePlayer(int distance, int currentPos) {
        board[currentPos] -= 1;
        int newPos = currentPos + distance;
        board[newPos] += 1;
    }
}
