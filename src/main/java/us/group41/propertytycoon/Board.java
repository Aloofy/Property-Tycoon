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
    private static short numPlayers = 0;
    private static short currentPlayerNo;
    //create board
    //int[] board = new int[40];
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

    public static void endTurn() {
        if (currentPlayerNo == numPlayers - 1) {
            currentPlayerNo = 0;
        } else {
            currentPlayerNo = (short) (currentPlayerNo + 1);
        }
        players.get(currentPlayerNo).setRollDoubleTimes((short) (0));
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

    public Card takeCard(String cardType, List<Player> players, Board board) {
        Card card;
        Player player;
        if (Objects.equals(cardType, "PotLuck")) {
            card = potLuck.poll();
        } else {
            card = opportunity.poll();
        }
        player = board.getCurrentPlayer();
        card.action.performAction(players, board);
        if ("JailFree".equals(card.getAction().getActionType())) {
            player.setNumJailFree((short) (player.getNumJailFree() + 1));
        } else {
            if (Objects.equals(cardType, "PotLuck")) {
                potLuck.add(card);
            } else {
                opportunity.add(card);
            }
        }
        return card;
    }

    public short getNumPlayers() {
        return numPlayers;
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
        doubleTimes = players.get(currentPlayerNo).getRollDoubleTimes();
        if (isDouble && doubleTimes == 2) {
            // if 3 times then send to jail.
            return false;
        }
        newMovePlayer(dist);
        if (isDouble) {
            players.get(currentPlayerNo).setRollDoubleTimes((short) (doubleTimes + 1));
            return true;
        } else {
            return false;
        }
    }


    public boolean startGame(Board board) {
        if (!isStarted) {
            isStarted = true;
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
        Player player = players.get(currentPlayerNo);
        currentPos = player.getCurrentPos();
        if ((currentPos + amount) > tiles.length) {
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

    public boolean buyProperty(Property property) {

        boolean brought = property.buyProperty(players.get(currentPlayerNo), bank);
        if (brought) {
            boolean allProperties = true;
            for (Property tile : tiles) {
                if (Objects.equals(property.getGroup(), tile.getGroup())) {
                    if (property.getOwner() != players.get(currentPlayerNo)) {
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

    public Card[] loadCard(String type) {
        ObjectMapper objectMapper = new ObjectMapper();
        Card[] cards = new Card[0];
        InputStream input;
        try {
            if (Objects.equals(type, "PotLuck")) {
                input = new FileInputStream("src/main/resources/us/group41/propertytycoon/potluck.json");
            } else {
                input = new FileInputStream("src/main/resources/us/group41/propertytycoon/opportunity.json");
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
