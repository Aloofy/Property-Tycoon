package us.group41.propertytycoon;

/**
 * This class is used to model the player including robots, and the functions a player could need.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class Player {
    private final int playerNum;
    public Token playerToken;
    private short money = 0;
    private Boolean inJail = false;
    private int currentPos;
    //if you roll double 3 times, go to jail.
    private short rollDoubleTimes = 0;
    //number of get out of jail free cards.
    private short numJailFree = 0;
    private boolean passedGO = false;
    //value of the last dice roll, used in calculating rent for utilities
    private short lastRoll;
    private boolean robot;

    /**
     * Player constructor
     *
     * @param playerNum player's number
     * @param token     player's token
     * @param robot     if player is a robot
     */
    Player(int playerNum, Token token, boolean robot) {
        this.playerNum = playerNum;
        playerToken = token;
        this.robot = robot;
    }

    private boolean isBankrupt = false;
    private short jailRolls = 0;

    /**
     * @return if it's a robot.
     */
    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }

    /**
     * @return player number
     */
    public int fetchPlayerNum() {
        return playerNum;
    }

    /**
     * @return if player is bankrupt
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * sets if the player is bankrupt
     *
     * @param bankrupt true for is bankrupt, false for not bankrupt.
     */
    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    /**
     * @return amount of rolls of dice whilst in jail
     */
    public short getJailRolls() {
        return jailRolls;
    }

    /**
     * sets the rolls of dice whilst in jail
     *
     * @param jailRolls amount to set to.
     */
    public void setJailRolls(short jailRolls) {
        this.jailRolls = jailRolls;
    }

    /**
     * @return if player has passed GO.
     */
    public boolean isPassedGO() {
        return passedGO;
    }

    /**
     * sets if player has passed go
     *
     * @param passedGO true if they've passed go
     */
    public void setPassedGO(boolean passedGO) {
        this.passedGO = passedGO;
    }

    /**
     * @return player's last roll of dice
     */
    public short getLastRoll() {
        return lastRoll;
    }

    /**
     * sets what their last roll of dice was
     *
     * @param lastRoll number total of their last roll of dice.
     */
    public void setLastRoll(short lastRoll) {
        this.lastRoll = lastRoll;
    }

    /**
     * @return if in jail.
     */
    public Boolean getInJail() {
        return inJail;
    }

    /**
     * sets if the player should be in jail
     *
     * @param inJail true if in jail, false otherwise
     */
    public void setInJail(Boolean inJail) {
        this.inJail = inJail;
    }

    /**
     * @return number of get out of jail free cards you have
     */
    public short getNumJailFree() {
        return numJailFree;
    }

    /**
     * sets number of get out of jail free cards a player has
     *
     * @param numJailFree amount of get out of jail free cards for a player to have.
     */
    public void setNumJailFree(short numJailFree) {
        this.numJailFree = numJailFree;
    }

    /**
     * @return get number of consecutive doubles rolled.
     */
    public short getRollDoubleTimes() {
        return rollDoubleTimes;
    }

    /**
     * sets number of times you've rolled doubles in a row
     *
     * @param rollDoubleTimes number of doubles you've rolled in a row
     */
    public void setRollDoubleTimes(short rollDoubleTimes) {
        this.rollDoubleTimes = rollDoubleTimes;
    }

    /**
     * @return the property the player is currently on
     */
    public Property getCurrentTile() {
        return Board.tiles[this.getCurrentPos()];
    }

    /**
     * @return the tile number that the player is currently on
     */
    public int getCurrentPos() {
        return currentPos;
    }

    /**
     * set's the player's current tile number
     *
     * @param currentPos position for player to be put on.
     */
    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    /**
     * @return the current token that is assigned to a player
     */
    public Token getPlayerToken() {
        return playerToken;
    }

    /**
     * removes money from player
     *
     * @param amount amount to take from player
     */
    public void payMoney(short amount) {
        money -= amount;
    }

    /**
     * @return player's current money.
     */
    public short getMoney() {
        return this.money;
    }

    /**
     * sets a player's money, used at start of game
     *
     * @param money for them to start with
     */
    public void setMoney(short money) {
        this.money = money;
    }

    /**
     * give money to player
     *
     * @param amount of money to give
     */
    public void giveMoney(short amount) {
        money += amount;
    }

    /**
     * gets the total number of houses that a player has on all of their properties used for tax calculation
     *
     * @param player which player to get the information from
     * @return returns number of houses
     */
    public short getHouses(Player player) {
        short numHouses = 0;
        for (int i = 0; i < Board.tiles.length; i++) {
            if (Board.tiles[i].getOwner() == player) {
                numHouses = (short) (numHouses + Board.tiles[i].getNumHouses());
            }
        }
        return numHouses;
    }

    /**
     * gets the total number of hotels that a player has on all of their properties used for tax calculation
     *
     * @param player which player to get the information from
     * @return returns number of hotels
     */
    public short getHotels(Player player) {
        short numHotels = 0;
        for (int i = 0; i < Board.tiles.length; i++) {
            if (Board.tiles[i].getOwner() == player) {
                numHotels = (short) (numHotels + Board.tiles[i].getNumHotels());
            }
        }
        return numHotels;
    }

    /**
     * enum of tokens in the game.
     */
    public enum Token {
        BOOT, SMARTPHONE, SHIP, HATSTAND, CAT, IRON
    }
}