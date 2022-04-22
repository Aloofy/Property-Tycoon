package us.group41.propertytycoon;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
    static List<Player> players = new ArrayList<>(0);
    //create board
    int[] board = new int[40];
    boolean isStarted = false;
    Property[] tiles;
    short numPlayers = 0;
    short currentPlayer;
    Bank bank;
    FreeParking freeParking;

    public static boolean addPlayer(int playerInt, Player.Token token) {
        for (Player player : players) {
            if (player.getPlayerToken() == token) {
                return false;
            }
        }

        players.add(new Player(playerInt, token));
        return true;
    }

    public boolean rolledDice(int dist, boolean isDouble) {

        return newMovePlayer(dist);
    }

    public boolean startGame() {
        int i;
        if (!isStarted) {
            isStarted = true;
            // freeParking = new FreeParking();
            bank = new Bank();
            bank.setMoney(50000);
            tiles = loadProperty();
            numPlayers = (short) players.size();
            for (Player player : players) {
                player.setMoney(1500);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean newMovePlayer(int amount) {
        int currentPos;
        int newPos;
        currentPos = players.get(currentPlayer).getCurrentPos();
        if ((currentPos + amount) > tiles.length) {
            newPos = (currentPos + amount - tiles.length);
        } else {
            newPos = (currentPos + amount);
        }
        players.get(currentPlayer).setCurrentPos(newPos);
        return true;
    }

    public boolean buyProperty(Property property) {
        int currentPlayer = 0;
        Bank bank = new Bank();
        boolean brought = property.buyProperty(players.get(currentPlayer), bank);
        if (brought) {
            boolean allProperties = true;
            for (Property tile : tiles) {
                if (Objects.equals(property.getGroup(), tile.getGroup())) {
                    if (property.getOwner() != players.get(currentPlayer)) {
                        allProperties = false;
                    }
                }
            }
            for (Property tile : tiles) {
                if (Objects.equals(property.getGroup(), tile.getGroup())) {
                    property.setAllPropertiesOwnedByOwner(allProperties);
                }
            }
        }
        return brought;
    }

    public Property[] loadProperty() {
        ObjectMapper objectMapper = new ObjectMapper();
        Property[] properties = new Property[0];
        try {
            InputStream input = new FileInputStream("src/main/resources/us/group41/propertytycoon/property.json");
            try {
                properties = objectMapper.readValue(input, Property[].class);
            } catch (IOException e) {
                System.err.println("Invalid Format.");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.err.println("No property file found.");
            e.printStackTrace();
        }
        return properties;
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
