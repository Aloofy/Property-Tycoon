package us.group41.propertytycoon;

import javafx.collections.ObservableIntegerArray;

public class Board {
    //create board
    int[] board = new int[40];
    boolean isStarted = false;

    public boolean startGame(int players) {
        if (isStarted) {
            return false;
        } else {
            board[0] = players;
            isStarted = true;
            return true;
        }
    }

    public boolean movePlayer(int newPos, int currentPos) {
        if (board[currentPos] > 0) {
            board[currentPos] -= 1;
            board[newPos] += 1;
            return true;
        } else {
            return false;
        }
    }

    public int getPlayersOnSquare(int pos) {
        return board[pos];
    }
}
