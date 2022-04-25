package us.group41.propertytycoon;

public class Bank {
    private int money;

    public Integer getMoney() {
        return this.money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void payMoney(Short amount) {
        money = money - amount;
    }

    public void giveMoney(Short amount) {
        money = money + amount;
    }

    @Override
    public String toString() {
        return "\nmoney: " + money + "\n";
    }
}
