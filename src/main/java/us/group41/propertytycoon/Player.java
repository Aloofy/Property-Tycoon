package us.group41.propertytycoon;

public class Player {
    private String name = null;
    private int playerNo;
    private Integer money = 0;
    private Boolean isInJail = false;

    Player(int playerNum, String playerName) {
        playerNo = playerNum;
        name = playerName;
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


}