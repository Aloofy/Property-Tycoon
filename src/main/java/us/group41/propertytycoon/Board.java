package us.group41.propertytycoon;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Board {


    private static final List<Player> players = new ArrayList<>(0);
    static Property[] tiles;

    private static short currentPlayerNo;

    boolean isStarted = false;
    Bank bank;
    FreeParking freeParking;
    Card[] cards;
    Queue<Card> potLuck;
    Queue<Card> opportunity;

    Board() {
        freeParking = new FreeParking();
        bank = new Bank();
        bank.setMoney(50000);
        tiles = loadProperty();
        cards = loadCard("PotLuck");
        potLuck = new LinkedList<Card>();

        Collections.addAll(potLuck, cards);
        Collections.shuffle((List<Card>) potLuck);
        cards = loadCard("Opportunity");
        opportunity = new LinkedList<Card>();
        Collections.addAll(opportunity, cards);
        Collections.shuffle((List<Card>) opportunity);
    }

    public static boolean endTurn() {

        short lastPlayer = currentPlayerNo;
        short i;
        for (i = (short) (currentPlayerNo + 1); i < players.size(); i++) {
            if (!players.get(i).isBankrupt()) {
                currentPlayerNo = i;
                break;
            }
        }
        if (currentPlayerNo != i) {
            for (i = 0; i < currentPlayerNo; i++) {
                if (!players.get(i).isBankrupt()) {
                    currentPlayerNo = i;
                    break;
                }
            }

        }

        return lastPlayer != currentPlayerNo;
    }

    public static boolean addPlayer(int playerInt, Player.Token token) {
        for (Player player : players) {
            if (player.getPlayerToken() == token) {
                return false;
            }
        }

        players.add(new Player(playerInt, token));
        return true;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static Property[] getTiles() {
        return tiles;
    }


    public short getNumPlayers() {
        return (short) players.size();
    }

    public short getCurrentPlayerNo() {
        return currentPlayerNo;
    }

    public Player getCurrentPlayer() {
        return players.get(getCurrentPlayerNo());
    }

    /**
     * Calls move player and tells you if you need to roll again.
     *
     * @param dist     Distance to move.
     * @param isDouble Whether the roll was a double.
     * @return true if the player should roll again, false if not.
     */
    public Boolean rolledDice(int dist, boolean isDouble) {
        short doubleTimes;
        Player player = players.get(currentPlayerNo);
        doubleTimes = player.getRollDoubleTimes();
        if (isDouble && doubleTimes == 2) {
            player.setInJail(true);
            player.setRollDoubleTimes((short) (0));  // reset number of doubles
            player.setCurrentPos(10);
            return false;
        }
        if (player.getInJail()) {
            if (isDouble) {
                player.setInJail(false);
                MovePlayer(dist);
            } else {
                player.setJailRolls((short) (player.getJailRolls() + 1));
                if (player.getJailRolls() == 3) {
                    player.setInJail(false);
                    player.setJailRolls((short) 0);
                    MovePlayer(dist);

                }
            }
        } else {
            MovePlayer(dist);
        }

        if (isDouble) {
            players.get(currentPlayerNo).setRollDoubleTimes((short) (doubleTimes + 1));
            return true;
        } else {
            player.setRollDoubleTimes((short) (0));  // reset number of doubles
            return false;
        }
    }

    public boolean endGame() {
        if (isStarted) {
            isStarted = false;
            for (Player player : players) {
                players.remove(player);
            }
        }
        return true;
    }

    public boolean startGame() {
        if (!isStarted) {
            isStarted = true;
            //numPlayers = (short) players.size();
            for (Player player : players) {
                player.setMoney(1500);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean MovePlayer(int amount) {
        int currentPos;
        int newPos;
        Player player = players.get(currentPlayerNo);
        currentPos = player.getCurrentPos();
        if ((currentPos + amount) > tiles.length - 1) {
            newPos = (currentPos + amount - tiles.length);
            bank.payMoney((short) 200); //give player 200 for passing GO
            player.giveMoney((short) 200);
            player.setPassedGO(true);
        } else {
            newPos = (currentPos + amount);
        }
        players.get(currentPlayerNo).setCurrentPos(newPos);
        return true;
    }

    public boolean movePlayerBack(int amount) {
        int currentPos;
        int newPos;
        Player player = players.get(currentPlayerNo);
        currentPos = player.getCurrentPos();
        if (currentPos >= amount) {
            newPos = (currentPos - amount);

        } else {
            newPos = (currentPos + tiles.length - amount);
        }
        players.get(currentPlayerNo).setCurrentPos(newPos);
        return true;
    }

    public boolean buyProperty(Property property) {

        Player player = players.get(currentPlayerNo);
        boolean brought = property.buyProperty(player, bank);
        if (brought) {
            boolean allProperties = true;
            for (Property tile : tiles) {
                if (Objects.equals(property.getGroup(), tile.getGroup())) {
                    if (tile.getOwner() != player || property.getOwner() == null) {
                        allProperties = false;
                    }
                }
            }
            for (Property tile : tiles) {
                if (Objects.equals(property.getGroup(), tile.getGroup())) {
                    tile.setAllPropertiesOwnedByOwner(allProperties);
                }
            }
        }
        return brought;
    }

    public Property[] loadProperty() {
        ObjectMapper objectMapper = new ObjectMapper();
        Property[] properties = new Property[0];
        try {
            InputStream input = new FileInputStream("property.json");
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

    public Card[] loadCard(String type) {
        ObjectMapper objectMapper = new ObjectMapper();
        Card[] cards = new Card[0];
        InputStream input;
        try {
            if (Objects.equals(type, "PotLuck")) {
                input = new FileInputStream("potluck.json");
            } else {
                input = new FileInputStream("opportunity.json");
            }

            try {
                cards = objectMapper.readValue(input, Card[].class);
            } catch (IOException e) {
                System.err.println("Invalid Format.");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.err.println("No card file found.");
            e.printStackTrace();
        }

        return cards;
    }

}