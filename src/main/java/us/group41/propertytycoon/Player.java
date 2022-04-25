package us.group41.propertytycoon;

public class Player {
    private final int playerNum;
    public Token playerToken;
    private Integer money = 0;
    private Boolean InJail = false;
    //private short position = 0;
    private int currentPos;
    private short rollDoubleTimes = 0;
    private short numJailFree = 0;


    private boolean passedGO = false;


    private short lastRoll;

    Player(int playerNum, Token token) {
        this.playerNum = playerNum;
        playerToken = token;
    }

    public boolean isPassedGO() {
        return passedGO;
    }

    public void setPassedGO(boolean passedGO) {
        this.passedGO = passedGO;
    }

    public short getLastRoll() {
        return lastRoll;
    }

    public void setLastRoll(short lastRoll) {
        this.lastRoll = lastRoll;
    }

    public Boolean getInJail() {
        return InJail;
    }

    public void setInJail(Boolean inJail) {
        InJail = inJail;
    }

    public short getNumJailFree() {
        return numJailFree;
    }

    public void setNumJailFree(short numJailFree) {
        this.numJailFree = numJailFree;
    }

    public short getRollDoubleTimes() {
        return rollDoubleTimes;
    }

    public void setRollDoubleTimes(short rollDoubleTimes) {
        this.rollDoubleTimes = rollDoubleTimes;
    }

    public Property getCurrentTile(Board board) {
        return Board.tiles[this.getCurrentPos()];
    }


    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public Token getPlayerToken() {
        return playerToken;
    }

    public void setPlayerToken(Token playerToken) {
        this.playerToken = playerToken;
    }

    public void payMoney(Short amount) {
        money = money - amount;
    }


    public Integer getMoney() {
        return this.money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void giveMoney(Short amount) {
        money = money + amount;
    }

    public short getHouses(Board board, Player player) {
        short numHouses = 0;
        for (int i = 0; i < Board.tiles.length; i++) {
            if (Board.tiles[i].getOwner() == player) {
                numHouses = (short) (numHouses + Board.tiles[i].getNumHouses());
            }
        }
        return numHouses;
    }

    public short getHotels(Board board, Player player) {
        short numHotels = 0;
        for (int i = 0; i < Board.tiles.length; i++) {
            if (Board.tiles[i].getOwner() == player) {
                numHotels = (short) (numHotels + Board.tiles[i].getNumHotels());
            }
        }
        return numHotels;
    }

    public enum Token {
        BOOT, SMARTPHONE, SHIP, HATSTAND, CAT, IRON
    }

    @Override
    public String toString() {
        return "\nmoney: " + money + "\n";
    }
}