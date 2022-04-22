package us.group41.propertytycoon;

public class Player {
    private final int playerNo;
    public Token playerToken;
    private String name = null;
    private Integer money = 0;
    private Boolean isInJail = false;
    private short position = 0;
    private int currentPos;

    Player(int playerNum, Token token) {
        playerNo = playerNum;
        playerToken = token;
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

    public short getPosition() {
        return position;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return this.money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void PayMoney(Short amount) {
        money = money - amount;
    }

    public void GiveMoney(Short amount) {
        money = money + amount;
    }

    public void setInJail() {
        isInJail = true;
    }

    public boolean isInJail() {
        return isInJail;
    }

    @Override
    public String toString() {
        return "\nmoney: " + money + "\n";
    }


    public enum Token {
        BOOT, SMARTPHONE, SHIP, HATSTAND, CAT, IRON
    }


}