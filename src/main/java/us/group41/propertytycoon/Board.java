package us.group41.propertytycoon;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Board {
    //create board
    int[] board = new int[40];
    boolean isStarted = false;
    Property[] tiles;
    Player[] players;

    public boolean startGame(int numPlayers) {
        int i;
        if (isStarted) {
            return false;
        } else {

            FreeParking freeParking = new FreeParking();
            Bank bank = new Bank();
            bank.setMoney(5000);
            Player[] players = new Player[numPlayers];

            for (i = 0; i < numPlayers; i++) {
                players[i] = new Player(i, "name");
                players[i].setMoney(500);
            }
            players[0].getMoney();


            isStarted = true;
            return true;
        }
    }

    public Property[] loadProperty() {

        ObjectMapper objectMapper = new ObjectMapper();
        Property[] prop2 = new Property[0];
        try {
            InputStream input = new FileInputStream("/tmp/property.json");
            try {
                prop2 = objectMapper.readValue(input, Property[].class);
                prop2[1].toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return prop2;
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
