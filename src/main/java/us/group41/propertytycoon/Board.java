package us.group41.propertytycoon;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class is used to model aspects of the board, including moving players, starting and ending games, and filling up board and card piles.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class Board {
    //set of players playing the game, list so that it can dynamically be added and removed.
    private static final List<Player> players = new ArrayList<>(0);
    static Property[] tiles;
    private static short currentPlayerNo;
    boolean isStarted = false;
    Bank bank;
    FreeParking freeParking;
    Card[] cards;
    //Cards are stored in queues so that it can be taken from the top and add to the bottom.
    Queue<Card> potLuck;
    Queue<Card> opportunity;

    /**
     * Board constructor
     */
    Board() {
        freeParking = new FreeParking();
        bank = new Bank();
        bank.setMoney(50000);
        tiles = loadProperty();
        cards = loadCard("PotLuck");
        potLuck = new LinkedList<>();
        Collections.addAll(potLuck, cards);
        //the cards should be shuffled
        Collections.shuffle((List<Card>) potLuck);
        cards = loadCard("Opportunity");
        opportunity = new LinkedList<>();
        Collections.addAll(opportunity, cards);
        //the cards should be shuffled
        Collections.shuffle((List<Card>) opportunity);
    }

    /**
     * Ends the current turn. Calculates who's the next player to play by skipping over bankrupt players.
     *
     * @return false if the game only has one player left
     */
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

    /**
     * Adds a player to the board.
     *
     * @param playerInt player number
     * @param token     player's token
     * @param robot     if they're a robot
     * @return false if the token has already been used, true if it hasn't.
     */
    public static boolean addPlayer(int playerInt, Player.Token token, boolean robot) {
        boolean result = true;
        for (Player player : players) {
            if (player.getPlayerToken() == token) {
                result = false;
                break;
            }
        }
        if (result) {
            players.add(new Player(playerInt, token, robot));
        }
        return result;
    }

    /**
     * @return players
     */
    public static List<Player> getPlayers() {
        return players;
    }

    /**
     * @return list of properties
     */
    public static Property[] getTiles() {
        return tiles;
    }

    /**
     * @return number of players
     */
    public short getNumPlayers() {
        return (short) players.size();
    }

    /**
     * @return current player number
     */
    public short getCurrentPlayerNo() {
        return currentPlayerNo;
    }

    /**
     * @return current player object
     */
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
        Boolean result = false;
        short doubleTimes;
        Player player = players.get(currentPlayerNo);
        doubleTimes = player.getRollDoubleTimes();
        if (isDouble && doubleTimes == 2) {
            player.setInJail(true);
            player.setRollDoubleTimes((short) (0));  // reset number of doubles
            player.setCurrentPos(10);
        } else {
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
                result = true;
            } else {
                player.setRollDoubleTimes((short) (0));  // reset number of doubles
            }
        }

        return result;
    }

    /**
     * Ends the game.
     */
    public void endGame() {
        if (isStarted) {
            isStarted = false;
            for (Player player : players) {
                players.remove(player);
            }
        }
    }

    /**
     * Starts the game.
     *
     * @return false if already started.
     */
    public boolean startGame() {
        if (!isStarted) {
            isStarted = true;
            //give players their money
            for (Player player : players) {
                player.setMoney((short) 1500);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Moves the player to the new tile.
     *
     * @param amount the distance determined by their dice roll.
     */
    public void MovePlayer(int amount) {
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
    }

    /**
     * If a card instructs the player to move backwards, it's handled here.
     *
     * @param amount distance to move backwards by.
     */
    public void movePlayerBack(int amount) {
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
    }

    /**
     * Buy the property.
     *
     * @param property property to buy.
     * @return if it's been successfully brought.
     */
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

    /**
     * Load the property array from the .json file.
     *
     * @return the property array generated from .json.
     */
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

    /**
     * Load the card array from the .json file into the correct queue.
     *
     * @param type the queue to add the cards to.
     * @return the card array generated from .json.
     */
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